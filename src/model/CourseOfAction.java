package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseOfAction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int coa;
	private String coaName;
	private String description;
	private String strengths;
	private String weakness;
	private Integer score;
	private Integer evaluation;
	
	private Integer criteriaEvaluation;
	//private ObservableList<Specification> specList;
	private ArrayList<DesignSelection> selection;
	private ArrayList<DesignSelection> criteriaSelection;

	
	public CourseOfAction(int coa, String coaName, String description, String strengths, String weakness){
		this.coa = coa;
		this.coaName = coaName;
		this.description = description;
		this.strengths = strengths;
		this.weakness = weakness;
		
		selection = new ArrayList<DesignSelection>();
		
		criteriaSelection = new ArrayList<DesignSelection>();

	}
	
	public Integer getCriteriaEvaluation() {
		return criteriaEvaluation;
	}
	
	public ArrayList<DesignSelection> getCriteriaSelection(){
		return criteriaSelection;
	}
	
	public void addCriteriaSelection(int index, DesignSelection selection){
		this.criteriaSelection.add(index, selection);
	}
	
	public ArrayList<DesignSelection> getSelection(){
		return selection;
	}
	
	public void setSelection(DesignSelection selection){
		this.selection.add(selection);
	}
	
	public void setCriteriaEvaluation(Integer criteriaEvaluation) {
		this.criteriaEvaluation = criteriaEvaluation;
	}

	public void addSelection(int index, DesignSelection selection){
		this.selection.add(index, selection);
 	}
	
	
	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCoaName() {
		return coaName;
	}

	public void setCoaName(String coaName) {
		this.coaName = coaName;
	}

	public int getCoa() {
		return coa;
	}
	public void setCoa(int coa) {
		this.coa = coa;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStrengths() {
		return strengths;
	}
	public void setStrengths(String strengths) {
		this.strengths = strengths;
	}
	public String getWeakness() {
		return weakness;
	}
	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}
}
