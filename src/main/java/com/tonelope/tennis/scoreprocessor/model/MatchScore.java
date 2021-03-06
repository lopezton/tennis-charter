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

import java.util.ArrayList;
import java.util.List;

import com.tonelope.tennis.scoreprocessor.utils.ListUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Tony Lopez
 *
 */
@Getter @Setter
public class MatchScore extends Score {

	public static final String SEPARATOR = ", ";
	private List<SetScore> setScores = new ArrayList<>();
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.setScores.size() > 1) {
			for(int i = 0; i < setScores.size() - 1; i++) {
				sb.append(setScores.get(i)).append(SEPARATOR);
			}
		}
		if (!this.setScores.isEmpty()) {
			sb.append(ListUtils.getLast(this.setScores));
		}
		
		return sb.toString();
	}

}
