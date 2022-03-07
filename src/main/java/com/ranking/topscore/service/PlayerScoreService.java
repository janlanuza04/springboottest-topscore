package com.ranking.topscore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ranking.topscore.component.PlayerScoreHistory;
import com.ranking.topscore.component.ScoreData;
import com.ranking.topscore.model.PlayerScore;
import com.ranking.topscore.repository.PlayerScoreRepository;

/**
 * The main Service for the Spring Boot Test: Top Score Ranking
 * 
 * @author janamadl
 *
 */
@Service
public class PlayerScoreService {

	/**
	 * The repository used in this service.
	 */
	@Autowired
	private PlayerScoreRepository playerScoreRepo;

	/**
	 * The method to get all player's score record
	 * 
	 * @return List of player's score records.
	 */
	public List<PlayerScore> listAll() {
		return playerScoreRepo.findAll();
	}

	/**
	 * The method to save a player's score record.
	 * 
	 * @param playerScore the player's score record.
	 */
	public void save(PlayerScore playerScore) {
		playerScoreRepo.save(playerScore);
	}

	/**
	 * The method to get a score record based on score ID for a player.
	 * 
	 * @param id the score ID.
	 * @return PlayerScore - the score record of a player.
	 */
	public PlayerScore get(Integer id) {
		return playerScoreRepo.findById(id).get();
	}

	/**
	 * The method to delete a score record based on score ID for a player.
	 * 
	 * @param id the score ID.
	 */
	public void delete(Integer id) {
		playerScoreRepo.deleteById(id);
	}

	/**
	 * The method to get a player's score history. Score History contains: average
	 * score, lowest score record, highest score record, and all score record of the
	 * player.
	 * 
	 * @param name the player's name.
	 * @return PlayerScoreHistory - contains the score history of a player.
	 */
	public PlayerScoreHistory getPlayerScoreHistory(String name) {
		PlayerScoreHistory playerScoreHistory = new PlayerScoreHistory();
		playerScoreHistory.setName(name);
		Integer avgScore = playerScoreRepo.avgScore(name);
		playerScoreHistory.setAverageScore(avgScore);

		PlayerScore lowestScoreRecord = playerScoreRepo.findTopByNameIgnoreCaseOrderByScoreAscScoreDateAsc(name);
		PlayerScore highestScoreRecord = playerScoreRepo.findTopByNameIgnoreCaseOrderByScoreDescScoreDateDesc(name);

		playerScoreHistory
				.setLowestScoreRecord(new ScoreData(lowestScoreRecord.getScore(), lowestScoreRecord.getScoreDate()));
		playerScoreHistory
				.setHighestScoreRecord(new ScoreData(highestScoreRecord.getScore(), highestScoreRecord.getScoreDate()));

		List<ScoreData> scoreDataList = new ArrayList<ScoreData>();
		for (PlayerScore playerScore : playerScoreRepo.findByNameIgnoreCaseOrderByScoreDateAsc(name)) {
			scoreDataList.add(new ScoreData(playerScore.getScore(), playerScore.getScoreDate()));
		}

		playerScoreHistory.setScoreHistoryRecords(scoreDataList);

		return playerScoreHistory;

	}

	/**
	 * The method to search a player's score record by using name, date before or
	 * after, between dates.
	 * 
	 * @param playerScoreSpec contains the search parameters.
	 * @param pageNo          the page number.
	 * @param pageSize        the page size.
	 * @return List of player's score records.
	 */
	public List<PlayerScore> searchPlayerScore(Specification<PlayerScore> playerScoreSpec, Integer pageNo,
			Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		System.out.println("PLAYERSCORESPEC:" + playerScoreSpec.toString());
		Page<PlayerScore> playerScoreResult = playerScoreRepo.findAll(playerScoreSpec, paging);
		return playerScoreResult.getContent();
	}
}
