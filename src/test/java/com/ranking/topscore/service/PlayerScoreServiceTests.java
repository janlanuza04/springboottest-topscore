package com.ranking.topscore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ranking.topscore.component.PlayerScoreHistory;
import com.ranking.topscore.model.PlayerScore;
import com.ranking.topscore.repository.PlayerScoreRepository;

@ExtendWith(MockitoExtension.class)
public class PlayerScoreServiceTests {

	@InjectMocks
	private PlayerScoreService playerScoreService;

	@Mock
	private PlayerScoreRepository playerScoreRepository;

	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Test
	public void testList() throws Exception {
		Date date = formatter.parse("2021-12-31 23:59:59");
		Integer id = 1;
		Integer score = 101;
		String name = "test1";
		PlayerScore playerScore = new PlayerScore(id, name, score, date);
		List<PlayerScore> listPlayerScore = new ArrayList<PlayerScore>();
		listPlayerScore.add(playerScore);

		when(playerScoreRepository.findAll()).thenReturn(listPlayerScore);

		List<PlayerScore> fetchedPlayerScore = playerScoreService.listAll();
		assertThat(fetchedPlayerScore.size()).isGreaterThan(0);
		assertEquals(id, fetchedPlayerScore.get(0).getId());
	}

	@Test
	public void testSave() throws Exception {
		Date date = formatter.parse("2021-12-31 23:59:59");
		Integer id = 1;
		Integer score = 101;
		String name = "test1";
		PlayerScore playerScore = new PlayerScore(id, name, score, date);

		playerScoreRepository.save(playerScore);
		verify(playerScoreRepository, times(1)).save(playerScore);
	}

	@Test
	public void testGet() throws Exception {
		Date date = formatter.parse("2021-12-31 23:59:59");
		Integer id = 1;
		Integer score = 101;
		String name = "test1";
		Optional<PlayerScore> playerScore = Optional.of(new PlayerScore(id, name, score, date));

		when(playerScoreRepository.findById(id)).thenReturn(playerScore);

		PlayerScore fetchedPlayerScore = playerScoreService.get(id);
		assertEquals(id, fetchedPlayerScore.getId());
		assertEquals(name, fetchedPlayerScore.getName());
		assertEquals(score, fetchedPlayerScore.getScore());
		assertEquals(date, fetchedPlayerScore.getScoreDate());
	}

	@Test
	public void testDelete() throws ParseException {
		Date date = formatter.parse("2021-12-31 23:59:59");
		Integer id = 1;
		Integer score = 101;
		String name = "test1";
		PlayerScore playerScore = new PlayerScore(id, name, score, date);

		playerScoreRepository.save(playerScore);
		playerScoreRepository.deleteById(id);
		verify(playerScoreRepository, times(1)).deleteById(id);
	}

	@Test
	public void testPlayerScoreHistory() throws Exception {
		Date lowestScoreDate = formatter.parse("2021-12-31 23:59:59");
		Date highestScoreDate = formatter.parse("2021-11-27 23:59:59");
		String playerName = "test1";
		Integer lowestScore = 100;
		Integer highestScore = 200;
		Integer avgScore = 150;
		PlayerScore lowestPlayerScore = new PlayerScore(1, playerName, lowestScore, lowestScoreDate);
		PlayerScore highestPlayerScore = new PlayerScore(2, playerName, highestScore, highestScoreDate);
		List<PlayerScore> playerScoreList = new ArrayList<PlayerScore>();
		playerScoreList.add(highestPlayerScore);
		playerScoreList.add(lowestPlayerScore);

		when(playerScoreRepository.findTopByNameIgnoreCaseOrderByScoreAscScoreDateAsc(playerName))
				.thenReturn(lowestPlayerScore);
		when(playerScoreRepository.findTopByNameIgnoreCaseOrderByScoreDescScoreDateDesc(playerName))
				.thenReturn(highestPlayerScore);
		when(playerScoreRepository.findByNameIgnoreCaseOrderByScoreDateAsc(playerName)).thenReturn(playerScoreList);
		when(playerScoreRepository.avgScore(playerName)).thenReturn(avgScore);

		PlayerScoreHistory fetchedPlayerScoreHistory = playerScoreService.getPlayerScoreHistory(playerName);
		assertEquals(playerName, fetchedPlayerScoreHistory.getName());
		assertEquals(avgScore, fetchedPlayerScoreHistory.getAverageScore());
		assertEquals(2, fetchedPlayerScoreHistory.getScoreHistoryRecords().size());
		assertEquals(lowestScore, fetchedPlayerScoreHistory.getLowestScoreRecord().getScore());
		assertEquals(highestScore, fetchedPlayerScoreHistory.getHighestScoreRecord().getScore());
	}
}
