package com.ranking.topscore.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ranking.topscore.component.PlayerScoreHistory;
import com.ranking.topscore.model.PlayerScore;
import com.ranking.topscore.service.PlayerScoreService;

import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LessThan;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * The main RestController for the Spring Boot Test: Top Score Ranking
 * 
 * @author janamadl
 *
 */
@RestController
public class TopScoreController {

	/**
	 * The PlayerScoreService.
	 */
	@Autowired
	private PlayerScoreService playerScoreService;

	/**
	 * The method to get all players' score records.
	 * 
	 * @return List of player's score records.
	 */
	@GetMapping("/playerscores")
	public List<PlayerScore> listAll() {
		return playerScoreService.listAll();
	}

	/**
	 * The method to add a player's score record.
	 * 
	 * @param playerScore the player score object to save.
	 */
	@PostMapping("/playerscores")
	public void addPlayerScore(@RequestBody PlayerScore playerScore) {
		playerScoreService.save(playerScore);
	}

	/**
	 * The method to get a score record using the score ID for a player.
	 * 
	 * @param id the score ID.
	 * @return ResponseEntity contains player score object, response is in JSON
	 *         format.
	 */
	@GetMapping("/playerscores/{id}")
	public ResponseEntity<PlayerScore> getById(@PathVariable Integer id) {
		try {
			PlayerScore playerScore = playerScoreService.get(id);
			return new ResponseEntity<PlayerScore>(playerScore, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<PlayerScore>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * The method delete a score record using the score ID for a player.
	 * 
	 * @param id the score ID.
	 */
	@DeleteMapping("/playerscores/{id}")
	public void deleteById(@PathVariable Integer id) {
		playerScoreService.delete(id);
	}

	/**
	 * The method to get a player's score history. Score History contains: average
	 * score, lowest score record, highest score record, and all score record of the
	 * player.
	 * 
	 * @param name the player's name
	 * @return PlayerScoreHistory - contains the score history of a player.
	 */
	@GetMapping("/playerscores/history/{name}")
	public PlayerScoreHistory getPlayerScoreHistory(@PathVariable String name) {
		return playerScoreService.getPlayerScoreHistory(name);
	}

	/**
	 * The method to search a player's score record by using name, date before or
	 * after, between dates. Response can be pageable by passing pageNo and
	 * pageSize.
	 * 
	 * @param spec     specification argument for the search.
	 * @param pageNo   the page number.
	 * @param pageSize the page size.
	 * @return List of player's score record based on the search parameters.
	 */
	@GetMapping("/playerscores/search")
	public List<PlayerScore> searchPlayerScore(@And({
			@Spec(path = "name", params = "name", paramSeparator = ',', spec = In.class),
			@Spec(path = "scoreDate", params = "dateAfter", spec = GreaterThan.class, config = "yyyy-MM-dd"),
			@Spec(path = "scoreDate", params = "dateBefore", spec = LessThan.class, config = "yyyy-MM-dd") }) Specification<PlayerScore> spec,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "100") Integer pageSize) {
		return playerScoreService.searchPlayerScore(spec, pageNo, pageSize);
	}
}
