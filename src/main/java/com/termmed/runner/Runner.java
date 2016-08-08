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
package com.termmed.runner;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.termmed.utils.FixRels;

// TODO: Auto-generated Javadoc
/**
 * The Class Runner.
 */
public class Runner {

	/** The logger. */
	private static Logger logger;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		if (args==null || args.length<1){
			logger.info("Error happened getting params.");
			System.exit(0);
		}
		Runner runner=new Runner(args[0]);
		try {
			runner.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** The file config. */
	private String fileConfig;
	
	/**
	 * Instantiates a new runner.
	 *
	 * @param fileConfig the file config
	 */
	public Runner(String fileConfig) {
		logger = Logger.getLogger("com.termmed.runner.Runner");
		this.fileConfig=fileConfig;
	}
	
	/**
	 * Execute.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void execute() throws ClassNotFoundException, IOException{
		
		FixRels fixRels=new FixRels(fileConfig);
		fixRels.execute();
		
	}
	
}
