package com.sg.kata.tennis;

public class Game {

	private Player playerOne;
	private Player playerTow;
	private boolean deuce;
	private boolean tieBreak;
	private boolean theEnd;

	public Game(Player playerOne, Player playerTow) {
		this.playerOne = playerOne;
		this.playerTow = playerTow;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTow() {
		return playerTow;
	}

	public void setPlayerTow(Player playerTow) {
		this.playerTow = playerTow;
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

	public String getTennisSet() {
		if (this.playerOne.getSetScore() >= 5 && this.playerTow.getSetScore() >= 5) {
			if (Math.abs(this.playerTow.getSetScore() - this.playerOne.getSetScore()) >= 2) {
				return getLeadPlayer().getName() + Constants.WON;
			} else if (this.playerOne.getSetScore() == this.playerTow.getSetScore()) {
				return Constants.EQUABILITY;
			} else {
				return getLeadPlayer().getName() + Constants.ADVENCED;
			}
		} else {
			if (this.playerOne.getSetScore() == 6 || this.playerTow.getSetScore() == 6) {
				return getLeadPlayer().getName() + Constants.WON;
			} else {
				return this.playerOne.getSetScore() + ", " + this.playerTow.getSetScore();
			}
		}
	}

	public Boolean checkScoreDifference(Player winner, Player looser) {
		boolean isWinner = false;
		if (winner.getPointScore() == 40) {
			if (winner.getPointScore() - looser.getPointScore() >= 10) {
				isWinner = true;
			}
		}

		return isWinner;
	}

	public void manageEndOfTheGame() {
		if (this.playerOne.getSetScore() >= 5 && this.playerTow.getSetScore() >= 5) {
			if (Math.abs(this.playerTow.getSetScore() - this.playerOne.getSetScore()) >= 2) {
				this.setTheEnd(true);
			}
		} else {
			if ((this.playerOne.getSetScore() == 6 || this.playerTow.getSetScore() == 6)
					&& Math.abs(this.playerTow.getSetScore() - this.playerOne.getSetScore()) >= 2) {
				this.setTheEnd(true);
			}
		}

	}

	public Boolean isDeuceActivated() {

		if (this.playerTow.getPointScore() >= 40 && this.playerOne.getPointScore() >= 40) {
			this.setDeuce(true);
			return true;
		} else {
			return false;
		}
	}

	public void isTieBreakActivated() {

		if (this.playerTow.getSetScore() >= 6 && this.playerOne.getSetScore() >= 6) {
			this.setTieBreak(true);

		}
	}

	public void winBall(Player winner, Player looser) {

		if (winner.getPointScore() < 30) {
			winner.setPointScore(winner.getPointScore() + 15);
		} else if (winner.getPointScore() == 30) {
			winner.setPointScore(40);
		} else if (looser.isAdvanced()) {
			looser.setAdvanced(false);
		} else {
			boolean isGreatThenTenScoreDiffrence = checkScoreDifference(winner, looser);
			if (winner.isAdvanced() || isGreatThenTenScoreDiffrence) {
				winner.winSet();
			} else {
				winner.setAdvanced(true);
			}
		}

	}

	public Player getLeadPlayer() {
		return (playerOne.getSetScore() > playerTow.getSetScore()) ? playerOne : playerTow;
	}

	public Player getPointScoreLeadPlayer() {
		return (playerOne.getPointScore() > playerTow.getPointScore()) ? playerOne : playerTow;
	}

	public void displayScore() {
		StringBuilder scorePlayerOne = new StringBuilder();
		scorePlayerOne.append(this.playerOne.getName());
		scorePlayerOne.append(Constants.SET);
		scorePlayerOne.append(this.playerOne.getSetScore());
		scorePlayerOne.append(Constants.POINTS);
		if (this.isDeuce() && this.playerOne.isAdvanced()) {
			scorePlayerOne.append("ADV");
		} else {
			scorePlayerOne.append(this.playerOne.getPointScore());
		}
		scorePlayerOne.append(Constants.ACCOLADE);

		StringBuilder scorePlayerTow = new StringBuilder();
		scorePlayerTow.append(this.playerTow.getName());
		scorePlayerTow.append(Constants.SET);
		scorePlayerTow.append(this.playerTow.getSetScore());
		scorePlayerTow.append(Constants.POINTS);
		if (this.isDeuce() && this.playerTow.isAdvanced()) {
			scorePlayerTow.append("ADV");
		} else {
			scorePlayerTow.append(this.playerTow.getPointScore());
		}

		scorePlayerTow.append(Constants.ACCOLADE);

		System.out.println(scorePlayerOne);
		System.out.println(scorePlayerTow);
		System.out.println(Constants.DELIMITER);
		System.out.println(getTennisSet());
	}

	public void resetPlayersScores() {

		if (this.playerOne.isRest() || this.playerTow.isRest()) {

			this.playerOne.setPointScore(0);
			this.playerTow.setPointScore(0);
			this.playerOne.setRest(false);
			this.playerOne.setAdvanced(false);
			this.playerTow.setRest(false);
			this.playerTow.setAdvanced(false);

			this.setDeuce(false);

		}
	}


}
