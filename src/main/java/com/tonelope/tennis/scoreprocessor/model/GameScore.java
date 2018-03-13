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

/**
 * 
 * @author Tony Lopez
 *
 */
@Getter @Setter
public class GameScore extends Score {

	public static final String SEPARATOR = " - ";
	
	private PointValue serverScore = PointValue.LOVE;
	private PointValue receiverScore = PointValue.LOVE;
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append(serverScore.getValue())
				.append(SEPARATOR)
				.append(receiverScore.getValue())
				.toString();
	}

	public boolean isDeuce() {
		return PointValue.FORTY.equals(this.serverScore) && PointValue.FORTY.equals(this.receiverScore);
	}
}
