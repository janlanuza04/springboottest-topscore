package com.ranking.topscore.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranking.topscore.model.PlayerScore;
import com.ranking.topscore.service.PlayerScoreService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TopScoreController.class)
public class TopScoreControllerTests {

	@MockBean
	PlayerScoreService playerScoreService;

	@Autowired
	MockMvc mockMvc;

	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Test
	public void testListAll() throws Exception {
		Date date = formatter.parse("2021-12-31 23:59:59");
		Integer id = 1;
		Integer score = 101;
		String name = "test1";
		PlayerScore playerScore = new PlayerScore(id, name, score, date);
		List<PlayerScore> listPlayerScore = new ArrayList<PlayerScore>();
		listPlayerScore.add(playerScore);

		when(playerScoreService.listAll()).thenReturn(listPlayerScore);

		mockMvc.perform(get("/playerscores")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].name", Matchers.is("test1")));

	}

	@Test
	public void testAddPlayerScore() throws Exception {
		Date date = formatter.parse("2021-12-31 23:59:59");
		Integer id = 11;
		Integer score = 101;
		String name = "test1";
		PlayerScore playerScore = new PlayerScore(id, name, score, date);
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(playerScore);

		mockMvc.perform(post("/playerscores").contentType("application/json").content(inputJson))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteById() throws Exception {

		mockMvc.perform(delete("/playerscores/1")).andExpect(status().isOk());
	}
}
