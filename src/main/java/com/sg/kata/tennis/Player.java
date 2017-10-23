package com.sg.kata.tennis;

public class Player {

	private String name;
	private boolean advanced;
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

	public boolean isAdvanced() {
		return advanced;
	}

	public void setAdvanced(boolean advanced) {
		this.advanced = advanced;
	}

	public boolean isRest() {
		return isRest;
	}

	public void setRest(boolean isRest) {
		this.isRest = isRest;
	}

}
