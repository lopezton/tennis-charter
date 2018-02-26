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
package com.tonelope.tennis.scoreprocessor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonelope.tennis.scoreprocessor.dao.MatchRepository;
import com.tonelope.tennis.scoreprocessor.model.Match;
import com.tonelope.tennis.scoreprocessor.model.Stroke;
import com.tonelope.tennis.scoreprocessor.processor.MatchProcessorResolver;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Tony Lopez
 *
 */
@Service
@Getter @Setter
public class LocalScoringUpdateService implements ScoringUpdateService {

	private final MatchProcessorResolver matchProcessorResolver;
	private final MatchRepository matchRepository;
	
	@Autowired
	public LocalScoringUpdateService(MatchRepository matchRepository,
			MatchProcessorResolver matchProcessorResolver) {
		this.matchRepository = matchRepository;
		this.matchProcessorResolver = matchProcessorResolver;
	}
	
	@Override
	public Match updateMatch(String matchId, Stroke stroke) {
		Match match = this.matchRepository.findById(matchId);
		this.matchProcessorResolver.get(match).addStrokeToMatch(match, stroke);
		return this.matchRepository.save(match);
	}
}