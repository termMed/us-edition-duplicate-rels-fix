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

package com.termmed.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class Parameters.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameters {

	/** The rf2 core snapshot stated rels. */
	private String rf2CoreSnapshotStatedRels;
	
	/** The rf2 core snapshot concepts. */
	private String rf2CoreSnapshotConcepts;

	/** The rf2 extension snapshot stated rels. */
	private String rf2ExtensionSnapshotStatedRels;
	private String rf2ExtensionFullStatedRels;
	private String rf2ExtensionDeltaStatedRels;
	
	/** The release date. */
	private String releaseDate;

	/**
	 * Gets the rf2 core snapshot stated rels.
	 *
	 * @return the rf2 core snapshot stated rels
	 */
	public String getRf2CoreSnapshotStatedRels() {
		return rf2CoreSnapshotStatedRels;
	}

	/**
	 * Sets the rf2 core snapshot stated rels.
	 *
	 * @param rf2CoreSnapshotStatedRels the new rf2 core snapshot stated rels
	 */
	public void setRf2CoreSnapshotStatedRels(String rf2CoreSnapshotStatedRels) {
		this.rf2CoreSnapshotStatedRels = rf2CoreSnapshotStatedRels;
	}

	/**
	 * Gets the rf2 extension snapshot stated rels.
	 *
	 * @return the rf2 extension snapshot stated rels
	 */
	public String getRf2ExtensionSnapshotStatedRels() {
		return rf2ExtensionSnapshotStatedRels;
	}

	/**
	 * Sets the rf2 extension snapshot stated rels.
	 *
	 * @param rf2ExtensionSnapshotStatedRels the new rf2 extension snapshot stated rels
	 */
	public void setRf2ExtensionSnapshotStatedRels(String rf2ExtensionSnapshotStatedRels) {
		this.rf2ExtensionSnapshotStatedRels = rf2ExtensionSnapshotStatedRels;
	}

	/**
	 * Gets the rf2 core snapshot concepts.
	 *
	 * @return the rf2 core snapshot concepts
	 */
	public String getRf2CoreSnapshotConcepts() {
		return rf2CoreSnapshotConcepts;
	}

	/**
	 * Sets the rf2 core snapshot concepts.
	 *
	 * @param rf2CoreSnapshotConcepts the new rf2 core snapshot concepts
	 */
	public void setRf2CoreSnapshotConcepts(String rf2CoreSnapshotConcepts) {
		this.rf2CoreSnapshotConcepts = rf2CoreSnapshotConcepts;
	}

	/**
	 * Gets the release date.
	 *
	 * @return the release date
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Sets the release date.
	 *
	 * @param releaseDate the new release date
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getRf2ExtensionFullStatedRels() {
		return rf2ExtensionFullStatedRels;
	}

	public void setRf2ExtensionFullStatedRels(String rf2ExtensionFullStatedRels) {
		this.rf2ExtensionFullStatedRels = rf2ExtensionFullStatedRels;
	}

	public String getRf2ExtensionDeltaStatedRels() {
		return rf2ExtensionDeltaStatedRels;
	}

	public void setRf2ExtensionDeltaStatedRels(String rf2ExtensionDeltaStatedRels) {
		this.rf2ExtensionDeltaStatedRels = rf2ExtensionDeltaStatedRels;
	}
	
	
}
