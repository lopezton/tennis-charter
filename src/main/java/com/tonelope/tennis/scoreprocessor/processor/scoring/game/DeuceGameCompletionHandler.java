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
package com.tonelope.tennis.scoreprocessor.processor.scoring.game;

import com.tonelope.tennis.scoreprocessor.model.Game;
import com.tonelope.tennis.scoreprocessor.model.GameScore;
import com.tonelope.tennis.scoreprocessor.model.Match;
import com.tonelope.tennis.scoreprocessor.model.PointValue;
import com.tonelope.tennis.scoreprocessor.model.Score;
import com.tonelope.tennis.scoreprocessor.model.Set;

/**
 * 
 * @author Tony Lopez
 *
 */
public class DeuceGameCompletionHandler extends GameCompletionHandler<Game> {

	@Override
	protected boolean isComplete(Score score) {
		GameScore gameScore = (GameScore) score;
		return PointValue.GAME.equals(gameScore.getServerScore()) || PointValue.GAME.equals(gameScore.getReceiverScore());
	}

	@Override
	public boolean test(Game scoringObject, Match match) {
		Set currentSet = match.getCurrentSet();
		if (currentSet.getGames().size() < 13 || this.isFinalSetWinByTwo(currentSet, match)) {
			return !match.getMatchRules().isNoAdScoring();
		}
		return false;
	}
}
