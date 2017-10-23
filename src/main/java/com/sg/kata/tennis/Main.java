package com.sg.kata.tennis;

public class Main implements Runnable {

	public Main() {
		Thread game = new Thread(this);
		game.start();

	}

	public static void main(String[] args) {

		new Main();

	}

	@Override
	public void run() {

		Player playerOne = new Player("PLAYER A");
		Player playerTow = new Player("PLAYER B");
		Game game = new Game(playerOne, playerTow);

		while (!game.isTheEnd()) {

			double ball = Math.random() * 2; 

			checkBeforePlayerWinBall(game);

			if (ball > 1) {
				game.winBall(game.getPlayerOne(),
						game.getPlayerTow());

			} else {
				game.winBall(game.getPlayerTow(),
						game.getPlayerOne());
			}
			checkAfterPlayerWinBall(game);

			try {

				Thread.sleep(500);
			} catch (InterruptedException exception) {

				exception.printStackTrace();
			}

		}
	}

	public void checkBeforePlayerWinBall(Game game) {
		game.isDeuceActivated();
		game.isTieBreakActivated();

	}

	public void checkAfterPlayerWinBall(Game game) {
		game.resetPlayersScores();
		game.displayScore();
		game.manageEndOfTheGame();
	}

}
