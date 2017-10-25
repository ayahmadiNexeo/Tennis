package com.sg.kata.tennis;

import static com.sg.kata.tennis.Constants.FORTY;
import static com.sg.kata.tennis.Constants.MIN_POINTS_GAP_TO_WIN_A_GAME;
import static com.sg.kata.tennis.Constants.THIRTY;

public class Player {

	private String name;
	private boolean advantage;
	private int pointScore;
	private int setScore;
	private boolean isRest;

	public Player(String name) {
		this.name = name;
	}

	public void winSet() {
		this.isRest = true;
		this.setScore = this.setScore + 1;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPointScore() {
		return pointScore;
	}

	public void setPointScore(int pointScore) {
		this.pointScore = pointScore;
	}

	public int getSetScore() {
		return setScore;
	}

	public void setSetScore(int setScore) {
		this.setScore = setScore;
	}

	public boolean isAdvantage() {
		return advantage;
	}

	public void setAdvantage(boolean advantage) {
		this.advantage = advantage;
	}

	public boolean isRest() {
		return isRest;
	}

	public void setRest(boolean isRest) {
		this.isRest = isRest;
	}
	
	public void winPoint( Player looser) {

		if (this.getPointScore() < THIRTY) {
			this.setPointScore(this.getPointScore() + 15);
		} else if (this.getPointScore() == THIRTY) {
			this.setPointScore(FORTY);
		} else if (looser.isAdvantage()) {
			looser.setAdvantage(false);
		} else {
			boolean isGreatThenTenScoreDiffrence = checkScoreDifference( looser);
			if (this.isAdvantage() || isGreatThenTenScoreDiffrence) {
				this.winSet();
			} else {
				this.setAdvantage(true);
			}
		}

	}

	public Boolean checkScoreDifference( Player looser) {
		boolean isWinner = false;
		if (this.getPointScore() == FORTY) {
			if (this.getPointScore() - looser.getPointScore() >= MIN_POINTS_GAP_TO_WIN_A_GAME) {
				isWinner = true;
			}
		}

		return isWinner;
	}
}
