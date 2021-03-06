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
package com.tonelope.tennis.scoreprocessor.processor.scoring.set;

import com.tonelope.tennis.scoreprocessor.model.Match;
import com.tonelope.tennis.scoreprocessor.model.Set;

/**
 * 
 * @author Tony Lopez
 *
 */
public class NoFinalSetTiebreakSetCompletionHandler extends SetCompletionHandler<Set> {

	@Override
	public boolean test(Set scoringObject, Match match) {
		return match.isCurrentlyInFinalSet() && match.getMatchRules().isFinalSetTiebreakDisabled();
	}

	@Override
	protected boolean isComplete(Match match, Integer p1Games, Integer p2Games) {
		final int gamesNeeded = match.getMatchRules().getNumberOfGamesPerSet();
		if ((p1Games == gamesNeeded && p2Games < gamesNeeded - 1)) {
			return true;
		} else if (p2Games == gamesNeeded && p1Games < gamesNeeded - 1) {
			return true;
		} else if (p1Games + p2Games >= (2 * gamesNeeded) && Math.abs(p1Games - p2Games) > 1) {
			return true;
		}
		return false;
	}
}
