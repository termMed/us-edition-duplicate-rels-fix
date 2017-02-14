/*
 *
 *  * Copyright (C) 2016 termMed IT
 *  * www.termmed.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 */
/*
 *  * Author: Alejandro Rodriguez
 */
package com.termmed.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.termmed.configuration.Parameters;

// TODO: Auto-generated Javadoc
/**
 * The Class FixRels.
 */
public class FixRels {

	private static final String DUPLICATE_RELS_FILE = "duplicate_rels.txt";

	/** The logger. */
	private static Logger logger;

	/** The config. */
	private static Parameters config;

	/**
	 * Instantiates a new fix rels.
	 *
	 * @param fileConfig the file config
	 * @throws ClassNotFoundException the class not found exception
	 */
	public FixRels(String fileConfig) throws ClassNotFoundException {
		logger = Logger.getLogger("com.termmed.runner.Runner");
		getParams(new File(fileConfig));
		createFolders();
	}

	/**
	 * Creates the folders.
	 */
	private void createFolders() {
		File output=new File(Constants.OUTPUT_FOLDER);
		if (!output.exists()){
			output.mkdirs();
		}else{
			if (!output.isDirectory()){
				output=new File(Constants.OUTPUT_FOLDER + UUID.randomUUID().toString());
				output.mkdirs();
			}
		}
	}

	/**
	 * Gets the params.
	 *
	 * @param file the file
	 * @return the params
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void getParams(File file) throws ClassNotFoundException {
		logger.info("Paramaters in " + file.getAbsolutePath());
		config = XmlMapUtil.getConfigFromFileSystem(file);
		GsonBuilder gsonb=new GsonBuilder();
		gsonb.setPrettyPrinting();
		Gson gson=gsonb.create();
		logger.info(gson.toJson(config,Class.forName("com.termmed.configuration.Parameters")));

	}

	/**
	 * Execute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void execute() throws IOException{
		HashMap<Long,List<String>> extRels=FileHelper.loadFile(config.getRf2ExtensionSnapshotStatedRels(),Constants.RELS_SOURCEID_COLUMN_IX, new Rf2Filter(new String[]{Constants.EQUAL},new int[]{Constants.ACTIVE_COLUMN_IX},new String[]{"1"}));

		HashMap<Long,List<String>>coreRels=FileHelper.loadFile(config.getRf2CoreSnapshotStatedRels(),Constants.RELS_SOURCEID_COLUMN_IX, new Rf2Filter(new String[]{Constants.EQUAL},new int[]{Constants.ACTIVE_COLUMN_IX},new String[]{"1"}));

		HashSet<String>duplRels=checkDuplicateRelatioship(extRels,coreRels);

		writeRelsInactivation(duplRels);

		consolidateStatedRels(duplRels);
		
		HashSet<String>otherRels=checkOthersRelationshipsFromCoreConcept(extRels,config.getRf2CoreSnapshotConcepts(),duplRels);

		writeRels(otherRels,"core_sourceId_rels.txt");
	}

	private void consolidateStatedRels(HashSet<String> rels) throws IOException {
		File outputFile=new File(Constants.OUTPUT_FOLDER,new File(config.getRf2ExtensionSnapshotStatedRels()).getName());
		BufferedWriter bw = FileHelper.getWriter(outputFile);

		HashSet<String>relIds=new HashSet<String>();
		String[] spl;
		for(String rel:rels){
			spl=rel.split("\t",-1);
			relIds.add(spl[0]);
		}
		
		BufferedReader br = FileHelper.getReader(new File(config.getRf2ExtensionSnapshotStatedRels()));
		br.readLine();
		bw.append(Constants.RELS_HEADER);
		bw.append("\r\n");
		String line;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (relIds.contains(spl[0])){
				continue;
			}else{
				bw.append(line);
				bw.append("\r\n");
			}
		}
		bw.close();
		br.close();
		
		HashSet<File> hFile=new HashSet<File>();
		hFile.add(new File(Constants.OUTPUT_FOLDER,DUPLICATE_RELS_FILE));
		hFile.add(outputFile);
		
		FileHelper.concatFile(hFile, outputFile);
		
		outputFile=new File(Constants.OUTPUT_FOLDER,new File(config.getRf2ExtensionFullStatedRels()).getName());
		bw = FileHelper.getWriter(outputFile);
		br = FileHelper.getReader(new File(config.getRf2ExtensionFullStatedRels()));
		br.readLine();
		bw.append(Constants.RELS_HEADER);
		bw.append("\r\n");
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (relIds.contains(spl[0]) && spl[1].equals(config.getReleaseDate())){
				continue;
			}else{
				bw.append(line);
				bw.append("\r\n");
			}
		}
		bw.close();
		br.close();
		
		hFile=new HashSet<File>();
		hFile.add(new File(Constants.OUTPUT_FOLDER,DUPLICATE_RELS_FILE));
		hFile.add(outputFile);
		
		FileHelper.concatFile(hFile, outputFile);
		
		outputFile=new File(Constants.OUTPUT_FOLDER,new File(config.getRf2ExtensionDeltaStatedRels()).getName());
		bw = FileHelper.getWriter(outputFile);
		br = FileHelper.getReader(new File(config.getRf2ExtensionDeltaStatedRels()));
		br.readLine();
		bw.append(Constants.RELS_HEADER);
		bw.append("\r\n");
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (relIds.contains(spl[0])){
				continue;
			}else{
				bw.append(line);
				bw.append("\r\n");
			}
		}
		bw.close();
		br.close();
		
		hFile=new HashSet<File>();
		hFile.add(new File(Constants.OUTPUT_FOLDER,DUPLICATE_RELS_FILE));
		hFile.add(outputFile);
		
		FileHelper.concatFile(hFile, outputFile);
	}

	/**
	 * Write rels inactivation.
	 *
	 * @param rels the rels
	 * @param filename the filename
	 * @param releaseDate the release date
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeRelsInactivation(HashSet<String> rels) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		HashSet<String> rows=new HashSet<String>();
		String[] spl;
		StringBuffer row;
		for(String rel:rels){
			spl=rel.split("\t",-1);
			row=new StringBuffer();
			for (int i=0;i<spl.length;i++){
				if (i==Constants.ACTIVE_COLUMN_IX){
					row.append("0");
				}else if (i==Constants.EFFECTIVETIME_COLUMN_IX){
					row.append(config.getReleaseDate());
				}else{
					row.append(spl[i]);
				}
				if (i<spl.length-1){
					row.append("\t");
				}
			}
			rows.add(row.toString());
		}
		writeRels(rows,DUPLICATE_RELS_FILE);
	}

	/**
	 * Check others relationships from core concept.
	 *
	 * @param extRels the ext rels
	 * @param rf2CoreSnapshotConcepts the rf2 core snapshot concepts
	 * @param duplRels the dupl rels
	 * @return the hash set
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private HashSet<String> checkOthersRelationshipsFromCoreConcept(HashMap<Long, List<String>> extRels,
			String rf2CoreSnapshotConcepts, HashSet<String> duplRels) throws IOException {

		HashSet<String> existingCoreSourceInRels=new HashSet<String>();
		HashMap<Long, List<String>> coreConcept = FileHelper.loadFile(rf2CoreSnapshotConcepts, Constants.SCTID_COLUMN_IX, null);

		for(Long id:extRels.keySet()){
			if (coreConcept.get(id)!=null ){
				List<String> extensionRels = extRels.get(id);

				for (String extRel:extensionRels){
					if (!duplRels.contains(extRel)){
						existingCoreSourceInRels.add(extRel);
					}
				}
			}
		}
		return existingCoreSourceInRels;
	}

	/**
	 * Check duplicate relatioship.
	 *
	 * @param extRels the ext rels
	 * @param coreRels the core rels
	 * @return the hash set
	 */
	private HashSet<String> checkDuplicateRelatioship(HashMap<Long, List<String>> extRels,
			HashMap<Long, List<String>> coreRels) {

		HashSet<String> duplRels=new HashSet<String>();
		String[] splCore;
		String[] splExt;
		for (Long id:extRels.keySet()){
			List<String> rels = coreRels.get(id);
			if (rels==null){
				continue;
			}
			List<String> extensionRels = extRels.get(id);

			for (String extRel:extensionRels){
				splExt=extRel.split("\t",-1);
				for (String coreRel:rels){
					splCore=coreRel.split("\t",-1);

					if (splExt[Constants.RELS_DESTINATIONID_COLUMN_IX].compareTo(splCore[Constants.RELS_DESTINATIONID_COLUMN_IX])==0
							&& splExt[Constants.RELS_GROUP_COLUMN_IX].compareTo(splCore[Constants.RELS_GROUP_COLUMN_IX])==0
							&& splExt[Constants.RELS_TYPEID_COLUMN_IX].compareTo(splCore[Constants.RELS_TYPEID_COLUMN_IX])==0){
						duplRels.add(extRel);
					}
				}

			}
		}
		return duplRels;
	}

	/**
	 * Write rels.
	 *
	 * @param rels the rels
	 * @param filename the filename
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeRels(HashSet<String> rels, String filename)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {

		BufferedWriter bw = FileHelper.getWriter(new File(Constants.OUTPUT_FOLDER,filename));

		FileHelper.writeInfo(Constants.RELS_HEADER,rels, bw);

		bw.close();
	}
}
