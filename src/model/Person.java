package model;

import java.io.Serializable;

public class Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String globalid;
	private String duties;
	private String email;

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDuties() {
		return duties;
	}
	public void setDuties(String duties) {
		this.duties = duties;
	}
	public Person(String first, String last, String global, String duties, String email){
		this.firstName = first;
		this.lastName = last;
		this.globalid = global;
		this.duties = duties;
		this.email = email;
	}
	public String getGlobalid() {
		return globalid;
	}
	public void setGlobalid(String globalid) {
		this.globalid = globalid;
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
	

}
