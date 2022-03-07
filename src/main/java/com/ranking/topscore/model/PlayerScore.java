package com.ranking.topscore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * The main model Entity for the Spring Boot Test: Top Score Ranking
 * 
 * @author janamadl
 *
 */
@Entity
@Table(name = "player_score")
public class PlayerScore {

	/**
	 * The score id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * The player's name.
	 */
	@Column(name = "player_name")
	private String name;
	/**
	 * The score, should be higher than 0
	 */
	@Min(value = 1, message = "Score should be higher than 0")
	private Integer score;
	/**
	 * The score date, format yyyy-MM-dd HH:mm:ss
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "score_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING)
	private Date scoreDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public PlayerScore(Integer id, String name, Integer score, Date scoreDate) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
		this.scoreDate = scoreDate;
	}

	public PlayerScore() {
	}
}
