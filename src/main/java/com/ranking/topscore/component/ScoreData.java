package com.ranking.topscore.component;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * This is the component for a player's score record.
 * 
 * @author janamadl
 *
 */
@Component
public class ScoreData {

	/**
	 * The player's score.
	 */
	private Integer score;
	/**
	 * The date when the player's score was recorded. Date format yyyy-MM-dd
	 * HH:mm:ss
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING)
	private Date scoreDate;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getScoreDate() {
		return scoreDate;
	}

	public void setScoreDate(Date scoreDate) {
		this.scoreDate = scoreDate;
	}

	public ScoreData(Integer score, Date scoreDate) {
		super();
		this.score = score;
		this.scoreDate = scoreDate;
	}

	public ScoreData() {
	}
}
