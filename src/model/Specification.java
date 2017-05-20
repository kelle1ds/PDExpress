package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Specification implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer specNumber;
	private String metric;
	private String unit;
	private Integer importance;
	private String value;
	private String evaluation;
	private Integer needSize = 100;
	//private Enumeration relate = {Strong, Moderate};
	
	private ArrayList<Integer> needs;

	private ArrayList<Integer> needList;
	private ArrayList<ArrayList<Integer>> relationship;
	private ArrayList<Integer> needsShown;
	private Set<Integer> set;
	
	public void setNeeds(int index) {  //Moderate Need
		
		needs.add(index, index+1);
		needsShown.add(index+1);
		Collections.sort(needsShown);
	}
	
	
	
	public void removeNeeds(int index){
		needsShown.clear();
		needs.clear();
	}
	
	
	public void removeNeed(Integer num){
		for(int i = 0;i<needsShown.size();i++){
			
			if(needsShown.get(i).intValue()==num){
				needsShown.remove(i);
			}
		}
		needs.remove(num-1);
		
	}
	
	public void clearNeeds(){
		needsShown.clear();
		needs.clear();
		setNeedList();
	}
	
	public ArrayList<Integer> getNeedsShown(){
		return this.needsShown;
	}
	
	/*
	public Specification(Integer number, Integer importance, String metric, 
			String unit, String value, String evaluation, Integer needSize){
		
		//needs = new ArrayList<Integer>(needSize);
		needs = new ArrayList<Integer>();
		needsShown = new ArrayList<Integer>();
		needsShownStrong = new ArrayList<Integer>();

		this.specNumber = number;
		this.importance = importance;
		this.metric = metric;
		
		this.unit = unit;
		this.value = value;
		this.evaluation = evaluation;
		
		setNeedList();
		setNeedListStrong();
	
	}
	*/

	public Specification(Integer number, Integer importance, String metric, String unit, String value, String evaluation){
		
		needs = new ArrayList<Integer>();
		relationship = new ArrayList<ArrayList<Integer>>();
		needsShown = new ArrayList<Integer>();

		this.specNumber = number;
		this.importance = importance;
		this.metric = metric;
		
		this.unit = unit;
		this.value = value;
		this.evaluation = evaluation;
		setNeedList();
	}
	
	public String getEvaluation() {
		return evaluation;
	}


	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public void setNeedList(){
		for(int i = 0; i<needSize;i++){
			needs.add(0);
		}
	}
	
	
	
	public ArrayList<Integer> getNeeds() {
		return needs;
	}
	
	public Integer getSpecNumber() {
		return specNumber;
	}

	public void setSpecNumber(Integer specNumber) {
		this.specNumber = specNumber;
	}

	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getImportance() {
		return importance;
	}
	public void setImportance(Integer importance) {
		this.importance = importance;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "" + importance + "";
	}
	
}
