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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tonelope.tennis.scoreprocessor.model.FrameworkException;
import com.tonelope.tennis.scoreprocessor.model.Game;
import com.tonelope.tennis.scoreprocessor.model.Match;
import com.tonelope.tennis.scoreprocessor.model.Point;
import com.tonelope.tennis.scoreprocessor.model.Set;
import com.tonelope.tennis.scoreprocessor.model.TiebreakGame;
import com.tonelope.tennis.scoreprocessor.model.Winnable;
import com.tonelope.tennis.scoreprocessor.processor.scoring.game.DeuceGameCompletionStrategy;
import com.tonelope.tennis.scoreprocessor.processor.scoring.game.NoAdGameCompletionStrategy;
import com.tonelope.tennis.scoreprocessor.processor.scoring.game.TiebreakGameCompletionStrategy;
import com.tonelope.tennis.scoreprocessor.processor.scoring.match.DefaultMatchCompletionStrategy;
import com.tonelope.tennis.scoreprocessor.processor.scoring.point.DefaultPointCompletionStrategy;
import com.tonelope.tennis.scoreprocessor.processor.scoring.point.TiebreakPointCompletionStrategy;
import com.tonelope.tennis.scoreprocessor.processor.scoring.set.DefaultSetCompletionStrategy;
import com.tonelope.tennis.scoreprocessor.processor.scoring.set.NoFinalSetTiebreakSetCompletionStrategy;

import lombok.Getter;

/**
 * 
 * @author Tony Lopez
 *
 */
@Getter
public class DefaultScoreCompletionStrategyResolver implements ScoreCompletionStrategyResolver {

	private final Map<Class<?>, List<ScoreCompletionStrategy<Winnable>>> scoreCompletionStrategies;
	
	public DefaultScoreCompletionStrategyResolver() {
		this(null);
	}
	
	public DefaultScoreCompletionStrategyResolver(Map<Class<?>, List<ScoreCompletionStrategy<Winnable>>> scoreCompletionStrategies) {
		if (null != scoreCompletionStrategies) {
			this.scoreCompletionStrategies = scoreCompletionStrategies;
		} else {
			this.scoreCompletionStrategies = this.createDefaultScoreCompletionStrategies();
		}
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> Map<Class<?>, List<T>> createDefaultScoreCompletionStrategies() {
		// TODO Refactor
		Map<Class<?>, List<T>> map = new HashMap<>();
		map.put(Point.class, (List<T>) Stream.of(new DefaultPointCompletionStrategy(), new TiebreakPointCompletionStrategy()).collect(Collectors.toList()));
		map.put(Game.class, (List<T>) Stream.of(new DeuceGameCompletionStrategy(), new NoAdGameCompletionStrategy()).collect(Collectors.toList()));
		map.put(TiebreakGame.class, (List<T>) Stream.of(new TiebreakGameCompletionStrategy()).collect(Collectors.toList()));
		map.put(Set.class, (List<T>) Stream.of(new DefaultSetCompletionStrategy(), new NoFinalSetTiebreakSetCompletionStrategy()).collect(Collectors.toList()));
		map.put(Match.class, (List<T>) Stream.of(new DefaultMatchCompletionStrategy()).collect(Collectors.toList()));
		return map;
	}

	private ScoreCompletionStrategy<Winnable> getStrategy(Winnable scoringObject, Match match) {
		List<ScoreCompletionStrategy<Winnable>> acceptableStrategies = this.scoreCompletionStrategies.get(scoringObject.getClass()).stream()
				.filter(s -> s.test(scoringObject, match))
				.collect(Collectors.toList());
		
		if (acceptableStrategies.isEmpty()) {
			throw new FrameworkException("Failed to find score completion strategy for " + scoringObject);
		} else if (acceptableStrategies.size() > 1) {
			throw new FrameworkException("Expected to find only (1) score completion strategy but found " + acceptableStrategies.size() + " for " + scoringObject);
		} else {
			return acceptableStrategies.get(0);
		}
	}

	/* (non-Javadoc)
	 * @see com.tonelope.tennis.scoreprocessor.processor.scoring.ScoreCompletionStrategyResolver#resolve(com.tonelope.tennis.scoreprocessor.model.Winnable, com.tonelope.tennis.scoreprocessor.model.Match)
	 */
	@Override
	public boolean resolve(Winnable scoringObject, Match match) {
		return this.getStrategy(scoringObject, match).apply(scoringObject, match);
	}
}
