package com.sg.kata.tennis;

public class Main implements Runnable {

	public Main() {
		Thread match = new Thread(this);
		match.start();

	}

	public static void main(String[] args) {

		new Main();

	}

	@Override
	public void run() {
		Match match = new Match();
		Player playerOne = match.getPlayerOne();
		Player playerTow = match.getPlayerTwo();
		

		while (!match.isTheEnd()) {

			double ball = Math.random() * 2;

			checkBeforePlayerWinBall(match);

			if (ball > 1) {
				playerOne.winPoint(match.getPlayerTwo());

			} else {
				playerTow.winPoint(match.getPlayerOne());
			}
			checkAfterPlayerWinBall(match);

			try {

				Thread.sleep(500);
			} catch (InterruptedException exception) {

				System.err.println(exception.getMessage());
			}

		}
	}

	public void checkBeforePlayerWinBall(Match match) {
		match.isDeuceActivated();
		match.isTieBreakActivated();

	}

	public void checkAfterPlayerWinBall(Match match) {
		match.resetPlayersScores();
		match.displayMatchScore();
		match.manageEndOfTheGame();
	}

}
