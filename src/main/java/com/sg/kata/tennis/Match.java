package com.sg.kata.tennis;

import static com.sg.kata.tennis.Constants.*;

public class Match {

	private Player playerOne;
	private Player playerTwo;
	private boolean deuce;
	private boolean tieBreak;
	private boolean theEnd;

	public Match() {
		this.playerOne = new Player(NAME_PLAYER_ONE);
		this.playerTwo = new Player(NAME_PLAYER_TWO);
	}

	public Match(Player playerOne, Player playerTow) {
		this.playerOne = playerOne;
		this.playerTwo = playerTow;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public boolean isDeuce() {
		return deuce;
	}

	public void setDeuce(boolean deuce) {
		this.deuce = deuce;
	}

	public boolean isTieBreak() {
		return tieBreak;
	}

	public void setTieBreak(boolean tieBreak) {
		this.tieBreak = tieBreak;
	}

	public boolean isTheEnd() {
		return theEnd;
	}

	public void setTheEnd(boolean theEnd) {
		this.theEnd = theEnd;
	}

	public void manageEndOfTheGame() {

		if (isAPlayerReachTheMinGameScoreToWinAMatch() && isGameGapAllowsWinASet()) {
			this.setTheEnd(true);
		}

	}

	private boolean isGameGapAllowsWinASet() {

		return (Math.abs(this.playerTwo.getSetScore() - this.playerOne.getSetScore()) >= MIN_GAMES_GAP_TO_WIN_A_MATCH);
	}

	public Boolean isDeuceActivated() {

		if (this.playerTwo.getPointScore() >= FORTY && this.playerOne.getPointScore() >= FORTY) {
			this.setDeuce(true);
			return true;
		} else {
			return false;
		}
	}

	public void isTieBreakActivated() {

		if (this.playerTwo.getSetScore() >= TIEBREAK_SET_SCORE && this.playerOne.getSetScore() >= TIEBREAK_SET_SCORE) {
			this.setTieBreak(true);

		}
	}

	public Player getLeadPlayer() {
		return (playerOne.getSetScore() > playerTwo.getSetScore()) ? playerOne : playerTwo;
	}

	public Player getPointScoreLeadPlayer() {
		return (playerOne.getPointScore() > playerTwo.getPointScore()) ? playerOne : playerTwo;
	}

	public void displayMatchScore() {
		StringBuilder scorePlayerOne = displayPlayerScore(this.playerOne);
		StringBuilder scorePlayerTow = displayPlayerScore(this.playerTwo);

		System.out.println(scorePlayerOne);
		System.out.println(scorePlayerTow);
		System.out.println(DELIMITER);
		System.out.println(getMatchSet());
	}

	private StringBuilder displayPlayerScore(Player player) {
		StringBuilder scorePlayer = new StringBuilder();
		scorePlayer.append(player.getName());
		scorePlayer.append(SET);
		scorePlayer.append(player.getSetScore());
		scorePlayer.append(POINTS);
		if (this.isDeuce() && player.isAdvantage()) {
			scorePlayer.append(ADV);
		} else {
			scorePlayer.append(player.getPointScore());
		}
		scorePlayer.append(ACCOLADE);
		return scorePlayer;
	}

	public String getMatchSet() {
		if (isAPlayerReachTheMinGameScoreToWinAMatch() && isGameGapAllowsWinASet()) {
			return getLeadPlayer().getName() + WON;

		} else {

			return this.playerOne.getSetScore() + ", " + this.playerTwo.getSetScore();
		}
	}

	private boolean isAPlayerReachTheMinGameScoreToWinAMatch() {
		return this.playerOne.getSetScore() >= MIN_SET_SCORE_TO_WIN_A_MATCH
				|| this.playerTwo.getSetScore() >= MIN_SET_SCORE_TO_WIN_A_MATCH;
	}

	public void resetPlayersScores() {

		if (this.playerOne.isRest() || this.playerTwo.isRest()) {

			this.playerOne.setPointScore(LOVE);
			this.playerTwo.setPointScore(LOVE);
			this.playerOne.setRest(false);
			this.playerOne.setAdvantage(false);
			this.playerTwo.setRest(false);
			this.playerTwo.setAdvantage(false);

			this.setDeuce(false);

		}
	}

}
