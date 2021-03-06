/**
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tonelope.tennis.scoreprocessor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tony Lopez
 *
 */
@Getter @Setter @ToString
public abstract class ScoringObject {

	protected Status status = Status.NOT_STARTED;
	
	public boolean isNotStarted() {
		return this.checkStatus(Status.NOT_STARTED);
	}
	
	public boolean isInProgress() {
		return this.checkStatus(Status.IN_PROGRESS);
	}
	
	public boolean isCompleted() {
		return this.checkStatus(Status.COMPLETE);
	}

	private boolean checkStatus(Status complete) {
		return complete.equals(this.status);
	}
}
