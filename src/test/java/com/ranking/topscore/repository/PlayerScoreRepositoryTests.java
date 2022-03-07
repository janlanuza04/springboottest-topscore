package com.ranking.topscore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ranking.topscore.model.PlayerScore;

@DataJpaTest
public class PlayerScoreRepositoryTests {

	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Autowired
	private PlayerScoreRepository playerScoreRepository;

	@Test
	public void testFindByNameIgnoreCase() {
		List<PlayerScore> playerScoreList = playerScoreRepository.findByNameIgnoreCase("test1");
		assertTrue(playerScoreList.size() == 4);
	}

	@Test
	public void testFindByNameIgnoreCaseOrderByScoreDateAsc() {
		List<PlayerScore> playerScoreList = playerScoreRepository.findByNameIgnoreCaseOrderByScoreDateAsc("test1");
		assertTrue(playerScoreList.size() == 4);
	}

	@Test
	public void testFindTopByNameIgnoreCaseOrderByScoreDescScoreDateDesc() {
		Date expectedDate = null;
		try {
			expectedDate = formatter.parse("2021-05-01 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		PlayerScore playerScore = playerScoreRepository.findTopByNameIgnoreCaseOrderByScoreDescScoreDateDesc("test1");
		assertEquals(821, playerScore.getScore());
		assertEquals(expectedDate, playerScore.getScoreDate());
	}

	@Test
	public void testFindTopByNameIgnoreCaseOrderByScoreAscScoreDateAsc() {
		Date expectedDate = null;
		try {
			expectedDate = formatter.parse("2021-12-31 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		PlayerScore playerScore = playerScoreRepository.findTopByNameIgnoreCaseOrderByScoreAscScoreDateAsc("test1");
		assertEquals(101, playerScore.getScore());
		assertEquals(expectedDate, playerScore.getScoreDate());
	}

	@Test
	public void testAvgScore() {
		Integer avgScore = playerScoreRepository.avgScore("test1");
		assertEquals(421, avgScore);
	}
}
