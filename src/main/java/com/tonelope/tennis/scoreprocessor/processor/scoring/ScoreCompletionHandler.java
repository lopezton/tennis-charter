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
package com.tonelope.tennis.scoreprocessor.processor.scoring;

import com.tonelope.tennis.scoreprocessor.model.Match;
import com.tonelope.tennis.scoreprocessor.model.Player;
import com.tonelope.tennis.scoreprocessor.model.Winnable;

/**
 * 
 * @author Tony Lopez
 *
 * @param <T> The <tt>Winnable</tt> type.
 */
public interface ScoreCompletionHandler<T extends Winnable> {

	boolean test(T scoringObject, Match match);
	
	boolean apply(T scoringObject, Match match);
	
	void updateScore(T scoringObject, Match match, Player winningPlayer);
}