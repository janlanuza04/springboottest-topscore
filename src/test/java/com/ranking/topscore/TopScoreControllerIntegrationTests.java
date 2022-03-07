package com.ranking.topscore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.ranking.topscore.component.PlayerScoreHistory;
import com.ranking.topscore.model.PlayerScore;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TopScoreControllerIntegrationTests {

	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testListAll() {
		ResponseEntity<List<PlayerScore>> responseEntity = this.restTemplate.exchange(
				"http://localhost:" + port + "/playerscores", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlayerScore>>() {
				});
		assertEquals(10, responseEntity.getBody().size());
	}

	@Test
	public void testGetPlayesScoreById() {
		Date expectedDate = null;
		try {
			expectedDate = formatter.parse("2021-12-31 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer expectedId = 1;
		Integer expectedScore = 101;
		String expectedName = "test1";

		ResponseEntity<PlayerScore> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/playerscores/1", PlayerScore.class);
		assertTrue(responseEntity != null);
		PlayerScore outputPlayerScoure = responseEntity.getBody();

		assertEquals(expectedId, outputPlayerScoure.getId());
		assertEquals(expectedName, outputPlayerScoure.getName());
		assertEquals(expectedScore, outputPlayerScoure.getScore());
		assertEquals(expectedDate, outputPlayerScoure.getScoreDate());
	}

	@Test
	public void testAddPlayerScore() {
		String dateInString = "2020-01-01 00:00:00";
		Date date = null;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PlayerScore playerScore = new PlayerScore(null, "testName", 100, date);
		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/playerscores", playerScore, String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testGetPlayerScoreHistory() {
		Date expectedLowestScoreDate = null;
		Date expectedHighestScoreDate = null;
		try {
			expectedLowestScoreDate = formatter.parse("2021-12-31 23:59:59");
			expectedHighestScoreDate = formatter.parse("2021-05-01 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer expectedLowestScore = 101;
		Integer expectedHighestScore = 821;
		Integer averageScore = 421;
		String expectedName = "test1";

		ResponseEntity<PlayerScoreHistory> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/playerscores/history/test1", PlayerScoreHistory.class);

		PlayerScoreHistory outputPlayerScoreHistory = responseEntity.getBody();

		assertEquals(expectedName, outputPlayerScoreHistory.getName());
		assertEquals(averageScore, outputPlayerScoreHistory.getAverageScore());
		assertEquals(expectedLowestScore, outputPlayerScoreHistory.getLowestScoreRecord().getScore());
		assertEquals(expectedLowestScoreDate, outputPlayerScoreHistory.getLowestScoreRecord().getScoreDate());
		assertEquals(expectedHighestScore, outputPlayerScoreHistory.getHighestScoreRecord().getScore());
		assertEquals(expectedHighestScoreDate, outputPlayerScoreHistory.getHighestScoreRecord().getScoreDate());
		assertEquals(4, outputPlayerScoreHistory.getScoreHistoryRecords().size());
	}

	@Test
	public void testSearchPlayerScoreOnlyName() {
		ResponseEntity<List<PlayerScore>> responseEntity = this.restTemplate.exchange(
				"http://localhost:" + port + "/playerscores/search?name=test1", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlayerScore>>() {
				});
		List<PlayerScore> outputList = responseEntity.getBody();
		assertEquals(4, outputList.size());
	}

	@Test
	public void testSearchPlayerScoreNameWithPagination() {
		ResponseEntity<List<PlayerScore>> responseEntity = this.restTemplate.exchange(
				"http://localhost:" + port + "/playerscores/search?name=test1&pageNo=0&pageSize=1", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<PlayerScore>>() {
				});
		List<PlayerScore> outputList = responseEntity.getBody();
		assertEquals(1, outputList.size());
	}

	@Test
	public void testSearchPlayerScoreNameDateBefore() {
		ResponseEntity<List<PlayerScore>> responseEntity = this.restTemplate.exchange(
				"http://localhost:" + port + "/playerscores/search?name=test1&dateBefore=2021-04-30", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<PlayerScore>>() {
				});
		List<PlayerScore> outputList = responseEntity.getBody();
		assertEquals(1, outputList.size());
	}

	@Test
	public void testSearchPlayerScoreNameDateAfter() {

		ResponseEntity<List<PlayerScore>> responseEntity = this.restTemplate.exchange(
				"http://localhost:" + port + "/playerscores/search?name=test1&dateAfter=2012-12-31", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<PlayerScore>>() {
				});
		List<PlayerScore> outputList = responseEntity.getBody();
		assertEquals(4, outputList.size());
	}

	@Test
	public void testSearchPlayerScoreNameDateBetween() {

		ResponseEntity<List<PlayerScore>> responseEntity = this.restTemplate.exchange(
				"http://localhost:" + port + "/playerscores/search?name=test1&dateBefore=2021-12-31&dateAfter=2021-01-31",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<PlayerScore>>() {
				});
		List<PlayerScore> outputList = responseEntity.getBody();
		assertEquals(3, outputList.size());
	}
}
