package com.sg.kata.tennis;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import com.sg.kata.tennis.Match;
import com.sg.kata.tennis.Player;

public class MatchTest {

	Player playerNumberOne;
	Player playerNumberTwo;
	Match match;

	@Before
	public void beforeGameTest() {
		match = new Match();
		playerNumberOne = match.getPlayerOne();
		playerNumberTwo = match.getPlayerTwo();
	}

	@Test
	public void shouldMatchCreateTwoPlayersInTheBeginning(){
		
		assertNotNull(playerNumberOne) ;
		assertNotNull(playerNumberTwo) ;
	}
	
	@Test
	public void shouldSetScoreAndPointScoreBeZeroInTheBeginningOfTheMatchForAllPlayers() {

		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
		assertThat(playerNumberOne, hasProperty("setScore", equalTo(0)));

		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(0)));
		assertThat(playerNumberTwo, hasProperty("setScore", equalTo(0)));
	}

	@Test
	public void shouldPointScoreBeFifteenIfPlayerWinTheFirstPoint() {
		playerNumberTwo.winPoint(playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(15)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void shouldPointsScoreBeThirtyIfPlayerWinTheSecondPoint() {
		playerNumberTwo.winPoint(playerNumberOne);
		playerNumberTwo.winPoint(playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(30)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void shouldPointsScoreBeThirtyIfPlayerOneWinTwoPointAndFifteenIfPlayerTwoWinLonlyPoint() {
		playerNumberTwo.winPoint(playerNumberOne);
		playerNumberTwo.winPoint(playerNumberOne);
		playerNumberOne.winPoint(playerNumberTwo);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(30)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(15)));
	}

	@Test
	public void shouldSetScoreBeFortyIfPlayerWinThreePoints() {
		playerNumberTwo.winPoint(playerNumberOne);
		playerNumberTwo.winPoint(playerNumberOne);
		playerNumberTwo.winPoint(playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(40)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void shouldDeuceBeActivatedIfTheTwoPlayersPonitsScoreReachForty() {

		winPoints(playerNumberOne, playerNumberTwo, 3);
		winPoints(playerNumberTwo, playerNumberOne, 2);

		match.isDeuceActivated();
		assertThat(match, hasProperty("deuce", is(false)));

		playerNumberTwo.winPoint(playerNumberOne);
		match.isDeuceActivated();
		assertThat(match, hasProperty("deuce", is(true)));
	}

	@Test
	public void shouldAdvantageBeTrueWhenAtLeastThreePointsHaveBeenScoredByEachPlayerAndAPlayerWinOneMorePoint() {

		winPoints(playerNumberOne, playerNumberTwo, 3);
		winPoints(playerNumberTwo, playerNumberOne, 3);

		playerNumberTwo.winPoint(playerNumberOne);
		assertThat(playerNumberOne, hasProperty("advantage", is(false)));
		assertThat(playerNumberTwo, hasProperty("advantage", is(true)));
	}

	@Test
	public void shouldSetScoreBeOneWhenAtLeastThreePointsScoredByEachSideAndPlayerWinTwoPointsSuccessively() {

		winPoints(playerNumberOne, playerNumberTwo, 3);
		winPoints(playerNumberTwo, playerNumberOne, 4);

		playerNumberTwo.winPoint(playerNumberOne);
		playerNumberTwo.winPoint(playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("setScore", equalTo(2)));
		assertThat(playerNumberOne, hasProperty("setScore", equalTo(0)));
	}

	@Test
	public void shouldSetScoreBeOneIfPlayerWinTheFirstSet() {
		playerNumberTwo.winSet();
		assertThat(playerNumberTwo, hasProperty("setScore", equalTo(1)));
	}

	@Test
	public void shouldTieBreakBeActivatedIfTheTwoPlayersReachSix() {

		winGames(playerNumberOne, 6);
		winGames(playerNumberTwo, 5);
		match.isTieBreakActivated();
		assertThat(match, hasProperty("tieBreak", is(false)));
		playerNumberTwo.winSet();
		match.isTieBreakActivated();
		assertThat(match, hasProperty("tieBreak", is(true)));
	}

	@Test
	public void shouldMatchBeWonByTheFirstPlayerWhoWonAtLeastSixSetsAndTheGapIsAtLeastTwo() {

		winGames(playerNumberOne, 5);
		winGames(playerNumberTwo, 4);

		assertThat(match.getMatchSet(), is(not("Victor won")));
		assertThat(match.getMatchSet(), is(not("Ahmed won")));
		playerNumberOne.winSet();
		assertThat(match.getMatchSet(), is("Victor won"));
	}

	@Test
	public void shouldMatchBeWonIfTheTwoPlayersWinAtLeastSixSetsAndTheGapIsGreaterThanTwo() {

		winGames(playerNumberOne, 7);
		winGames(playerNumberTwo, 6);

		assertThat(match.getMatchSet(), is(not("Victor won")));
		assertThat(match.getMatchSet(), is(not("Ahmed won")));
		playerNumberOne.winSet();
		assertThat(match.getMatchSet(), is("Victor won"));
	}

	private void winPoints(Player winner, Player looser, int n) {
		IntStream.rangeClosed(1, n).forEach((Integer) -> {
			winner.winPoint(looser);
		});
	}

	private void winGames(Player player, int n) {
		IntStream.rangeClosed(1, n).forEach((Integer) -> {
			player.winSet();
		});
	}
}
