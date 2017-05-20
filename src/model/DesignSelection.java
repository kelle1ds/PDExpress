package model;

import java.io.Serializable;

import javafx.collections.ObservableList;

public class DesignSelection implements Serializable {
 
	private Integer evaluationScore;
	private Integer product;
	
	
	public DesignSelection(Integer evaluationScore, Integer product){
		this.evaluationScore = evaluationScore;
		this.product = product;
	}
	
	
	public Integer getEvaluationScore() {
		return evaluationScore;
	}
	
	public void setEvaluationScore(Integer evaluationScore) {
		this.evaluationScore = evaluationScore;
	}
	public Integer getProduct() {
		return product;
	}
	public void setProduct(Integer product) {
		this.product = product;
	}
	
	

	
}
