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

		Player playerOne = new Player("PLAYER A");
		Player playerTow = new Player("PLAYER B");
		Match match = new Match(playerOne, playerTow);

		while (!match.isTheEnd()) {

			double ball = Math.random() * 2; 

			checkBeforePlayerWinBall(match);

			if (ball > 1) {
				match.winPoint(match.getPlayerOne(),
						match.getPlayerTwo());

			} else {
				match.winPoint(match.getPlayerTwo(),
						match.getPlayerOne());
			}
			checkAfterPlayerWinBall(match);

			try {

				Thread.sleep(500);
			} catch (InterruptedException exception) {

				exception.printStackTrace();
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
