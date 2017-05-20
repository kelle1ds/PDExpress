package model;

import java.io.Serializable;

public class Needs implements Serializable {

	private Integer number;  //need number
	private String need;
	private Integer importance;
	
	public Needs(Integer number, String need, Integer importance){
		this.number = number;
		this.need = need;
		this.importance = importance;
	}
	

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getNeed() {
		return need;
	}

	public void setNeed(String need) {
		this.need = need;
	}

	public Integer getImportance() {
		return importance;
	}

	public void setImportance(Integer importance) {
		this.importance = importance;
	}
	
	
}
