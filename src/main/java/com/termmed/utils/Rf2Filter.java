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

// TODO: Auto-generated Javadoc
/**
 * The Class Rf2Filter.
 */
public class Rf2Filter {

	/** The columns. */
	private int[] columns;
	
	/** The values. */
	private String[] values;
	
	/** The signs. */
	private String[] signs;

	/**
	 * Instantiates a new rf2 filter.
	 *
	 * @param signs the signs
	 * @param columns the columns
	 * @param values the values
	 */
	public Rf2Filter(String[] signs, int[] columns, String[] values) {
		this.signs=signs;
		this.columns=columns;
		this.values=values;
	}

	/**
	 * Match filter.
	 *
	 * @param spl the spl
	 * @return true, if successful
	 */
	public boolean matchFilter(String[] spl){

		if (signs!=null){
			for (int i=0;i<signs.length;i++){
				if (signs[i].equals(Constants.EQUAL)){
					if (!spl[columns[i]].equals(values[i])){
						return false;
					}
				}

			}
		}

		return true;
	}
}
