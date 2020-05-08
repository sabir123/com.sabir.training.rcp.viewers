package com.sabir.training.rcp.viewers.model;

import java.util.ArrayList;
import java.util.List;

public class Person {

	private String firstName, lastName;
	private int age;
	private List<Person> children;
	private Person parent;
	private List<Skill> skills;
	
	public Person(String firstName, String lastName, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public List<Person> getChildren(){
		return children;
	}
	public void addChildren(Person p) {
		if(children == null )
			children = new ArrayList<>();
		children.add(p);
	}
	
	public boolean hasChildren() {
		if(this.getChildren() != null && this.getChildren().size() > 0) {
			return true;
		}
		return false;
	}

	public Person getParent() {
		return parent;
	}

	public void setParent(Person parent) {
		this.parent = parent;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
}
