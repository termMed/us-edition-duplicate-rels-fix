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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class FileHelper.
 */
public class FileHelper {
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger(FileHelper.class);
	
	/**
	 * Gets the reader.
	 *
	 * @param inFile the in file
	 * @return the reader
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 */
	public static BufferedReader getReader(File inFile) throws UnsupportedEncodingException, FileNotFoundException {

		FileInputStream rfis = new FileInputStream(inFile);
		InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
		BufferedReader rbr = new BufferedReader(risr);
		return rbr;

	}
	
	/**
	 * Gets the writer.
	 *
	 * @param file the file
	 * @return the writer
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 */
	public static BufferedWriter getWriter(File file) throws UnsupportedEncodingException, FileNotFoundException {

		FileOutputStream tfos = new FileOutputStream( file);
		OutputStreamWriter tfosw = new OutputStreamWriter(tfos,"UTF-8");
		return new BufferedWriter(tfosw);

	}

	/**
	 * Load file.
	 *
	 * @param rf2File the rf2 file
	 * @param sctidColumnIx the sctid column ix
	 * @param rf2Filter the rf2 filter
	 * @return the hash map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static HashMap<Long, List<String>> loadFile(String rf2File, int sctidColumnIx,
			Rf2Filter rf2Filter) throws IOException {
		HashMap<Long, List<String>> conceptRow=new HashMap<Long, List<String>>();
		
		
		BufferedReader br = getReader(new File(rf2File));
		br.readLine();
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (rf2Filter!=null && !rf2Filter.matchFilter(spl)){
				continue;
			}
			Long id=Long.parseLong(spl[sctidColumnIx]);
			List<String> rows = conceptRow.get(id);
			if (rows==null){
				rows=new ArrayList<String>();
			}
			rows.add(line);
			conceptRow.put(id, rows);
			
		}
		br.close();
		return conceptRow;
	}

	/**
	 * Write info.
	 *
	 * @param header the header
	 * @param lines the lines
	 * @param bw the bw
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeInfo(String header, HashSet<String> lines, BufferedWriter bw) throws IOException {
		
		if (header!=null){
			bw.append(header);
			bw.append("\r\n");
		}
		for(String line:lines){
			bw.append(line);
			bw.append("\r\n");
		}
	}
	/**
	 * Concatenate files.
	 *
	 * @param hFile the set with files to concatenate
	 * @param outputfile the outputfile
	 */
	public static void concatFile(HashSet<File> hFile, File outputfile) {

		try{
			
			String fileName=outputfile.getName();
			File fTmp = new File(outputfile.getParentFile()  + "/tmp_" + fileName);

			if (fTmp.exists())
				fTmp.delete();
			
			FileOutputStream fos = new FileOutputStream( fTmp);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			boolean first = true;
			String nextLine;
			for (File file:hFile){

				FileInputStream fis = new FileInputStream(file	);
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				
				nextLine=br.readLine();
				if (first && nextLine!=null){
					bw.append(nextLine);
					bw.append("\r\n");
					first=false;
				}

				while ((nextLine=br.readLine())!=null){
					bw.append(nextLine);
					bw.append("\r\n");

				}
				br.close();
				isr.close();
				fis.close();
				br=null;
				isr=null;
				fis=null;

			}

			bw.close();

			if (outputfile.exists())
				outputfile.delete();
			fTmp.renameTo(outputfile) ;
			
			if (fTmp.exists())
				fTmp.delete();

		} catch (IOException e) {
			e.printStackTrace();
		}finally{

		}
	}

}