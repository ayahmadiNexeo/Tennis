package com.sg.kata.tennis;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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
		playerNumberOne = new Player("Victor");
		playerNumberTwo = new Player("Ahmed");
		match = new Match(playerNumberOne, playerNumberTwo);
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
		match.winPoint(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(15)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void shouldPointsScoreBeThirtyIfPlayerWinTheSecondPoint() {
		match.winPoint(playerNumberTwo, playerNumberOne);
		match.winPoint(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(30)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void shouldPointsScoreBeThirtyIfPlayerOneWinTwoPointAndFifteenIfPlayerTwoWinLonlyPoint() {
		match.winPoint(playerNumberTwo, playerNumberOne);
		match.winPoint(playerNumberTwo, playerNumberOne);
		match.winPoint(playerNumberOne, playerNumberTwo);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(30)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(15)));
	}

	@Test
	public void shouldSetScoreBeFortyIfPlayerWinThreePoints() {
		match.winPoint(playerNumberTwo, playerNumberOne);
		match.winPoint(playerNumberTwo, playerNumberOne);
		match.winPoint(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberTwo, hasProperty("pointScore", equalTo(40)));
		assertThat(playerNumberOne, hasProperty("pointScore", equalTo(0)));
	}

	@Test
	public void shouldDeuceBeActivatedIfTheTwoPlayersPonitsScoreReachForty() {

		winPoints(playerNumberOne, playerNumberTwo, 3);
		winPoints(playerNumberTwo, playerNumberOne, 2);

		match.isDeuceActivated();
		assertThat(match, hasProperty("deuce", is(false)));

		match.winPoint(playerNumberTwo, playerNumberOne);
		match.isDeuceActivated();
		assertThat(match, hasProperty("deuce", is(true)));
	}

	@Test
	public void shouldAdvantageBeTrueWhenAtLeastThreePointsHaveBeenScoredByEachPlayerAndAPlayerWinOneMorePoint() {

		winPoints(playerNumberOne, playerNumberTwo, 3);
		winPoints(playerNumberTwo, playerNumberOne, 3);

		match.winPoint(playerNumberTwo, playerNumberOne);
		assertThat(playerNumberOne, hasProperty("advantage", is(false)));
		assertThat(playerNumberTwo, hasProperty("advantage", is(true)));
	}

	@Test
	public void shouldSetScoreBeOneWhenAtLeastThreePointsScoredByEachSideAndPlayerWinTwoPointsSuccessively() {

		winPoints(playerNumberOne, playerNumberTwo, 3);
		winPoints(playerNumberTwo, playerNumberOne, 4);

		match.winPoint(playerNumberTwo, playerNumberOne);
		match.winPoint(playerNumberTwo, playerNumberOne);
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

		winSets(playerNumberOne, 6);
		winSets(playerNumberTwo, 5);
		match.isTieBreakActivated();
		assertThat(match, hasProperty("tieBreak", is(false)));
		playerNumberTwo.winSet();
		match.isTieBreakActivated();
		assertThat(match, hasProperty("tieBreak", is(true)));
	}

	@Test
	public void shouldEquabilityBeTrueWhenAtLeastSixSetsHaveBeenScoredByEachPlayerAndTheScoresIsEqual() {

		winSets(playerNumberOne, 6);
		winSets(playerNumberTwo, 6);

		match.isTieBreakActivated();
		assertThat(match.getMatchSet(), is("Equability"));
		playerNumberOne.winSet();
		assertThat(match.getMatchSet(), is(not("Equability")));
		assertThat(match.getMatchSet(), is("Victor on advantage"));
		playerNumberTwo.winSet();
		assertThat(match.getMatchSet(), is("Equability"));
	}

	@Test
	public void shouldMatchBeWonByTheFirstPlayerWhoWonAtLeastSixSetsAndTheGapIsAtLeastTwo() {

		winSets(playerNumberOne, 5);
		winSets(playerNumberTwo, 4);

		assertThat(match.getMatchSet(), is(not("Victor won")));
		assertThat(match.getMatchSet(), is(not("Ahmed won")));
		playerNumberOne.winSet();
		assertThat(match.getMatchSet(), is("Victor won"));
	}

	@Test
	public void shouldMatchBeWonIfTheTwoPlayersWinAtLeastSixSetsAndTheGapIsGreaterThanTwo() {

		winSets(playerNumberOne, 7);
		winSets(playerNumberTwo, 6);

		assertThat(match.getMatchSet(), is(not("Victor won")));
		assertThat(match.getMatchSet(), is(not("Ahmed won")));
		playerNumberOne.winSet();
		assertThat(match.getMatchSet(), is("Victor won"));
	}

	private void winPoints(Player winner, Player looser, int n) {
		IntStream.rangeClosed(1, n).forEach((Integer) -> {
			match.winPoint(winner, looser);
		});
	}

	private void winSets(Player player, int n) {
		IntStream.rangeClosed(1, n).forEach((Integer) -> {
			player.winSet();
		});
	}
}
