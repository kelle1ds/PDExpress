package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class DataBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String projectName;
	private String projectDescription;
	private String businessGoals;
	private String primaryMarket;
	private String secondaryMarket;
	private String assumptions;
	private String stakeholders;
	private Integer importance;
	private Integer specImportance;
	private Integer needNum;
	private ArrayList<Integer> needsIndex;
	private ObservableList<Person> personList;
	private ObservableList<Needs> needList;
	private ObservableList<Specification> specList;
	private ObservableList<CourseOfAction> coaList;
	private ObservableList<BenchMark> bmList;

	public DataBase(){
		specList = FXCollections.observableArrayList();
		personList = FXCollections.observableArrayList();
		needList = FXCollections.observableArrayList();
		needsIndex = new ArrayList<Integer>();
		coaList = FXCollections.observableArrayList();
		bmList = FXCollections.observableArrayList();
		projectName = "";
		projectDescription ="";
		businessGoals = "";
		primaryMarket = "";
		secondaryMarket = "";
		assumptions = "";
		stakeholders = "";
	}
	
	public void addBm(BenchMark bm){
		bmList.add(bm);
	}
	
	public ObservableList<BenchMark> getBmList(){
		return bmList;
	}
	
	public void addCOA(CourseOfAction coa){
		coaList.add(coa);
	}
	
	public ObservableList<CourseOfAction> getCoaList(){
		return coaList;
	}
	
	public void setNeedsIndex(){
		for(int i = 0; i<needList.size();i++){
			needList.get(i).setNumber(i+1);
		}
	}
	
	
	
	public void setSpecificationsIndex(){			//reassigns the Specification numbers to keep them ordered and neat
		for(int i = 0; i<specList.size();i++){
			specList.get(i).setSpecNumber(i + 1);;
		}
	}
	
	public void setCoaIndex(){			//reassigns the COA numbers to keep them ordered and neat
		for(int i = 0; i<coaList.size();i++){
			coaList.get(i).setCoa(i + 1);
		}
	}
	
	public void setBmIndex(){			//reassigns the BM numbers to keep them ordered and neat
		for(int i = 0; i<bmList.size();i++){
			bmList.get(i).setCoa(i + 1);
		}
	}
	
	
	public void addSpecification(Specification spec){
		specList.add(spec);
	}
	
	public ObservableList<Specification> getSpecList(){
		return specList;
	}

	public Integer getSpecImportance() {
		return specImportance;
	}

	public void setSpecImportance(Integer specImportance) {
		this.specImportance = specImportance;
	}

	
	public Integer getNeedNum(){
		if(needList.size()==0) return 1;
		else return needList.size() + 1;
	}
	
	public Integer getNeedNum(boolean need){
		if(needList.size()==0) return 1;
		else return needList.size() + 1;
	}
	
	public Integer getSpecNum(){
		if(specList.size()==0) return 1;
		else return specList.size() + 1;
	}
	
	public Integer getCoaNum(){
		if(coaList.size()==0) return 1;
		else return coaList.size() + 1;
	}
	
	public Integer getBmNum(){
		if(bmList.size()==0) return 1;
		else return bmList.size() + 1;
	}
	
	public Integer getImportance() {
		return importance;
	}

	public void setImportance(Integer importance) {
		this.importance = importance;
	}
	

	
	
	
	public void addPerson(Person person){
		personList.add(person);
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public ObservableList<Needs> getNeedList() {
		return needList;
	}


	public void setNeedList(Needs needs) {
		needList.add(needs);
	}

	

	public String getBusinessGoals() {
		return businessGoals;
	}

	public void setBusinessGoals(String businessGoals) {
		System.out.println(businessGoals + " set as a goal");
		this.businessGoals = businessGoals;
	}

	public String getPrimaryMarket() {
		return primaryMarket;
	}

	public void setPrimaryMarket(String primaryMarket) {
		this.primaryMarket = primaryMarket;
	}

	public String getSecondaryMarket() {
		return secondaryMarket;
	}

	public void setSecondaryMarket(String secondaryMarket) {
		this.secondaryMarket = secondaryMarket;
	}

	public String getAssumptions() {
		return assumptions;
	}

	public void setAssumptions(String assumptions) {
		this.assumptions = assumptions;
	}

	public String getStakeholders() {
		return stakeholders;
	}

	public void setStakeholders(String stakeholders) {
		this.stakeholders = stakeholders;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public ObservableList<Person> getPersons(){
		return personList;
	}
	
	
	public void saveToFile(File file) throws IOException{
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Person[] persons = personList.toArray(new Person[personList.size()]);
		Needs[] needs = needList.toArray(new Needs[needList.size()]);
		Specification[] spec = specList.toArray(new Specification[specList.size()]);
		CourseOfAction[] coa = coaList.toArray(new CourseOfAction[coaList.size()]);
		BenchMark[] bm = bmList.toArray(new BenchMark[bmList.size()]);

		oos.writeObject(projectName);
		oos.writeObject(projectDescription);
		oos.writeObject(businessGoals);
		oos.writeObject(primaryMarket);
		oos.writeObject(secondaryMarket);
		oos.writeObject(assumptions);
		oos.writeObject(stakeholders);

		oos.writeObject(persons);
		oos.writeObject(needs);
		oos.writeObject(spec);
		oos.writeObject(coa);
		oos.writeObject(bm);
		
		oos.close();
		
	}
	
	public void loadFromFile(File file) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			projectName = (String) ois.readObject();
			projectDescription = (String) ois.readObject();
			businessGoals = (String) ois.readObject();
			primaryMarket = (String) ois.readObject();
			secondaryMarket = (String) ois.readObject();
			assumptions = (String) ois.readObject();
			stakeholders = (String) ois.readObject();

			Person[] persons = (Person[]) ois.readObject();
			Needs[] needs = (Needs[]) ois.readObject();
			Specification[] spec = (Specification[]) ois.readObject();
			CourseOfAction[] coa = (CourseOfAction[]) ois.readObject();
			BenchMark[] bm = (BenchMark[]) ois.readObject();

			personList.clear();
			needList.clear();
			specList.clear();
			coaList.clear();
			personList.addAll(Arrays.asList(persons));
			needList.addAll(Arrays.asList(needs));
			specList.addAll(Arrays.asList(spec));
			coaList.addAll(Arrays.asList(coa));
			bmList.addAll(Arrays.asList(bm));
			
		} catch (ClassNotFoundException e) {
			Alert alert = new Alert(null);
			alert.setTitle("Open File Alert");
			alert.setContentText("some whent wrong when opening database file");
			//e.printStackTrace();
		}
		
		ois.close();
	}
}
