package com.sabir.training.rcp.viewers.model;

public class Skill {
	private String name;
	private int proficiency = 1;
	
	public Skill(String name, int proficieny) {
		this.name = name;
		this.proficiency = proficieny;
	}

	public String getName() {
		return name;
	}

	public int getProficiency() {
		return proficiency;
	}

}
