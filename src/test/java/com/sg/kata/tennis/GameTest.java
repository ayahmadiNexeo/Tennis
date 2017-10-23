package com.sg.kata.tennis;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import com.sg.kata.tennis.Game;
import com.sg.kata.tennis.Player;

public class GameTest {

	Player playerNumberOne;
	Player playerNumberTwo;
	Game game;

	@Before
	public void beforeGameTest() {
		playerNumberOne = new Player("Victor");
		playerNumberTwo = new Player("Ahmed");
		game = new Game(playerNumberOne, playerNumberTwo);
	}

	@Test
	public void setScoreAndPointScoreShouldZeroInTheBeginningForAllPlayers() {

		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
		assertThat(playerNumberOne, hasProperty("setScore", equalTo(0)));

		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(0)));
		assertThat(playerNumberTwo, hasProperty("setScore", equalTo(0)));
	}

	@Test
	public void pointScoreShouldBeFifteenIfPlayerWinTheFirstBall() {
		game.winBall(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(15)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void pointsScoreShouldBeThirtyIfPlayerWinTheSecondBall() {
		game.winBall(playerNumberTwo, playerNumberOne);
		game.winBall(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(30)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void scoreShouldBeFortyIfPlayerWinTheThirdBall() {
		game.winBall(playerNumberTwo, playerNumberOne);
		game.winBall(playerNumberTwo, playerNumberOne);
		game.winBall(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(40)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void deuceShouldBeActivatedIfTheTwoPlayersReachTheScoreForty() {
		IntStream.rangeClosed(1, 3).forEach((Integer) -> {
			game.winBall(playerNumberOne , playerNumberTwo);
		});
		
		IntStream.rangeClosed(1, 2).forEach((Integer) -> {
			game.winBall(playerNumberTwo, playerNumberOne);
		});
		game.isDeuceActivated();
		assertThat(game, hasProperty("deuce", is(false)));
		
		game.winBall(playerNumberTwo, playerNumberOne);
		game.isDeuceActivated();
		assertThat(game, hasProperty("deuce", is(true)));
	}

	@Test
	public void advantageShouldBeDescriptionWhenLeastThreeBallHaveBeenScoredByEachSideAndPlayerHasOneBallMoreThanHisOpponent() {
		IntStream.rangeClosed(1, 3).forEach((Integer) -> {
			game.winBall(playerNumberOne , playerNumberTwo);
		});
		IntStream.rangeClosed(1, 4).forEach((Integer) -> {
			game.winBall(playerNumberTwo, playerNumberOne);
		});
		game.winBall(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberOne, hasProperty("advanced", is(false)));
		assertThat(playerNumberTwo, hasProperty("advanced", is(true)));
	}

	@Test
	public void setScoreShouldBeOneWhenAtLeastThreeBallHaveBeenScoredByEachSideAndPlayerHasTowBallMoreThanHisOpponent() {

		IntStream.rangeClosed(1, 3).forEach((Integer) -> {
			game.winBall(playerNumberOne , playerNumberTwo);
		});
		IntStream.rangeClosed(1, 4).forEach((Integer) -> {
			game.winBall(playerNumberTwo, playerNumberOne);
		});
		game.winBall(playerNumberTwo,playerNumberOne);
		game.winBall(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("setScore", equalTo(2)));
		assertThat(playerNumberOne, hasProperty("setScore", equalTo(0)));
	}

	@Test
	public void setScoreShouldBeOneIfPlayerWinTheFirstSet() {
		playerNumberTwo.winSet();
		assertThat(playerNumberTwo, hasProperty("setScore", equalTo(1)));
	}

	@Test
	public void tieBreakShouldBeActivatedIfTheTwoPlayersReachTheScoreSix() {
		IntStream.rangeClosed(1, 6).forEach((Integer) -> {
			playerNumberOne.winSet(); 
		});
		IntStream.rangeClosed(1, 5).forEach((Integer) -> {
			playerNumberTwo.winSet();
		});
		game.isTieBreakActivated();
		assertThat(game, hasProperty("tieBreak", is(false)));
		playerNumberTwo.winSet();
		game.isTieBreakActivated();
		assertThat(game, hasProperty("tieBreak", is(true)));
	}

	@Test
	public void equabilityShouldBeDescriptionWhenAtLeastSixSetHaveBeenScoredByEachPlayerAndTheScoresAreEqual() {
		IntStream.rangeClosed(1, 6).forEach((Integer) -> {
			playerNumberOne.winSet();
		});
		IntStream.rangeClosed(1, 6).forEach((Integer) -> {
			playerNumberTwo.winSet();
		});
		game.isTieBreakActivated();
		assertThat(game.getTennisSet(), is("Equability"));
		playerNumberOne.winSet();
		assertThat(game.getTennisSet(), is(not("Equability")));
		assertThat(game.getTennisSet(), is("Victor on advanced"));
		playerNumberTwo.winSet();
		assertThat(game.getTennisSet(), is("Equability"));
	}

	@Test
	public void gameShouldBeWonByTheFirstPlayerwhoHaveWonAtLeastSixSetsInTotalAndWithAtLeastTwoSetsMoreThanTheOpponent() {
		IntStream.rangeClosed(1, 6).forEach((Integer) -> {
			playerNumberOne.winSet();
		});
		IntStream.rangeClosed(1, 5).forEach((Integer) -> {
			playerNumberTwo.winSet();
		});
		assertThat(game.getTennisSet(), is(not("Victor won")));
		assertThat(game.getTennisSet(), is(not("Ahmed won")));
		playerNumberOne.winSet();
		assertThat(game.getTennisSet(), is("Victor won"));
	}

}
