package com.ranking.topscore.component;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * This is the component for passing the player score history records.
 * 
 * @author janamadl
 * 
 */
@Component
public class PlayerScoreHistory {
	/**
	 * The player's name.
	 */
	private String name;
	/**
	 * The player's average score.
	 */
	private Integer averageScore;
	/**
	 * The player's lowest score record.
	 */
	private ScoreData lowestScoreRecord;
	/**
	 * The player's highest score record.
	 */
	private ScoreData highestScoreRecord;
	/**
	 * The player's all score record.
	 */
	private List<ScoreData> scoreHistoryRecords;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(Integer averageScore) {
		this.averageScore = averageScore;
	}

	public ScoreData getLowestScoreRecord() {
		return lowestScoreRecord;
	}

	public void setLowestScoreRecord(ScoreData lowestScoreRecord) {
		this.lowestScoreRecord = lowestScoreRecord;
	}

	public ScoreData getHighestScoreRecord() {
		return highestScoreRecord;
	}

	public void setHighestScoreRecord(ScoreData highestScoreRecord) {
		this.highestScoreRecord = highestScoreRecord;
	}

	public List<ScoreData> getScoreHistoryRecords() {
		return scoreHistoryRecords;
	}

	public void setScoreHistoryRecords(List<ScoreData> scoreHistoryRecords) {
		this.scoreHistoryRecords = scoreHistoryRecords;
	}

	public PlayerScoreHistory(String name, Integer averageScore, ScoreData lowestScoreRecord,
			ScoreData highestScoreRecord, List<ScoreData> scoreHistoryRecords) {
		super();
		this.name = name;
		this.averageScore = averageScore;
		this.lowestScoreRecord = lowestScoreRecord;
		this.highestScoreRecord = highestScoreRecord;
		this.scoreHistoryRecords = scoreHistoryRecords;
	}

	public PlayerScoreHistory() {
	}
}
