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
package com.tonelope.tennis.scoreprocessor.processor.scoring.point;

import com.tonelope.tennis.scoreprocessor.model.Match;
import com.tonelope.tennis.scoreprocessor.model.Player;
import com.tonelope.tennis.scoreprocessor.model.Point;
import com.tonelope.tennis.scoreprocessor.model.SimplePoint;
import com.tonelope.tennis.scoreprocessor.model.Status;
import com.tonelope.tennis.scoreprocessor.model.Stroke;
import com.tonelope.tennis.scoreprocessor.processor.scoring.ScoreCompletionHandler;
import com.tonelope.tennis.scoreprocessor.utils.ListUtils;

/**
 * 
 * @author Tony Lopez
 *
 */
public abstract class PointCompletionHandler implements ScoreCompletionHandler<Point> {

	/*
	 * (non-Javadoc)
	 * @see com.tonelope.tennis.scoreprocessor.processor.scoring.ScoreCompletionHandler#apply(com.tonelope.tennis.scoreprocessor.model.Winnable, com.tonelope.tennis.scoreprocessor.model.Match)
	 */
	@Override
	public boolean apply(Point scoringObject, Match match) {
		
		Player winningPlayer = null;
		
		if (!(scoringObject instanceof SimplePoint)) {
			
			Stroke stroke = scoringObject.getCurrentStroke();
			if (stroke.isWinner()) {
				winningPlayer = stroke.getPlayer();
			} else if (stroke.isOutRallyShot()) {
				winningPlayer = ListUtils.getLast(scoringObject.getStrokes(), 2).getPlayer();
			} else if (stroke.isDoubleFault()) {
				winningPlayer = stroke.getPlayer().getOpposingPlayer(match.getPlayers());
			}
		} else {
			
			winningPlayer = scoringObject.getWinningPlayer();
		}
		
		if (null != winningPlayer) {
			scoringObject.setStatus(Status.COMPLETE);
			this.updateScore(scoringObject, match, winningPlayer);
			return true;
		}
		return false;
	}
}
