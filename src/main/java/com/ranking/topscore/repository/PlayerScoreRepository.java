package com.ranking.topscore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ranking.topscore.model.PlayerScore;

/**
 * The main Repository for the Spring Boot Test: Top Score Ranking
 * 
 * @author janamadl
 *
 */
@Repository
public interface PlayerScoreRepository
		extends JpaRepository<PlayerScore, Integer>, JpaSpecificationExecutor<PlayerScore> {

	/**
	 * The method to find score record by name ignoring case.
	 * 
	 * @param name the player's name.
	 * @return List of player's score records.
	 */
	public List<PlayerScore> findByNameIgnoreCase(String name);

	/**
	 * The method to find top score record based on name ignoring case, score
	 * descending order and score date descending order.
	 * 
	 * @param name the player's name.
	 * @return PlayerScore - the score record of a player.
	 */
	public PlayerScore findTopByNameIgnoreCaseOrderByScoreDescScoreDateDesc(String name);

	/**
	 * The method to find top score record based on name ignoring case, score
	 * ascending order and score date ascending order.
	 * 
	 * @param name the player's name.
	 * @return PlayerScore - the score record of a player.
	 */
	public PlayerScore findTopByNameIgnoreCaseOrderByScoreAscScoreDateAsc(String name);

	/**
	 * The method to find top score record based on name ignoring case, score
	 * ascending order.
	 * 
	 * @param name the player's name.
	 * @return List of player's score records.
	 */
	public List<PlayerScore> findByNameIgnoreCaseOrderByScoreDateAsc(String name);

	/**
	 * The method to get average score of a player's score record.
	 * @param name the player's name.
	 * @return Integer - the average score.
	 */
	@Query(value = "SELECT avg(score) FROM player_score WHERE upper(player_name) = upper(?1)", nativeQuery = true)
	public Integer avgScore(String name);
}
