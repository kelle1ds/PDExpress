package controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.ListView;
//import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.DataBase;
import model.DesignSelection;
import model.Person;
//import model.Project;
import model.Specification;
import print.ExportExcel;
import print.ExportNeedSpec;
import print.ExportProject;
import print.PrintBenchmarks;
import print.PrintBmEvaluation;
import print.PrintConceptEvaluation;
import print.PrintConceptEvaluation1;
import print.PrintCustomerNeeds;
import print.PrintDesignConcepts;
import print.PrintNeedSpecMatrix;
import print.PrintProject;
import print.PrintSpecifications;
import model.Needs;
import model.BenchMark;
import model.CourseOfAction;

public class MainWindowController {
	
	Main main;
	Stage primaryStage;
	Stage specificationStage;
	
	private Person person;
	private DataBase dataBase;
	private String projectName, projectDescription;
	private Alert alert;
	private  boolean newProject = false;
	private boolean beenSaved = false;
	private boolean saved = true;
	private File savedFile;
	private boolean textEntered = false;
	
	@FXML TextField projectNameField, firstNameField, lastNameField, globalIdField, emailField;
	@FXML TextArea projectDescriptionField, dutiesField, projectDetailGoals, projectDetailPrimaryMarket;
	@FXML TextArea projectDetailSecondaryMarket,projectDetailAssumptions,projectDetailStakeholders;
	@FXML Button createProjectButton, newProjectButton, editProjectButton, saveButton, openButton;
	@FXML AnchorPane newProjectPane;
	@FXML Label projectDescriptionTableLabel, projectNameTableLabel,statusLabel, newProjectLabel; 
	@FXML Label enterTextLabel1, enterTextLabel2; 

	@FXML TableView tableView;
	@FXML TableColumn<Person, String> firstNameColumn;
	@FXML TableColumn<Person, String> lastNameColumn;
	@FXML TableColumn<Person, String> globalidColumn;
	@FXML TableColumn<Person, String> emailColumn;
	
	////////////Product Needs/////
	private Needs need;
	private Integer needEdited;  //index for edited need
	private boolean needEdit = false;
	private boolean setNeeds = false;
	private boolean needDeleted = false;
	private Integer needNumberSet;
	private Integer needImportance;
	private String needString;
	@FXML RadioButton needVeryImport, needImport, needNotImport, needOkay;
	@FXML Tab specificationTab;
	@FXML Label needNumber, needWarningLabel, projectDetailName, projectDetailDescription;
	@FXML Button setNeedButton,UnsetNeedButton;
	@FXML TextField needStatement;
	@FXML TableView tableViewNeeds, tableViewNeeds2;
	@FXML TableColumn<Needs, Integer> numberColumn;
	@FXML TableColumn<Needs, Integer> importanceColumn;
	@FXML TableColumn<Needs, String> statementColumn;  //needs statement column
	
	////////////Specifications///////////////////////
	private Specification spec;
	private boolean specEdit = false;
	private Integer specNumberSet;
	private int editSpecIndex;
	private Specification editSpecification;
	private String tempEvaluation;
	
	@FXML TableColumn<Needs, Integer> numberColumn2;
	@FXML TableColumn<Needs, String> statementColumn2;  //needs statement column
	@FXML Button specificationButton;
	@FXML RadioButton specLittleImport,specImport,specVeryImport,specOkay,specNotImport;
	@FXML TextField metricField, unitField, valueField;

	@FXML TableColumn<Specification, Integer> specNumber;
	@FXML TableColumn<Specification, Integer> specImp;
	@FXML TableColumn<Specification, String> specMetric;
	@FXML TableColumn<Specification, String> specUnit;
	@FXML TableColumn<Specification, String> specValue;
	@FXML TableView specificationTableView;

	//////////////////////Specification II/////////////////
	private int needIndex;
	@FXML Label metrixSelected;
	@FXML TableView specificationTableView3;
	@FXML TableView tableViewNeeds3;
	@FXML TableColumn<Specification, String> specNeedMetric;
	@FXML TableColumn<Needs, String> statementColumn3;
	@FXML TableColumn<Specification, Integer> needMet;
	@FXML TableColumn<Needs, Integer> numberColumn3;
	
	///////////////////Specification III ////////////////////////
	private int evaluationIndex;
	private boolean editEvaluation = false;
	@FXML TableColumn<Specification, String> specMetric2;
	@FXML TableColumn<Specification, Integer> evalSpecNumber;
	@FXML TableColumn<Specification, String> evaluationCol;
	@FXML TableColumn<Specification, Integer> specNeedMetImport;
	@FXML TableView specificationTableView2;
	@FXML TextArea evaluationText;

	/////////////////////EVALUATION////////////////////////////////
	private Integer evaluation;
	@FXML Label CoaSelectedLabel;
	@FXML Label bmProduct1, bmProduct2, bmProduct3, bmProduct4, bmProduct5, bmProduct6, bmProduct7, bmProduct8, bmProduct9, bmProduct10;
	@FXML Label bmProduct11, bmProduct12, bmProduct13, bmProduct14, bmProduct15, bmProduct16,bmProduct17,bmProduct18, bmProduct19, bmProduct20, bmProduct21;
	@FXML Label bmProduct22, bmProduct23, bmProduct24, bmProduct25;
	@FXML TextField bmScore1, bmScore2, bmScore3, bmScore4, bmScore5, bmScore6, bmScore7, bmScore8, bmScore9, bmScore10;
	@FXML TextField bmScore11, bmScore12, bmScore13, bmScore14, bmScore15, bmScore16, bmScore17, bmScore18, bmScore19, bmScore20, bmScore21;
	@FXML TextField bmScore22, bmScore23, bmScore24, bmScore25;
	@FXML Label bmImport1, bmImport2, bmImport3, bmImport4, bmImport5, bmImport6, bmImport7, bmImport8, bmImport9, bmImport10;
	@FXML Label bmImport11, bmImport12, bmImport13, bmImport14, bmImport15, bmImport16, bmImport17, bmImport18, bmImport19, bmImport20, bmImport21;
	@FXML Label bmImport22, bmImport23, bmImport24, bmImport25;
	@FXML Label bmOneSpec1, bmOneSpec2, bmOneSpec3, bmOneSpec4, bmOneSpec5, bmOneSpec6, bmOneSpec7, bmOneSpec8, bmOneSpec9, bmOneSpec10;
	@FXML Label bmOneSpec11, bmOneSpec12, bmOneSpec13, bmOneSpec14, bmOneSpec15, bmOneSpec16, bmOneSpec17, bmOneSpec18, bmOneSpec19, bmOneSpec20, bmOneSpec21;
	@FXML Label bmOneSpec22, bmOneSpec23, bmOneSpec24, bmOneSpec25;
	@FXML Label  coaOneSpec1, coaOneSpec2, coaOneSpec3, coaOneSpec4, coaOneSpec5, coaOneSpec6, coaOneSpec7, coaOneSpec8;
	@FXML Label  coaOneSpec9, coaOneSpec10, coaOneSpec11, coaOneSpec12,coaOneSpec13a, coaOneSpec14, coaOneSpec15, coaOneSpec16;
	@FXML Label coaOneSpec17, coaOneSpec18, coaOneSpec19, coaOneSpec20, coaOneSpec21, weightedScoreLabel;
	@FXML Label coaOneSpec22, coaOneSpec23, coaOneSpec24, coaOneSpec25;
	@FXML Label  coaOneSpecImport1, coaOneSpecImport2, coaOneSpecImport3, coaOneSpecImport4, coaOneSpecImport5, coaOneSpecImport6, coaOneSpecImport7, coaOneSpecImport8;
	@FXML Label  coaOneSpecImport9, coaOneSpecImport10, coaOneSpecImport11, coaOneSpecImport12, coaOneSpecImport13, coaOneSpecImport14, coaOneSpecImport15;
	@FXML Label coaOneSpecImport16, coaOneSpecImport17, coaOneSpecImport18, coaOneSpecImport19, coaOneSpecImport20, coaOneSpecImport21;
	@FXML Label coaOneSpecImport22, coaOneSpecImport23, coaOneSpecImport24, coaOneSpecImport25;
	@FXML TextField evaluationScore1, evaluationScore2, evaluationScore3, evaluationScore4, evaluationScore5, evaluationScore6, evaluationScore21;
	@FXML TextField evaluationScore22, evaluationScore23, evaluationScore24, evaluationScore25;
	@FXML TextField evaluationScore7, evaluationScore8, evaluationScore9, evaluationScore10, evaluationScore11, evaluationScore12, evaluationScore20;
	@FXML TextField evaluationScore13, evaluationScore14, evaluationScore15, evaluationScore16, evaluationScore17, evaluationScore18, evaluationScore19;
	@FXML Label evaluationProduct1, evaluationProduct2, evaluationProduct3, evaluationProduct4, evaluationProduct5, evaluationProduct6, evaluationProduct21;
	@FXML Label evaluationProduct7, evaluationProduct8, evaluationProduct9, evaluationProduct10, evaluationProduct11, evaluationProduct12, evaluationProduct20;
	@FXML Label evaluationProduct13, evaluationProduct14, evaluationProduct15, evaluationProduct16, evaluationProduct17, evaluationProduct18, evaluationProduct19;
	@FXML Label evaluationProduct22, evaluationProduct23, evaluationProduct24, evaluationProduct25;

	////////////////////////////BENCHMARKING/////////////////////////
	private boolean editBmSet = false;
	private BenchMark bm;
	private boolean bmIsSelected = false;
	private Integer bmNumber = 0;
	private int bmSelected; 
	private int bmIndex;
	@FXML TextField coaName1;
	@FXML TextArea coaDescription1,coaStrength1,coaWeakness1;
	@FXML  TableColumn<Specification, String>coaMetricColumn1;
	@FXML TableView coaTableView1,TableViewCoa1, scoringTableView2; //scoringTableView21;
	@FXML TableColumn<Specification, String> coaValueColumn1;
	@FXML TableColumn<BenchMark, String> scoringTableViewColumn2;//scoringTableViewColumn21;
	@FXML TableColumn<BenchMark, Integer> coaScoreColumn2;//coaScoreColumn21;
	@FXML TableColumn<BenchMark, Integer> coaNumColumn1;
	@FXML TableColumn<BenchMark, String> coaNameColumn1;
	
	////////////////////////BENCHMARK SCORING///////////////
	@FXML Label setConceptLabel2, CoaSelectedLabel1, weightedScoreLabel2, bmWarningLabel;
	
	/////////////////////////////COA-SCORING//////////////////////////////
	private CourseOfAction coa;
	private DesignSelection designSelection;
	private ArrayList<DesignSelection> selected;  //used to pass an edited coa to the new scoring
	private boolean editCoaSet = false;
	private boolean startScoring = false;
	private boolean coaIsSelected = false;
	private boolean coaIsSelected1 = false;
	private Integer coaNumber = 0;
	private int coaSelected, coaSelected1; 
	private int coaIndex;
	@FXML Label coaNum;
	@FXML TextField coaName;
	@FXML TextArea coaDescription,coaStrength,coaWeakness;
	@FXML  TableColumn<Specification, String>coaMetricColumn;
	@FXML TableColumn<Specification, String> coaValueColumn;
	@FXML TableView coaTableView,TableViewCoa,scoringTableView, scoringTableView1;
	@FXML TableColumn<CourseOfAction, Integer> coaNumColumn;
	@FXML TableColumn<CourseOfAction, String> coaNameColumn;
	@FXML TableColumn<CourseOfAction, String> scoringTableViewColumn, scoringTableViewColumn1;
	@FXML TableColumn<CourseOfAction, Integer> coaScoreColumn, coaScoreColumn1;
	@FXML ImageView imageView1;
	@FXML Label imageLabel, setConceptLabel, setConceptLabel1, warningLabel;
	
	/////////////////////////////////////////CRITERIA SCORING/////////////////////////////////
	
	@FXML Label coaOneNeed1, coaOneNeed2, coaOneNeed3, coaOneNeed4, coaOneNeed5, coaOneNeed6, coaOneNeed7, coaOneNeed8, coaOneNeed9, coaOneNeed10;
	@FXML Label coaOneNeed11, coaOneNeed12, coaOneNeed13, coaOneNeed14, coaOneNeed15, coaOneNeed16, coaOneNeed17, coaOneNeed18, coaOneNeed19, coaOneNeed20, coaOneNeed21;
	@FXML Label coaOneNeed22, coaOneNeed23, coaOneNeed24, coaOneNeed25;
	@FXML Label coaNeedImport1, coaNeedImport2, coaNeedImport3, coaNeedImport4, coaNeedImport5, coaNeedImport6, coaNeedImport7;
	@FXML Label coaNeedImport8, coaNeedImport9, coaNeedImport10, coaNeedImport11, coaNeedImport12, coaNeedImport13, coaNeedImport14;
	@FXML Label coaNeedImport15, coaNeedImport16, coaNeedImport17, coaNeedImport18, coaNeedImport19, coaNeedImport20,coaNeedImport21;
	@FXML Label coaNeedImport22, coaNeedImport23, coaNeedImport24, coaNeedImport25;
	@FXML TextField criteriaScore1, criteriaScore2, criteriaScore3, criteriaScore4, criteriaScore5, criteriaScore6, criteriaScore7, criteriaScore8, criteriaScore9, criteriaScore10, criteriaScore11, criteriaScore12;
	@FXML TextField criteriaScore13, criteriaScore14, criteriaScore15, criteriaScore16, criteriaScore17, criteriaScore18, criteriaScore19, criteriaScore20, criteriaScore21;
	@FXML TextField criteriaScore22, criteriaScore23, criteriaScore24, criteriaScore25;
	@FXML Label criteriaProduct1, criteriaProduct2, criteriaProduct3, criteriaProduct4, criteriaProduct5, criteriaProduct6, criteriaProduct7, criteriaProduct8, criteriaProduct9, criteriaProduct10;
	@FXML Label criteriaProduct11, criteriaProduct12, criteriaProduct13, criteriaProduct14, criteriaProduct15, criteriaProduct16, criteriaProduct17, criteriaProduct18, criteriaProduct19, criteriaProduct20,criteriaProduct21;
	@FXML Label criteriaProduct22, criteriaProduct23, criteriaProduct24, criteriaProduct25;
	@FXML Label weightedScoreLabel1;
	
	/*
	/////////////////////////////////////NEW BM SCORING//////////////////////
	@FXML Label specMetricLabel, specMetricImportance, specMetricProduct;
	@FXML TextField specMetricEvaluate;
	@FXML Button specMetricForward, specMetricBack;
	*/
	
	//////////////////////////////START PROGRAM
	
	public void initialize(){	//Initialization for JavaFX
		
		setConceptLabel.setText("Select A Concept First");
		setConceptLabel1.setText("Select A Product First");
		setConceptLabel2.setText("Select A Concept First");

		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		globalidColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("globalid"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
		numberColumn.setCellValueFactory(new PropertyValueFactory<Needs, Integer>("number"));		
		importanceColumn.setCellValueFactory(new PropertyValueFactory<Needs, Integer>("importance"));
		statementColumn.setCellValueFactory(new PropertyValueFactory<Needs, String>("need"));
		numberColumn2.setCellValueFactory(new PropertyValueFactory<Needs, Integer>("number"));		
		statementColumn2.setCellValueFactory(new PropertyValueFactory<Needs, String>("need"));
		statementColumn3.setCellValueFactory(new PropertyValueFactory<Needs, String>("need"));
		specNumber.setCellValueFactory(new PropertyValueFactory<Specification, Integer>("specNumber"));
		specImp.setCellValueFactory(new PropertyValueFactory<Specification, Integer>("importance"));
		specMetric.setCellValueFactory(new PropertyValueFactory<Specification, String>("metric"));
		specValue.setCellValueFactory(new PropertyValueFactory<Specification, String>("value"));
		specUnit.setCellValueFactory(new PropertyValueFactory<Specification, String>("unit"));
		needMet.setCellValueFactory(new PropertyValueFactory<Specification, Integer>("needsShown"));
		specNeedMetImport.setCellValueFactory(new PropertyValueFactory<Specification, Integer>("importance"));

		specMetric2.setCellValueFactory(new PropertyValueFactory<Specification, String>("metric"));
		specNeedMetric.setCellValueFactory(new PropertyValueFactory<Specification, String>("metric"));
		evalSpecNumber.setCellValueFactory(new PropertyValueFactory<Specification,Integer>("specNumber"));
		coaMetricColumn.setCellValueFactory(new PropertyValueFactory<Specification, String>("metric"));
		coaMetricColumn1.setCellValueFactory(new PropertyValueFactory<Specification, String>("metric"));//Benchmark spec colum
		coaValueColumn.setCellValueFactory(new PropertyValueFactory<Specification, String>("value"));//benchmark spec col

		coaValueColumn.setCellValueFactory(new PropertyValueFactory<Specification, String>("value"));
		coaNumColumn.setCellValueFactory(new PropertyValueFactory<CourseOfAction, Integer>("coa"));
		coaNumColumn1.setCellValueFactory(new PropertyValueFactory<BenchMark, Integer>("coa"));

		coaScoreColumn.setCellValueFactory(new PropertyValueFactory<CourseOfAction, Integer>("evaluation"));
		coaScoreColumn1.setCellValueFactory(new PropertyValueFactory<CourseOfAction, Integer>("criteriaEvaluation"));
		coaScoreColumn2.setCellValueFactory(new PropertyValueFactory<BenchMark, Integer>("evaluation"));

		coaNameColumn.setCellValueFactory(new PropertyValueFactory<CourseOfAction,String>("coaName"));
		coaNameColumn1.setCellValueFactory(new PropertyValueFactory<BenchMark,String>("coaName"));

		scoringTableViewColumn.setCellValueFactory(new PropertyValueFactory<CourseOfAction, String>("coaName"));
		scoringTableViewColumn1.setCellValueFactory(new PropertyValueFactory<CourseOfAction, String>("coaName"));
		scoringTableViewColumn2.setCellValueFactory(new PropertyValueFactory<BenchMark, String>("coaName"));
		evaluationCol.setCellValueFactory(new PropertyValueFactory<Specification,String>("evaluation"));
		numberColumn3.setCellValueFactory(new PropertyValueFactory<Needs, Integer>("number"));		

		//scoringTableViewColumn21.setCellValueFactory(new PropertyValueFactory<BenchMark, String>("coaName"));
		//coaScoreColumn21.setCellValueFactory(new PropertyValueFactory<BenchMark, Integer>("evaluation"));

		if(dataBase != null) {
			newProjectLabel.setText("Active Project");
		}else
		{
			newProjectLabel.setText("No Active Project");
		}
	}
	/////////////////////// EXPORT AS AN EXCEL FILE//////////////////////
	
	public void exportExcel(){
		if(dataBase != null){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Data to .xls");
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
			File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				ExportExcel export = new ExportExcel(selectedFile, dataBase.getNeedList(),dataBase.getSpecList(),
						dataBase.getPersons(), dataBase.getProjectName(), dataBase.getProjectDescription(), dataBase.getBusinessGoals(),
						dataBase.getPrimaryMarket(), dataBase.getSecondaryMarket(), dataBase.getAssumptions(), dataBase.getStakeholders());
				 
				export.export();
			}
		}
	}
	//////////////////////// EXPORT AS CSV ////////////////////////////////////////
	
	public void exportNeedSpecification() throws IOException{
		if(dataBase != null){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Data to csv");
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
			File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				ExportNeedSpec export = new ExportNeedSpec(selectedFile, dataBase.getNeedList(),dataBase.getSpecList());
				 
				export.export();
			}
		}
			else printDatabaseError();
		
	}
	
	public void onExport() throws IOException{
		if(dataBase != null){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Data to csv");
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
			File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				ExportProject export = new ExportProject(selectedFile, dataBase.getNeedList(),dataBase.getSpecList(),
						dataBase.getPersons(), dataBase.getProjectName(), dataBase.getProjectDescription(), dataBase.getBusinessGoals(),
						dataBase.getPrimaryMarket(), dataBase.getSecondaryMarket(), dataBase.getAssumptions(), dataBase.getStakeholders());
				 
				export.export();
			}
		}
			else printDatabaseError();
		
	}
	
	////////////////////////PRINT////////////////////////////////////////

	
	public void printNeedSpecification(){
		if(dataBase != null){
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Specification Data to PDF");
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
			File selectedFile = fileChooser.showSaveDialog(primaryStage);
				if(selectedFile != null){
					try {
						PrintNeedSpecMatrix print = new PrintNeedSpecMatrix(selectedFile, dataBase.getSpecList(), dataBase.getNeedList());
					     print.printFile();
					} catch (IOException e) {
						alert.setTitle("IO Exception");
						alert.setHeaderText("IO Exception during Save");
						alert.setContentText(e.toString());
						alert.show();
						//e.printStackTrace();
					}
				}
			}
			else printDatabaseError();
	}
	
	public void onPrintSpecification() throws IOException{
		
		if(dataBase != null){
			
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Specification Data to PDF");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				try {
					PrintSpecifications print = new PrintSpecifications(selectedFile, dataBase.getSpecList());
				     print.printFile();
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
					//e.printStackTrace();
				}
			}
		}
		else printDatabaseError();
	 }
	 
	public void onPrintProductNeeds(){
		if(dataBase != null){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Product Needs to PDF");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				try {
					PrintCustomerNeeds print = new PrintCustomerNeeds(selectedFile, dataBase.getNeedList());
				     print.printFile();
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
					//e.printStackTrace();
				}
			}
		}
		else printDatabaseError();
	}
	
	public void onPrintProjectDetails(){
		if(dataBase != null){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Product Needs to PDF");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				try {
					PrintProject print = new PrintProject(selectedFile, dataBase.getNeedList(),
							dataBase.getPersons(), dataBase.getProjectName(), dataBase.getProjectDescription(), dataBase.getBusinessGoals(),
							dataBase.getPrimaryMarket(), dataBase.getSecondaryMarket(), dataBase.getAssumptions(), dataBase.getStakeholders());
				     print.printFile();
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
				}
			}
		}
		else printDatabaseError();
	}

	public void onPrintDesignConcepts(){
		if(dataBase != null){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Design Concepts to PDF");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				try {
					PrintDesignConcepts print = new PrintDesignConcepts(selectedFile, dataBase.getCoaList());
				     print.printFile();
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
					//e.printStackTrace();
				}
			}
		}
		else printDatabaseError();
	}
	
	public void onPrintBenchmarks(){
		if(dataBase != null){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Design Concepts to PDF");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				try {
					PrintBenchmarks print = new PrintBenchmarks(selectedFile, dataBase.getBmList());
				     print.printFile();
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
					//e.printStackTrace();
				}
			}
		}
		else printDatabaseError();
	}
	
	public void onPrintConceptEvaluation1(){
		if(dataBase != null){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Design Concepts to PDF");
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
			File selectedFile = fileChooser.showSaveDialog(primaryStage);
				if(selectedFile != null){
					try {
						PrintConceptEvaluation1 print = new PrintConceptEvaluation1(selectedFile, dataBase.getCoaList(), dataBase.getNeedList()); 
								//,dataBase.getSelection());
					     print.printFile();
					} catch (IOException e) {
						alert.setTitle("IO Exception");
						alert.setHeaderText("IO Exception during Save");
						alert.setContentText(e.toString());
						alert.show();
						//e.printStackTrace();
					}
				}
			}
			else printDatabaseError();
	}
		
	public void onPrintConceptEvaluation(){
		if(dataBase != null){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Design Concepts to PDF");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				try {
					PrintConceptEvaluation print = new PrintConceptEvaluation(selectedFile, dataBase.getCoaList(), dataBase.getSpecList()); 
							//,dataBase.getSelection());
				     print.printFile();
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
					//e.printStackTrace();
				}
			}
		}
		else printDatabaseError();
	}
	
	public void onPrintBmEvaluation(){
		if(dataBase != null){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save BM Evaluations to PDF");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if(selectedFile != null){
				try {
					PrintBmEvaluation print = new PrintBmEvaluation(selectedFile, dataBase.getBmList(), dataBase.getSpecList()); 
							//,dataBase.getSelection());
				     print.printFile();
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
					//e.printStackTrace();
				}
			}
		}
		else printDatabaseError();
	}
	
	public void printDatabaseError(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("You cannot print an empty project");
		alert.setTitle("Printing Error");
		alert.setHeaderText("Printing an Empty Database");
		alert.show();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////CONCEPT SCORING II CUSTOMER NEEDS EVALUATION////////////////////////////////////
	public void onCalculate1(){
		if(!coaIsSelected1 == true) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Design Concept Selection");
			alert.setHeaderText("Design Concept Not Selected");
			alert.setContentText("Select a Design Concept First");
			alert.show();
		}
		else
		{
			int score1 = 0, score2 = 0, score3 = 0, score4 = 0, score5 = 0, score6 = 0, score7 = 0, score8 = 0, score9 = 0, score21 = 0;
			int score10 = 0, score11 = 0, score12 = 0, score13 = 0, score14 = 0, score15 = 0, score16 = 0, score17 = 0, score18 = 0, score19 = 0, score20 = 0;
			int score22 = 0, score23 = 0, score24 = 0, score25 = 0;
			int product1 = 0, product2 = 0, product3 = 0, product4 = 0, product5 = 0, product6 = 0, product7 = 0, product8 = 0, product17 = 0, product18 = 0;
			int product9 = 0, product10 = 0, product11 = 0, product12 = 0, product13 = 0, product14 = 0, product15 = 0, product16 = 0, product19 = 0, product20 = 0, product21 = 0;
			int product22 = 0, product23 = 0, product24 = 0, product25 = 0;
			
			if(dataBase.getNeedList().size() >= 1){
				score1 = Integer.parseInt(criteriaScore1.getText());
				product1 =  dataBase.getNeedList().get(0).getImportance() * Integer.parseInt(criteriaScore1.getText());					
				DesignSelection selection1 = new DesignSelection(score1, product1);
				criteriaProduct1.setText(Integer.toString(product1)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(0, selection1);
 			}
			
			if(dataBase.getNeedList().size() >= 2){
				score2 = Integer.parseInt(criteriaScore2.getText());
				product2 =  dataBase.getNeedList().get(1).getImportance() * Integer.parseInt(criteriaScore2.getText());					
				DesignSelection selection2 = new DesignSelection(score2, product2);
				criteriaProduct2.setText(Integer.toString(product2)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(1, selection2);
 			}
			
			if(dataBase.getNeedList().size() >= 3){
				score3 = Integer.parseInt(criteriaScore3.getText());
				product3 =  dataBase.getNeedList().get(2).getImportance() * Integer.parseInt(criteriaScore3.getText());					
				DesignSelection selection3 = new DesignSelection(score3, product3);
				criteriaProduct3.setText(Integer.toString(product3)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(2, selection3);
 			}
			
			if(dataBase.getNeedList().size() >= 4){
				score4 = Integer.parseInt(criteriaScore4.getText());
				product4 =  dataBase.getNeedList().get(3).getImportance() * Integer.parseInt(criteriaScore4.getText());					
				DesignSelection selection4 = new DesignSelection(score4, product4);
				criteriaProduct4.setText(Integer.toString(product4)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(3, selection4);
 			}
			
			if(dataBase.getNeedList().size() >= 5){
				score5 = Integer.parseInt(criteriaScore5.getText());
				product5 =  dataBase.getNeedList().get(4).getImportance() * Integer.parseInt(criteriaScore5.getText());					
				DesignSelection selection5 = new DesignSelection(score5, product5);
				criteriaProduct5.setText(Integer.toString(product5)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(4, selection5);
 			}
			if(dataBase.getNeedList().size() >= 6){
				score6 = Integer.parseInt(criteriaScore6.getText());
				product6 =  dataBase.getNeedList().get(5).getImportance() * Integer.parseInt(criteriaScore6.getText());					
				DesignSelection selection6 = new DesignSelection(score6, product6);
				criteriaProduct6.setText(Integer.toString(product6)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(5, selection6);
 			}
			if(dataBase.getNeedList().size() >= 7){
				score7 = Integer.parseInt(criteriaScore7.getText());
				product7 =  dataBase.getNeedList().get(6).getImportance() * Integer.parseInt(criteriaScore7.getText());					
				DesignSelection selection7 = new DesignSelection(score7, product7);
				criteriaProduct7.setText(Integer.toString(product7)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(6, selection7);
 			}
			if(dataBase.getNeedList().size() >= 8){
				score8 = Integer.parseInt(criteriaScore8.getText());
				product8 =  dataBase.getNeedList().get(7).getImportance() * Integer.parseInt(criteriaScore8.getText());					
				DesignSelection selection8 = new DesignSelection(score8, product8);
				criteriaProduct8.setText(Integer.toString(product8)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(7, selection8);
 			}
			if(dataBase.getNeedList().size() >= 9){
				score9 = Integer.parseInt(criteriaScore9.getText());
				product9 =  dataBase.getNeedList().get(8).getImportance() * Integer.parseInt(criteriaScore9.getText());					
				DesignSelection selection9 = new DesignSelection(score9, product9);
				criteriaProduct9.setText(Integer.toString(product9)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(8, selection9);
 			}
			if(dataBase.getNeedList().size() >= 10){
				score10 = Integer.parseInt(criteriaScore10.getText());
				product10 =  dataBase.getNeedList().get(9).getImportance() * Integer.parseInt(criteriaScore10.getText());					
				DesignSelection selection10 = new DesignSelection(score10, product10);
				criteriaProduct10.setText(Integer.toString(product10)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(9, selection10);
 			}
			if(dataBase.getNeedList().size() >= 11){
				score11 = Integer.parseInt(criteriaScore11.getText());
				product11 =  dataBase.getNeedList().get(10).getImportance() * Integer.parseInt(criteriaScore11.getText());					
				DesignSelection selection11 = new DesignSelection(score11, product11);
				criteriaProduct11.setText(Integer.toString(product11)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(10, selection11);
			}
			if(dataBase.getNeedList().size() >= 12){
				score12 = Integer.parseInt(criteriaScore12.getText());
				product12 =  dataBase.getNeedList().get(11).getImportance() * Integer.parseInt(criteriaScore12.getText());					
				DesignSelection selection12 = new DesignSelection(score12, product12);
				criteriaProduct12.setText(Integer.toString(product12)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(11, selection12);
			}
			if(dataBase.getNeedList().size() >= 13){
				score13 = Integer.parseInt(criteriaScore13.getText());
				product13 =  dataBase.getNeedList().get(12).getImportance() * Integer.parseInt(criteriaScore13.getText());					
				DesignSelection selection13 = new DesignSelection(score13, product13);
				criteriaProduct13.setText(Integer.toString(product13)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(12, selection13);
			}
			if(dataBase.getNeedList().size() >= 14){
				score14 = Integer.parseInt(criteriaScore14.getText());
				product14 =  dataBase.getNeedList().get(13).getImportance() * Integer.parseInt(criteriaScore14.getText());					
				DesignSelection selection14 = new DesignSelection(score14, product14);
				criteriaProduct14.setText(Integer.toString(product14)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(13, selection14);
			}
			if(dataBase.getNeedList().size() >= 15){
				score15 = Integer.parseInt(criteriaScore15.getText());
				product15 =  dataBase.getNeedList().get(14).getImportance() * Integer.parseInt(criteriaScore15.getText());					
				DesignSelection selection15 = new DesignSelection(score15, product15);
				criteriaProduct15.setText(Integer.toString(product15)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(14, selection15);
			}
			if(dataBase.getNeedList().size() >= 16){
				score16 = Integer.parseInt(criteriaScore16.getText());
				product16 =  dataBase.getNeedList().get(15).getImportance() * Integer.parseInt(criteriaScore16.getText());					
				DesignSelection selection16 = new DesignSelection(score16, product16);
				criteriaProduct16.setText(Integer.toString(product16)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(15, selection16);
			}
			if(dataBase.getNeedList().size() >= 17){
				score17 = Integer.parseInt(criteriaScore17.getText());
				product17 =  dataBase.getNeedList().get(16).getImportance() * Integer.parseInt(criteriaScore17.getText());					
				DesignSelection selection17 = new DesignSelection(score17, product17);
				criteriaProduct17.setText(Integer.toString(product17)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(16, selection17);
			}
			if(dataBase.getNeedList().size() >= 18){
				score18 = Integer.parseInt(criteriaScore18.getText());
				product18 =  dataBase.getNeedList().get(17).getImportance() * Integer.parseInt(criteriaScore18.getText());					
				DesignSelection selection18 = new DesignSelection(score18, product18);
				criteriaProduct18.setText(Integer.toString(product18)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(17, selection18);
			}
			if(dataBase.getNeedList().size() >= 19){
				score19 = Integer.parseInt(criteriaScore19.getText());
				product19 =  dataBase.getNeedList().get(18).getImportance() * Integer.parseInt(criteriaScore19.getText());					
				DesignSelection selection19 = new DesignSelection(score19, product19);
				criteriaProduct19.setText(Integer.toString(product19)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(18, selection19);
			}
			if(dataBase.getNeedList().size() >= 20){
				score20 = Integer.parseInt(criteriaScore20.getText());
				product20 =  dataBase.getNeedList().get(19).getImportance() * Integer.parseInt(criteriaScore20.getText());					
				DesignSelection selection20 = new DesignSelection(score20, product20);
				criteriaProduct20.setText(Integer.toString(product20)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(19, selection20);
			}
			if(dataBase.getNeedList().size() >= 21){
				score21 = Integer.parseInt(criteriaScore21.getText());
				product21 =  dataBase.getNeedList().get(20).getImportance() * Integer.parseInt(criteriaScore21.getText());					
				DesignSelection selection21 = new DesignSelection(score21, product21);
				criteriaProduct21.setText(Integer.toString(product21)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(20, selection21);
			}
			if(dataBase.getNeedList().size() >= 22){
				score22 = Integer.parseInt(criteriaScore22.getText());
				product22 =  dataBase.getNeedList().get(21).getImportance() * Integer.parseInt(criteriaScore22.getText());					
				DesignSelection selection22 = new DesignSelection(score22, product22);
				criteriaProduct22.setText(Integer.toString(product22)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(21, selection22);
			}
			if(dataBase.getNeedList().size() >= 23){
				score23 = Integer.parseInt(criteriaScore23.getText());
				product23 =  dataBase.getNeedList().get(22).getImportance() * Integer.parseInt(criteriaScore23.getText());					
				DesignSelection selection23 = new DesignSelection(score23, product23);
				criteriaProduct23.setText(Integer.toString(product23)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(22, selection23);
			}
			if(dataBase.getNeedList().size() >= 24){
				score24 = Integer.parseInt(criteriaScore24.getText());
				product24 =  dataBase.getNeedList().get(23).getImportance() * Integer.parseInt(criteriaScore24.getText());					
				DesignSelection selection24 = new DesignSelection(score24, product24);
				criteriaProduct24.setText(Integer.toString(product24)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(23, selection24);
			}
			if(dataBase.getNeedList().size() >= 25){
				score25 = Integer.parseInt(criteriaScore25.getText());
				product25 =  dataBase.getNeedList().get(24).getImportance() * Integer.parseInt(criteriaScore25.getText());					
				DesignSelection selection25 = new DesignSelection(score25, product25);
				criteriaProduct25.setText(Integer.toString(product25)); 
				dataBase.getCoaList().get(coaSelected1).addCriteriaSelection(24, selection25);
			}
			
			int product = product1 + product2 + product3 + product4 + product5 + product6 + product7 + product8 +
					product9 + product10 + product11 + product12 + product13 + product14 + product15 + product16 +
					product17 + product18 + product19 + product20 + product21 + product22 + product23 + product24 + product25;
			
			

					
			weightedScoreLabel1.setText(Integer.toString(product)); 
			dataBase.getCoaList().get(coaSelected1).setCriteriaEvaluation(product);
			setConceptLabel2.setText("");	
			setEvaluationValues();
			refreshTableView6();
			coaIsSelected1 = false;
			saved = false;	
		}
		
	}
	
	public void onCoaSelect1(){
		coaSelected1 = scoringTableView1.getSelectionModel().getSelectedIndex();
		coaIsSelected1 = true;
		setConceptLabel2.setText(dataBase.getCoaList().get(coaSelected1).getCoaName() + " Selected");
		
		try{
		if(dataBase.getNeedList().size() >= 1){
			criteriaScore1.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(0).getEvaluationScore()));
			criteriaProduct1.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(0).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 2){
			criteriaScore2.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(1).getEvaluationScore()));
			criteriaProduct2.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(1).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 3){
			criteriaScore3.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(2).getEvaluationScore()));
			criteriaProduct3.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(2).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 4){
			criteriaScore4.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(3).getEvaluationScore()));
			criteriaProduct4.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(3).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 5){
			criteriaScore5.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(4).getEvaluationScore()));
			criteriaProduct5.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(4).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 6){
			criteriaScore6.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(5).getEvaluationScore()));
			criteriaProduct6.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(5).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 7){
			criteriaScore7.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(6).getEvaluationScore()));
			criteriaProduct7.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(6).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 8){
			criteriaScore8.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(7).getEvaluationScore()));
			criteriaProduct8.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(7).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 9){
			criteriaScore9.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(8).getEvaluationScore()));
			criteriaProduct9.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(8).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 10){
			criteriaScore10.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(9).getEvaluationScore()));
			criteriaProduct10.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(9).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 11){
			criteriaScore11.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(10).getEvaluationScore()));
			criteriaProduct11.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(10).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 12){
			criteriaScore12.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(11).getEvaluationScore()));
			criteriaProduct12.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(11).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 13){
			criteriaScore13.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(12).getEvaluationScore()));
			criteriaProduct13.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(12).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 14){
			criteriaScore14.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(13).getEvaluationScore()));
			criteriaProduct14.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(13).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 15){
			criteriaScore15.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(14).getEvaluationScore()));
			criteriaProduct15.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(14).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 16){
			criteriaScore16.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(15).getEvaluationScore()));
			criteriaProduct16.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(15).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 17){
			criteriaScore17.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(16).getEvaluationScore()));
			criteriaProduct17.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(16).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 18){
			criteriaScore18.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(17).getEvaluationScore()));
			criteriaProduct18.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(17).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 19){
			criteriaScore19.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(18).getEvaluationScore()));
			criteriaProduct19.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(18).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 20){
			criteriaScore20.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(19).getEvaluationScore()));
			criteriaProduct20.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(19).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 21){
			criteriaScore21.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(20).getEvaluationScore()));
			criteriaProduct21.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(20).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 22){
			criteriaScore22.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(21).getEvaluationScore()));
			criteriaProduct22.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(21).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 23){
			criteriaScore23.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(22).getEvaluationScore()));
			criteriaProduct23.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(22).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 24){
			criteriaScore24.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(23).getEvaluationScore()));
			criteriaProduct24.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(23).getProduct()));
		}
		if(dataBase.getNeedList().size() >= 25){
			criteriaScore25.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(24).getEvaluationScore()));
			criteriaProduct25.setText(Integer.toString(dataBase.getCoaList().get(coaSelected1).getCriteriaSelection().get(24).getProduct()));
		}
		
		}catch(IndexOutOfBoundsException e){
			

		}
		
		refreshTableView6();
		startScoring = true;
	}
	
	public void setCriteriaLabels(){
		if(dataBase.getNeedList().size()>=1){
			coaOneNeed1.setText(dataBase.getNeedList().get(0).getNeed());
			coaNeedImport1.setText(Integer.toString(dataBase.getNeedList().get(0).getImportance()));
		}
		
		if(dataBase.getNeedList().size()>=2){
			coaOneNeed2.setText(dataBase.getNeedList().get(1).getNeed());
			coaNeedImport2.setText(Integer.toString(dataBase.getNeedList().get(1).getImportance()));
		}
		
		if(dataBase.getNeedList().size()>=3){
			coaOneNeed3.setText(dataBase.getNeedList().get(2).getNeed());
			coaNeedImport3.setText(Integer.toString(dataBase.getNeedList().get(2).getImportance()));
		}
		if(dataBase.getNeedList().size()>=4){
			coaOneNeed4.setText(dataBase.getNeedList().get(3).getNeed());
			coaNeedImport4.setText(Integer.toString(dataBase.getNeedList().get(3).getImportance()));
		}
		if(dataBase.getNeedList().size()>=5){
			coaOneNeed5.setText(dataBase.getNeedList().get(4).getNeed());
			coaNeedImport5.setText(Integer.toString(dataBase.getNeedList().get(4).getImportance()));
		}
		if(dataBase.getNeedList().size()>=6){
			coaOneNeed6.setText(dataBase.getNeedList().get(5).getNeed());
			coaNeedImport6.setText(Integer.toString(dataBase.getNeedList().get(5).getImportance()));
		}
		if(dataBase.getNeedList().size()>=7){
			coaOneNeed7.setText(dataBase.getNeedList().get(6).getNeed());
			coaNeedImport7.setText(Integer.toString(dataBase.getNeedList().get(6).getImportance()));
		}
		if(dataBase.getNeedList().size()>=7){
			coaOneNeed7.setText(dataBase.getNeedList().get(6).getNeed());
			coaNeedImport7.setText(Integer.toString(dataBase.getNeedList().get(6).getImportance()));
		}
		if(dataBase.getNeedList().size()>=8){
			coaOneNeed8.setText(dataBase.getNeedList().get(7).getNeed());
			coaNeedImport8.setText(Integer.toString(dataBase.getNeedList().get(7).getImportance()));
		}
		if(dataBase.getNeedList().size()>=9){
			coaOneNeed9.setText(dataBase.getNeedList().get(8).getNeed());
			coaNeedImport9.setText(Integer.toString(dataBase.getNeedList().get(8).getImportance()));
		}
		if(dataBase.getNeedList().size()>=10){
			coaOneNeed10.setText(dataBase.getNeedList().get(9).getNeed());
			coaNeedImport10.setText(Integer.toString(dataBase.getNeedList().get(9).getImportance()));
		}
		if(dataBase.getNeedList().size()>=11){
			coaOneNeed11.setText(dataBase.getNeedList().get(10).getNeed());
			coaNeedImport11.setText(Integer.toString(dataBase.getNeedList().get(10).getImportance()));
		}
		if(dataBase.getNeedList().size()>=12){
			coaOneNeed12.setText(dataBase.getNeedList().get(11).getNeed());
			coaNeedImport12.setText(Integer.toString(dataBase.getNeedList().get(11).getImportance()));
		}
		if(dataBase.getNeedList().size()>=13){
			coaOneNeed13.setText(dataBase.getNeedList().get(12).getNeed());
			coaNeedImport13.setText(Integer.toString(dataBase.getNeedList().get(12).getImportance()));
		}
		if(dataBase.getNeedList().size()>=14){
			coaOneNeed14.setText(dataBase.getNeedList().get(13).getNeed());
			coaNeedImport14.setText(Integer.toString(dataBase.getNeedList().get(13).getImportance()));
		}
		if(dataBase.getNeedList().size()>=15){
			coaOneNeed15.setText(dataBase.getNeedList().get(14).getNeed());
			coaNeedImport15.setText(Integer.toString(dataBase.getNeedList().get(14).getImportance()));
		}
		if(dataBase.getNeedList().size()>=16){
			coaOneNeed16.setText(dataBase.getNeedList().get(15).getNeed());
			coaNeedImport16.setText(Integer.toString(dataBase.getNeedList().get(15).getImportance()));
		}
		if(dataBase.getNeedList().size()>=17){
			coaOneNeed17.setText(dataBase.getNeedList().get(16).getNeed());
			coaNeedImport17.setText(Integer.toString(dataBase.getNeedList().get(16).getImportance()));
		}
		if(dataBase.getNeedList().size()>=18){
			coaOneNeed18.setText(dataBase.getNeedList().get(17).getNeed());
			coaNeedImport18.setText(Integer.toString(dataBase.getNeedList().get(17).getImportance()));
		}
		if(dataBase.getNeedList().size()>=19){
			coaOneNeed19.setText(dataBase.getNeedList().get(18).getNeed());
			coaNeedImport19.setText(Integer.toString(dataBase.getNeedList().get(18).getImportance()));
		}
		if(dataBase.getNeedList().size()>=20){
			coaOneNeed20.setText(dataBase.getNeedList().get(19).getNeed());
			coaNeedImport20.setText(Integer.toString(dataBase.getNeedList().get(19).getImportance()));
		}
		if(dataBase.getNeedList().size()>=21){
			coaOneNeed21.setText(dataBase.getNeedList().get(20).getNeed());
			coaNeedImport21.setText(Integer.toString(dataBase.getNeedList().get(20).getImportance()));
		}
		if(dataBase.getNeedList().size()>=22){
			coaOneNeed22.setText(dataBase.getNeedList().get(21).getNeed());
			coaNeedImport22.setText(Integer.toString(dataBase.getNeedList().get(21).getImportance()));
		}
		if(dataBase.getNeedList().size()>=23){
			coaOneNeed23.setText(dataBase.getNeedList().get(22).getNeed());
			coaNeedImport23.setText(Integer.toString(dataBase.getNeedList().get(22).getImportance()));
		}
		if(dataBase.getNeedList().size()>=24){
			coaOneNeed24.setText(dataBase.getNeedList().get(23).getNeed());
			coaNeedImport24.setText(Integer.toString(dataBase.getNeedList().get(23).getImportance()));
		}
		if(dataBase.getNeedList().size()>=25){
			coaOneNeed25.setText(dataBase.getNeedList().get(24).getNeed());
			coaNeedImport25.setText(Integer.toString(dataBase.getNeedList().get(24).getImportance()));
		}

	}
	
	/////////////////////////////////////////////////////////////////////////////////
	//////////////////////////CONCEPT SCORING I////////////////////////////////////////
	
	
	public void onCoaSelect(){ //Select a COA for evaluating within the Concept Evaluation tab
		coaSelected = scoringTableView.getSelectionModel().getSelectedIndex();
		coaIsSelected = true;
		setConceptLabel.setText(dataBase.getCoaList().get(coaSelected).getCoaName() + " Selected");
		
		if(!startScoring){
		
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Scoring Values");
			alert.setHeaderText("Enter a valid evaluation score");
			alert.setContentText("Ensure you enter one of the following values in the Evaluation column: 5, 4, 3, 2, 1, or 0");
			//alert.show();
			
			warningLabel.setText("Ensure you enter one of the following values in the Evaluation column: 5, 4, 3, 2, 1, or 0. " +
					"See the following scoring table:");
		}
		
		int specListSize = dataBase.getSpecList().size();
		int selectionSize = dataBase.getCoaList().get(coaSelected).getSelection().size();
		CoaSelectedLabel.setText("Selected Design: " + dataBase.getCoaList().get(coaSelected).getCoaName());
		
		try{
		
		if(dataBase.getSpecList().size() >= 1){
			evaluationScore1.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(0).getEvaluationScore()));
			evaluationProduct1.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(0).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 2){
			evaluationScore2.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(1).getEvaluationScore()));
			evaluationProduct2.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(1).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 3){
			evaluationScore3.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(2).getEvaluationScore()));
			evaluationProduct3.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(2).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 4){
			evaluationScore4.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(3).getEvaluationScore()));
			evaluationProduct4.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(3).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 5){
			evaluationScore5.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(4).getEvaluationScore()));
			evaluationProduct5.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(4).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 6){
			evaluationScore6.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(5).getEvaluationScore()));
			evaluationProduct6.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(5).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 7){
			evaluationScore7.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(6).getEvaluationScore()));
			evaluationProduct7.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(6).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 8){
			evaluationScore8.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(7).getEvaluationScore()));
			evaluationProduct8.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(7).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 9){
			evaluationScore9.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(8).getEvaluationScore()));
			evaluationProduct9.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(8).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 10){
			evaluationScore10.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(9).getEvaluationScore()));
			evaluationProduct10.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(9).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 11){
			evaluationScore11.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(10).getEvaluationScore()));
			evaluationProduct11.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(10).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 12){
			evaluationScore12.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(11).getEvaluationScore()));
			evaluationProduct12.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(11).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 13){
			evaluationScore13.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(12).getEvaluationScore()));
			evaluationProduct13.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(12).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 14){
			evaluationScore14.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(13).getEvaluationScore()));
			evaluationProduct14.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(13).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 15){
			evaluationScore15.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(14).getEvaluationScore()));
			evaluationProduct15.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(14).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 16){
			evaluationScore16.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(15).getEvaluationScore()));
			evaluationProduct16.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(15).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 17){
			evaluationScore17.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(16).getEvaluationScore()));
			evaluationProduct17.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(16).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 18){
			evaluationScore18.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(17).getEvaluationScore()));
			evaluationProduct18.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(17).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 19){
			evaluationScore19.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(18).getEvaluationScore()));
			evaluationProduct19.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(18).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 20){
			evaluationScore20.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(19).getEvaluationScore()));
			evaluationProduct20.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(19).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 21){
			evaluationScore21.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(20).getEvaluationScore()));
			evaluationProduct21.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(20).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 22){
			evaluationScore22.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(21).getEvaluationScore()));
			evaluationProduct22.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(21).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 23){
			evaluationScore23.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(22).getEvaluationScore()));
			evaluationProduct23.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(22).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 24){
			evaluationScore24.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(23).getEvaluationScore()));
			evaluationProduct24.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(23).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 25){
			evaluationScore25.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(24).getEvaluationScore()));
			evaluationProduct25.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(24).getProduct()));
		}
		}catch(IndexOutOfBoundsException e){
			
		}
		
		refreshTableView6();
		startScoring = true;
	}
	
	public void onDeleteScores(){
		int index = scoringTableView.getSelectionModel().getSelectedIndex();
		dataBase.getCoaList().get(index).setEvaluation(0);
		refreshTableView6();
	}
	
	
	public void onCalculate(){   //Performs the{ calculations in the Weighted Scoring Table
		
		if(!coaIsSelected == true) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Design Concept Selection");
			alert.setHeaderText("Design Concept Not Selected");
			alert.setContentText("Select a Design Concept First");
			alert.show();
		}
		else
		{
			int score1 = 0, score2 = 0, score3 = 0, score4 = 0, score5 = 0, score6 = 0, score7 = 0, score8 = 0, score9 = 0;
			int score10 = 0, score11 = 0, score12 = 0, score13 = 0, score14 = 0, score15 = 0, score16 = 0, score17=0, score18=0;
			int score19=0, score20=0, score21=0, score22 = 0, score23=0, score24=0, score25=0;
			int product1 = 0, product2 = 0, product3 = 0, product4 = 0, product5 = 0, product6 = 0, product7 = 0, product8 = 0, product9 = 0, product10 = 0, product11 = 0, product12 = 0, product13 = 0, product14 = 0, product15 = 0, 
					product16 = 0, product17 = 0, product18=0, product19 = 0, product20 = 0, product21 = 0, product22=0,product23=0,product24=0,product25=0;
			
			try{
				
				if(dataBase.getSpecList().size() >= 1){
					score1 = Integer.parseInt(evaluationScore1.getText());
					product1 =  dataBase.getSpecList().get(0).getImportance() * Integer.parseInt(evaluationScore1.getText());					
					DesignSelection selection1 = new DesignSelection(score1, product1);
					evaluationProduct1.setText(Integer.toString(product1)); 
					dataBase.getCoaList().get(coaSelected).addSelection(0, selection1);
				}
				
				if(dataBase.getSpecList().size() >= 2){
					score2 = Integer.parseInt(evaluationScore2.getText());
					product2 =  dataBase.getSpecList().get(1).getImportance() * Integer.parseInt(evaluationScore2.getText());					
					DesignSelection selection2 = new DesignSelection(score2, product2);
					evaluationProduct2.setText(Integer.toString(product2)); 
					dataBase.getCoaList().get(coaSelected).addSelection(1, selection2);
				}
				
				if(dataBase.getSpecList().size() >= 3){
					score3 = Integer.parseInt(evaluationScore3.getText());
					product3 =  dataBase.getSpecList().get(2).getImportance() * Integer.parseInt(evaluationScore3.getText());					
					DesignSelection selection3 = new DesignSelection(score3, product3);
					evaluationProduct3.setText(Integer.toString(product3)); 
					dataBase.getCoaList().get(coaSelected).addSelection(2, selection3);
				}
				
				if(dataBase.getSpecList().size() >= 4){
					score4 = Integer.parseInt(evaluationScore4.getText());
					product4 =  dataBase.getSpecList().get(3).getImportance() * Integer.parseInt(evaluationScore4.getText());					
					DesignSelection selection4 = new DesignSelection(score4, product4);
					evaluationProduct4.setText(Integer.toString(product4)); 
					dataBase.getCoaList().get(coaSelected).addSelection(3, selection4);
				}
				
				if(dataBase.getSpecList().size() >= 5){
					score5 = Integer.parseInt(evaluationScore5.getText());
					product5 =  dataBase.getSpecList().get(4).getImportance() * Integer.parseInt(evaluationScore5.getText());					
					DesignSelection selection5 = new DesignSelection(score5, product5);
					evaluationProduct5.setText(Integer.toString(product5)); 
					dataBase.getCoaList().get(coaSelected).addSelection(4, selection5);
				}
				
				if(dataBase.getSpecList().size() >= 6){
					score6 = Integer.parseInt(evaluationScore6.getText());
					product6 =  dataBase.getSpecList().get(5).getImportance() * Integer.parseInt(evaluationScore6.getText());					
					DesignSelection selection6 = new DesignSelection(score6, product6);
					evaluationProduct6.setText(Integer.toString(product6)); 
					dataBase.getCoaList().get(coaSelected).addSelection(5, selection6);
				}
				
				if(dataBase.getSpecList().size() >= 7){
					score7 = Integer.parseInt(evaluationScore7.getText());
					product7 =  dataBase.getSpecList().get(6).getImportance() * Integer.parseInt(evaluationScore7.getText());					
					DesignSelection selection7 = new DesignSelection(score7, product7);
					evaluationProduct7.setText(Integer.toString(product7)); 
					dataBase.getCoaList().get(coaSelected).addSelection(6, selection7);
				}
				
				if(dataBase.getSpecList().size() >= 8){
					score8 = Integer.parseInt(evaluationScore8.getText());
					product8 =  dataBase.getSpecList().get(7).getImportance() * Integer.parseInt(evaluationScore8.getText());					
					DesignSelection selection8 = new DesignSelection(score8, product8);
					evaluationProduct8.setText(Integer.toString(product8)); 
					dataBase.getCoaList().get(coaSelected).addSelection(7, selection8);
				}
				
				if(dataBase.getSpecList().size() >= 9){
					score9 = Integer.parseInt(evaluationScore9.getText());
					product9 =  dataBase.getSpecList().get(8).getImportance() * Integer.parseInt(evaluationScore9.getText());					
					DesignSelection selection9 = new DesignSelection(score9, product9);
					evaluationProduct9.setText(Integer.toString(product9)); 
					dataBase.getCoaList().get(coaSelected).addSelection(8, selection9);
				}
				
				if(dataBase.getSpecList().size() >= 10){
					score10 = Integer.parseInt(evaluationScore10.getText());
					product10 =  dataBase.getSpecList().get(9).getImportance() * Integer.parseInt(evaluationScore10.getText());					
					DesignSelection selection10 = new DesignSelection(score10, product10);
					evaluationProduct10.setText(Integer.toString(product10)); 
					dataBase.getCoaList().get(coaSelected).addSelection(9, selection10);
				}
				
				if(dataBase.getSpecList().size() >= 11){
					score11 = Integer.parseInt(evaluationScore11.getText());
					product11 =  dataBase.getSpecList().get(10).getImportance() * Integer.parseInt(evaluationScore11.getText());					
					DesignSelection selection11 = new DesignSelection(score11, product11);
					evaluationProduct11.setText(Integer.toString(product11)); 
					dataBase.getCoaList().get(coaSelected).addSelection(10, selection11);
				}
				
				if(dataBase.getSpecList().size() >= 12){
					score12 = Integer.parseInt(evaluationScore12.getText());
					product12 =  dataBase.getSpecList().get(11).getImportance() * Integer.parseInt(evaluationScore12.getText());					
					DesignSelection selection12 = new DesignSelection(score12, product12);
					evaluationProduct12.setText(Integer.toString(product12)); 
					dataBase.getCoaList().get(coaSelected).addSelection(11, selection12);
				}
				
				if(dataBase.getSpecList().size() >= 13){
					score13 = Integer.parseInt(evaluationScore13.getText());
					product13 =  dataBase.getSpecList().get(12).getImportance() * Integer.parseInt(evaluationScore13.getText());					
					DesignSelection selection13 = new DesignSelection(score13, product13);
					evaluationProduct13.setText(Integer.toString(product13)); 
					dataBase.getCoaList().get(coaSelected).addSelection(12, selection13);

				}
				
				if(dataBase.getSpecList().size() >= 14){
					score14 = Integer.parseInt(evaluationScore14.getText());
					product14 =  dataBase.getSpecList().get(13).getImportance() * Integer.parseInt(evaluationScore14.getText());					
					DesignSelection selection14 = new DesignSelection(score14, product14);
					evaluationProduct14.setText(Integer.toString(product14)); 
					dataBase.getCoaList().get(coaSelected).addSelection(13, selection14);
				}
				
				if(dataBase.getSpecList().size() >= 15){
					score15 = Integer.parseInt(evaluationScore15.getText());
					product15 =  dataBase.getSpecList().get(14).getImportance() * Integer.parseInt(evaluationScore15.getText());					
					DesignSelection selection15 = new DesignSelection(score15, product15);
					evaluationProduct15.setText(Integer.toString(product15)); 
					dataBase.getCoaList().get(coaSelected).addSelection(14, selection15);
				}
				
				if(dataBase.getSpecList().size() >= 16){
					score16 = Integer.parseInt(evaluationScore16.getText());
					product16 =  dataBase.getSpecList().get(15).getImportance() * Integer.parseInt(evaluationScore16.getText());					
					DesignSelection selection16 = new DesignSelection(score16, product16);
					evaluationProduct16.setText(Integer.toString(product16)); 
					dataBase.getCoaList().get(coaSelected).addSelection(15, selection16);
				}
				if(dataBase.getSpecList().size() >= 17){
					score17 = Integer.parseInt(evaluationScore17.getText());
					product17 =  dataBase.getSpecList().get(16).getImportance() * Integer.parseInt(evaluationScore17.getText());					
					DesignSelection selection17 = new DesignSelection(score17, product17);
					evaluationProduct17.setText(Integer.toString(product17)); 
					dataBase.getCoaList().get(coaSelected).addSelection(16, selection17);
				}
				if(dataBase.getSpecList().size() >= 18){
					score18 = Integer.parseInt(evaluationScore18.getText());
					product18 =  dataBase.getSpecList().get(17).getImportance() * Integer.parseInt(evaluationScore18.getText());					
					DesignSelection selection18 = new DesignSelection(score18, product18);
					evaluationProduct18.setText(Integer.toString(product18)); 
					dataBase.getCoaList().get(coaSelected).addSelection(17, selection18);
				}
				if(dataBase.getSpecList().size() >= 19){
					score19 = Integer.parseInt(evaluationScore19.getText());
					product19 =  dataBase.getSpecList().get(18).getImportance() * Integer.parseInt(evaluationScore19.getText());					
					DesignSelection selection19 = new DesignSelection(score19, product19);
					evaluationProduct19.setText(Integer.toString(product19)); 
					dataBase.getCoaList().get(coaSelected).addSelection(18, selection19);
				}
				if(dataBase.getSpecList().size() >= 20){
					score20 = Integer.parseInt(evaluationScore20.getText());
					product20 =  dataBase.getSpecList().get(19).getImportance() * Integer.parseInt(evaluationScore20.getText());					
					DesignSelection selection20 = new DesignSelection(score20, product20);
					evaluationProduct20.setText(Integer.toString(product20)); 
					dataBase.getCoaList().get(coaSelected).addSelection(19, selection20);
				}
				if(dataBase.getSpecList().size() >= 21){
					score21 = Integer.parseInt(evaluationScore21.getText());
					product21 =  dataBase.getSpecList().get(20).getImportance() * Integer.parseInt(evaluationScore21.getText());					
					DesignSelection selection21 = new DesignSelection(score21, product21);
					evaluationProduct21.setText(Integer.toString(product21)); 
					dataBase.getCoaList().get(coaSelected).addSelection(20, selection21);
				}
				if(dataBase.getSpecList().size() >= 22){
					score22 = Integer.parseInt(evaluationScore22.getText());
					product22 =  dataBase.getSpecList().get(21).getImportance() * Integer.parseInt(evaluationScore21.getText());					
					DesignSelection selection22 = new DesignSelection(score22, product22);
					evaluationProduct22.setText(Integer.toString(product22)); 
					dataBase.getCoaList().get(coaSelected).addSelection(21, selection22);
				}
				if(dataBase.getSpecList().size() >= 23){
					score23 = Integer.parseInt(evaluationScore23.getText());
					product23 =  dataBase.getSpecList().get(22).getImportance() * Integer.parseInt(evaluationScore22.getText());					
					DesignSelection selection23 = new DesignSelection(score23, product23);
					evaluationProduct23.setText(Integer.toString(product23)); 
					dataBase.getCoaList().get(coaSelected).addSelection(22, selection23);
				}
				if(dataBase.getSpecList().size() >= 24){
					score24 = Integer.parseInt(evaluationScore24.getText());
					product24 =  dataBase.getSpecList().get(23).getImportance() * Integer.parseInt(evaluationScore24.getText());					
					DesignSelection selection24 = new DesignSelection(score24, product24);
					evaluationProduct24.setText(Integer.toString(product24)); 
					dataBase.getCoaList().get(coaSelected).addSelection(23, selection24);
				}
				if(dataBase.getSpecList().size() >= 25){
					score25 = Integer.parseInt(evaluationScore25.getText());
					product25 =  dataBase.getSpecList().get(24).getImportance() * Integer.parseInt(evaluationScore25.getText());					
					DesignSelection selection25 = new DesignSelection(score25, product25);
					evaluationProduct25.setText(Integer.toString(product25)); 
					dataBase.getCoaList().get(coaSelected).addSelection(24, selection25);
				}
					
			}catch (NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Not a valid score");
				alert.setHeaderText("Error " + nfe.getMessage());
				alert.setContentText("Please enter one of the following integers: 5, 4, 3, 2, 1, or 0");
				alert.show();
	         }
					
			int product = product1 + product2 + product3 + product4 + product5 + product6 + product7 + product8 +
					product9 + product10 + product11 + product12 + product13 + product14 + product15 
					+ product16 + product17 + product18 + product19 + product20 + product21;
					
			weightedScoreLabel.setText(Integer.toString(product)); 
			dataBase.getCoaList().get(coaSelected).setEvaluation(product);
			CoaSelectedLabel.setText("");
			
			setEvaluationValues();	
			refreshTableView6();
		}
		setConceptLabel.setText("Select A Concept Before Calculating");

		coaIsSelected = false;
		saved = false;
	}  

	public void setSpecificationLabels(){      //Sets the Labels in the Weighted Scoring Table
		
		if(dataBase.getSpecList().size()>=1) {
			coaOneSpec1.setText(dataBase.getSpecList().get(0).getMetric());
			coaOneSpecImport1.setText(dataBase.getSpecList().get(0).toString());
			bmOneSpec1.setText(dataBase.getSpecList().get(0).getMetric());
			bmImport1.setText(dataBase.getSpecList().get(0).toString());
		}
		
		if(dataBase.getSpecList().size()>=2){
			coaOneSpec2.setText(dataBase.getSpecList().get(1).getMetric());
			coaOneSpecImport2.setText(dataBase.getSpecList().get(1).toString());
			bmOneSpec2.setText(dataBase.getSpecList().get(1).getMetric());
			bmImport2.setText(dataBase.getSpecList().get(1).toString());
		}
		
		if(dataBase.getSpecList().size()>=3){
			coaOneSpec3.setText(dataBase.getSpecList().get(2).getMetric());
			coaOneSpecImport3.setText(dataBase.getSpecList().get(2).toString());	
			bmOneSpec3.setText(dataBase.getSpecList().get(2).getMetric());
			bmImport3.setText(dataBase.getSpecList().get(2).toString());
		}
		
		if(dataBase.getSpecList().size()>=4) {
			coaOneSpec4.setText(dataBase.getSpecList().get(3).getMetric());
			coaOneSpecImport4.setText(dataBase.getSpecList().get(3).toString());
			bmOneSpec4.setText(dataBase.getSpecList().get(3).getMetric());
			bmImport4.setText(dataBase.getSpecList().get(3).toString());
		}
		
		if(dataBase.getSpecList().size()>=5) {
			coaOneSpec5.setText(dataBase.getSpecList().get(4).getMetric());
			coaOneSpecImport5.setText(dataBase.getSpecList().get(4).toString());
			bmOneSpec5.setText(dataBase.getSpecList().get(4).getMetric());
			bmImport5.setText(dataBase.getSpecList().get(4).toString());
		}
		
		if(dataBase.getSpecList().size()>=6) {
			coaOneSpec6.setText(dataBase.getSpecList().get(5).getMetric());
			coaOneSpecImport6.setText(dataBase.getSpecList().get(5).toString());
			bmOneSpec6.setText(dataBase.getSpecList().get(5).getMetric());
			bmImport6.setText(dataBase.getSpecList().get(5).toString());
		}
		
		if(dataBase.getSpecList().size()>=7) {
			coaOneSpec7.setText(dataBase.getSpecList().get(6).getMetric());
			coaOneSpecImport7.setText(dataBase.getSpecList().get(6).toString());
			bmOneSpec7.setText(dataBase.getSpecList().get(6).getMetric());
			bmImport7.setText(dataBase.getSpecList().get(6).toString());
		}
		
		if(dataBase.getSpecList().size()>=8) {
			coaOneSpec8.setText(dataBase.getSpecList().get(7).getMetric());
			coaOneSpecImport8.setText(dataBase.getSpecList().get(7).toString());
			bmOneSpec8.setText(dataBase.getSpecList().get(7).getMetric());
			bmImport8.setText(dataBase.getSpecList().get(7).toString());
		}
		
		if(dataBase.getSpecList().size()>=9) {
			coaOneSpec9.setText(dataBase.getSpecList().get(8).getMetric());
			coaOneSpecImport9.setText(dataBase.getSpecList().get(8).toString());
			bmOneSpec9.setText(dataBase.getSpecList().get(8).getMetric());
			bmImport9.setText(dataBase.getSpecList().get(8).toString());
		}
		if(dataBase.getSpecList().size()>=10) {
			coaOneSpec10.setText(dataBase.getSpecList().get(9).getMetric());
			coaOneSpecImport10.setText(dataBase.getSpecList().get(9).toString());
			bmOneSpec10.setText(dataBase.getSpecList().get(9).getMetric());
			bmImport10.setText(dataBase.getSpecList().get(9).toString());
		}
		
		if(dataBase.getSpecList().size()>=11) {
			coaOneSpec11.setText(dataBase.getSpecList().get(10).getMetric());
			coaOneSpecImport11.setText(dataBase.getSpecList().get(10).toString());
			bmOneSpec11.setText(dataBase.getSpecList().get(10).getMetric());
			bmImport11.setText(dataBase.getSpecList().get(10).toString());
		}
		
		if(dataBase.getSpecList().size()>=12) {
			coaOneSpec12.setText(dataBase.getSpecList().get(11).getMetric());
			coaOneSpecImport12.setText(dataBase.getSpecList().get(11).toString());
			bmOneSpec12.setText(dataBase.getSpecList().get(11).getMetric());
			bmImport12.setText(dataBase.getSpecList().get(11).toString());
		}
		
		if(dataBase.getSpecList().size()>=13) {
			coaOneSpec13a.setText(dataBase.getSpecList().get(12).getMetric());
			coaOneSpecImport13.setText(dataBase.getSpecList().get(12).toString());
			bmOneSpec13.setText(dataBase.getSpecList().get(12).getMetric());
			bmImport13.setText(dataBase.getSpecList().get(12).toString());
		}
		
		if(dataBase.getSpecList().size()>=14) {
			coaOneSpec14.setText(dataBase.getSpecList().get(13).getMetric());
			coaOneSpecImport14.setText(dataBase.getSpecList().get(13).toString());
			bmOneSpec14.setText(dataBase.getSpecList().get(13).getMetric());
			bmImport14.setText(dataBase.getSpecList().get(13).toString());
		}
		
		if(dataBase.getSpecList().size()>=15) {
			coaOneSpec15.setText(dataBase.getSpecList().get(14).getMetric());
			coaOneSpecImport15.setText(dataBase.getSpecList().get(14).toString());
			bmOneSpec15.setText(dataBase.getSpecList().get(14).getMetric());
			bmImport15.setText(dataBase.getSpecList().get(14).toString());
		}
		if(dataBase.getSpecList().size()>=16) {
			coaOneSpec16.setText(dataBase.getSpecList().get(15).getMetric());
			coaOneSpecImport16.setText(dataBase.getSpecList().get(15).toString());
			bmOneSpec16.setText(dataBase.getSpecList().get(15).getMetric());
			bmImport16.setText(dataBase.getSpecList().get(15).toString());
		}
		if(dataBase.getSpecList().size()>=17) {
			coaOneSpec17.setText(dataBase.getSpecList().get(16).getMetric());
			coaOneSpecImport17.setText(dataBase.getSpecList().get(16).toString());
			bmOneSpec17.setText(dataBase.getSpecList().get(16).getMetric());
			bmImport17.setText(dataBase.getSpecList().get(16).toString());
		}
		if(dataBase.getSpecList().size()>=18) {
			coaOneSpec18.setText(dataBase.getSpecList().get(17).getMetric());
			coaOneSpecImport18.setText(dataBase.getSpecList().get(17).toString());
			bmOneSpec18.setText(dataBase.getSpecList().get(17).getMetric());
			bmImport18.setText(dataBase.getSpecList().get(17).toString());
		}
		if(dataBase.getSpecList().size()>=19) {
			coaOneSpec19.setText(dataBase.getSpecList().get(18).getMetric());
			coaOneSpecImport19.setText(dataBase.getSpecList().get(18).toString());
			bmOneSpec19.setText(dataBase.getSpecList().get(18).getMetric());
			bmImport19.setText(dataBase.getSpecList().get(18).toString());
		}
		if(dataBase.getSpecList().size()>=20) {
			coaOneSpec20.setText(dataBase.getSpecList().get(19).getMetric());
			coaOneSpecImport20.setText(dataBase.getSpecList().get(19).toString());
			bmOneSpec20.setText(dataBase.getSpecList().get(19).getMetric());
			bmImport20.setText(dataBase.getSpecList().get(19).toString());
		}
		if(dataBase.getSpecList().size()>=21) {
			coaOneSpec21.setText(dataBase.getSpecList().get(20).getMetric());
			coaOneSpecImport21.setText(dataBase.getSpecList().get(20).toString());
			bmOneSpec21.setText(dataBase.getSpecList().get(20).getMetric());
			bmImport21.setText(dataBase.getSpecList().get(20).toString());
		}
		if(dataBase.getSpecList().size()>=22) {
			coaOneSpec22.setText(dataBase.getSpecList().get(21).getMetric());
			coaOneSpecImport22.setText(dataBase.getSpecList().get(21).toString());
			bmOneSpec22.setText(dataBase.getSpecList().get(21).getMetric());
			bmImport22.setText(dataBase.getSpecList().get(21).toString());
		}
		if(dataBase.getSpecList().size()>=23) {
			coaOneSpec23.setText(dataBase.getSpecList().get(22).getMetric());
			coaOneSpecImport23.setText(dataBase.getSpecList().get(22).toString());
			bmOneSpec23.setText(dataBase.getSpecList().get(22).getMetric());
			bmImport23.setText(dataBase.getSpecList().get(22).toString());
		}
		if(dataBase.getSpecList().size()>=24) {
			coaOneSpec24.setText(dataBase.getSpecList().get(23).getMetric());
			coaOneSpecImport24.setText(dataBase.getSpecList().get(23).toString());
			bmOneSpec24.setText(dataBase.getSpecList().get(23).getMetric());
			bmImport24.setText(dataBase.getSpecList().get(23).toString());
		}
		if(dataBase.getSpecList().size()>=25) {
			coaOneSpec25.setText(dataBase.getSpecList().get(24).getMetric());
			coaOneSpecImport25.setText(dataBase.getSpecList().get(24).toString());
			bmOneSpec25.setText(dataBase.getSpecList().get(24).getMetric());
			bmImport25.setText(dataBase.getSpecList().get(24).toString());
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////CONCEPT DEVELOPMENT - COA///////////////////////
	
	public void onCoaButton(){  //Creating a new COA
		if(newProject){
	
		if(!editCoaSet){  //new coa
			
			coa = new CourseOfAction(dataBase.getCoaNum(), coaName.getText(),coaDescription.getText(),coaStrength.getText(),coaWeakness.getText());
			coaNumber = dataBase.getCoaNum();
			editCoaSet = false;	
		}
			
		else {
			System.out.println("COA edit");
			int index = coaIndex;  //what coa is being edited or has been selected
			
			//ArrayList<DesignSelection> list = dataBase.getCoaList().get(index).getSelection();
						
			coa = new CourseOfAction(dataBase.getCoaNum(), coaName.getText(),coaDescription.getText(),coaStrength.getText(),coaWeakness.getText());

			//dataBase.getCoaList().get(coaSelected).setEvaluation(product);
			
			coa.setEvaluation(dataBase.getCoaList().get(index).getEvaluation());
			
			for(int i = 0; i < selected.size();i++){
				coa.addSelection(i, selected.get(i));
			}
			
			dataBase.getCoaList().remove(index);
						
			editCoaSet = false;		
		}
		
		dataBase.addCOA(coa);
				
		dataBase.setCoaIndex();
		
		TableViewCoa.setItems(dataBase.getCoaList());
		scoringTableView.setItems(dataBase.getCoaList());
		
		coaName.setText("");
		coaDescription.setText("");
		coaStrength.setText("");
		coaWeakness.setText("");
		
		refreshTableView5();
		saved = false;

		}
		else nullDatabaseWarning();
	}
	
	public void onDeleteConcept(){
		int index = TableViewCoa.getSelectionModel().getSelectedIndex();
		//coaName.setText(dataBase.getCoaList().get(index).getCoaName());
		//coaDescription.setText(dataBase.getCoaList().get(index).getDescription());
		//coaStrength.setText(dataBase.getCoaList().get(index).getStrengths());
		//coaWeakness.setText(dataBase.getCoaList().get(index).getWeakness());
		
		dataBase.getCoaList().remove(index);
		
		dataBase.setCoaIndex();
		refreshTableView5();
		saved = false;

	}
	
	public void onMousePressCoa(){//editing a COA
		editCoaSet = true;
		int index = TableViewCoa.getSelectionModel().getSelectedIndex();
		coaIndex = index;
		coaName.setText(dataBase.getCoaList().get(index).getCoaName());
		coaDescription.setText(dataBase.getCoaList().get(index).getDescription());
		coaStrength.setText(dataBase.getCoaList().get(index).getStrengths());
		coaWeakness.setText(dataBase.getCoaList().get(index).getWeakness());
				
		selected = dataBase.getCoaList().get(index).getSelection();//ArrayList<DesignSelection> 
		
		coaNumber = index + 1;
		
		//dataBase.getCoaList().remove(index);
		refreshTableView5();
		
		refreshTableView5();
	}
	
	///////////////////////////////////BENCHMARK SCORING///////////////////////////////////
	
	
	public void onProductSelect(){ //Select a Product for evaluating within the Benchmark Evaluation tab
		coaSelected = scoringTableView2.getSelectionModel().getSelectedIndex();
		coaIsSelected = true;
		setConceptLabel1.setText(dataBase.getBmList().get(coaSelected).getCoaName() + " Selected");
		
		if(!startScoring){
		
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Scoring Values");
			alert.setHeaderText("Enter a valid evaluation score");
			alert.setContentText("Ensure you enter one of the following values in the Evaluation column: 5, 4, 3, 2, 1, or 0");
			//alert.show();
			
			bmWarningLabel.setText("Ensure you enter one of the following values in the Evaluation column: 5, 4, 3, 2, 1, or 0. " +
					"See the following scoring table:");
		}
		
		int specListSize = dataBase.getSpecList().size();
		int selectionSize = dataBase.getBmList().get(coaSelected).getSelection().size();
		CoaSelectedLabel1.setText("Selected Design: " + dataBase.getBmList().get(coaSelected).getCoaName());
		
		try{
		
		if(dataBase.getSpecList().size() >= 1){
			bmScore1.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(0).getEvaluationScore()));
			bmProduct1.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(0).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 2){
			bmScore2.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(1).getEvaluationScore()));
			bmProduct2.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(1).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 3){
			bmScore3.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(2).getEvaluationScore()));
			bmProduct3.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(2).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 4){
			bmScore4.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(3).getEvaluationScore()));
			bmProduct4.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(3).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 5){
			bmScore5.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(4).getEvaluationScore()));
			bmProduct5.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(4).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 6){
			bmScore6.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(5).getEvaluationScore()));
			bmProduct6.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(5).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 7){
			bmScore7.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(6).getEvaluationScore()));
			bmProduct7.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(6).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 8){
			bmScore8.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(7).getEvaluationScore()));
			bmProduct8.setText(Integer.toString(dataBase.getCoaList().get(coaSelected).getSelection().get(7).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 9){
			bmScore9.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(8).getEvaluationScore()));
			bmProduct9.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(8).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 10){
			bmScore10.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(9).getEvaluationScore()));
			bmProduct10.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(9).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 11){
			bmScore11.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(10).getEvaluationScore()));
			bmProduct11.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(10).getProduct()));
		}
		
		if(dataBase.getSpecList().size() >= 12){
			bmScore12.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(11).getEvaluationScore()));
			bmProduct12.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(11).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 13){
			bmScore13.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(12).getEvaluationScore()));
			bmProduct13.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(12).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 14){
			bmScore14.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(13).getEvaluationScore()));
			bmProduct14.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(13).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 15){
			bmScore15.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(14).getEvaluationScore()));
			bmProduct15.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(14).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 16){
			bmScore16.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(15).getEvaluationScore()));
			bmProduct16.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(15).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 17){
			bmScore17.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(16).getEvaluationScore()));
			bmProduct17.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(16).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 18){
			bmScore18.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(17).getEvaluationScore()));
			bmProduct18.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(17).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 19){
			bmScore19.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(18).getEvaluationScore()));
			bmProduct19.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(18).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 20){
			bmScore20.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(19).getEvaluationScore()));
			bmProduct20.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(19).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 21){
			bmScore21.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(20).getEvaluationScore()));
			bmProduct21.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(20).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 22){
			bmScore22.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(21).getEvaluationScore()));
			bmProduct22.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(21).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 23){
			bmScore23.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(22).getEvaluationScore()));
			bmProduct23.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(22).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 24){
			bmScore24.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(23).getEvaluationScore()));
			bmProduct24.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(23).getProduct()));
		}
		if(dataBase.getSpecList().size() >= 25){
			bmScore25.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(24).getEvaluationScore()));
			bmProduct25.setText(Integer.toString(dataBase.getBmList().get(coaSelected).getSelection().get(24).getProduct()));
		}
		}catch(IndexOutOfBoundsException e){
			
		}
		
		refreshTableView6();
		startScoring = true;
	}
	
	public void onDeleteProductScores(){
		int index = scoringTableView2.getSelectionModel().getSelectedIndex();
			
		dataBase.getBmList().get(index).setEvaluation(0);
		refreshTableView6();

	}
	
	public void onCalculate2(){   //Performs the Benchmark calculations in the Weighted Scoring Table
		
		if(!coaIsSelected == true) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Benchmark Selection");
			alert.setHeaderText("Benchmark Not Selected");
			alert.setContentText("Select a Benchmark First");
			alert.show();
		}
		else
		{
			int score1 = 0, score2 = 0, score3 = 0, score4 = 0, score5 = 0, score6 = 0, score7 = 0, score8 = 0, score9 = 0;
			int score10 = 0, score11 = 0, score12 = 0, score13 = 0, score14 = 0, score15 = 0, 
					score16 = 0, score17=0, score18=0, score19=0, score20=0, score21=0, score22=0, score23=0, score24=0, score25=0;
			int product1 = 0, product2 = 0, product3 = 0, product4 = 0, product5 = 0, product6 = 0, product7 = 0, product8 = 0,product9 = 0, product10 = 0, product11 = 0, product12 = 0, product13 = 0, product14 = 0, product15 = 0, 
					product16 = 0, product17=0, product18=0, product19=0, product20=0, product21=0, product22=0, product23=0, product24=0, product25=0;
			
			try{
				
				if(dataBase.getSpecList().size() >= 1){
					score1 = Integer.parseInt(bmScore1.getText());
					product1 =  dataBase.getSpecList().get(0).getImportance() * Integer.parseInt(bmScore1.getText());					
					DesignSelection selection1 = new DesignSelection(score1, product1);
					bmProduct1.setText(Integer.toString(product1)); 
					dataBase.getBmList().get(coaSelected).addSelection(0, selection1);
				}
				
				if(dataBase.getSpecList().size() >= 2){
					score2 = Integer.parseInt(bmScore2.getText());
					product2 =  dataBase.getSpecList().get(1).getImportance() * Integer.parseInt(bmScore2.getText());					
					DesignSelection selection2 = new DesignSelection(score2, product2);
					bmProduct2.setText(Integer.toString(product2)); 
					dataBase.getBmList().get(coaSelected).addSelection(1, selection2);
				}
				
				if(dataBase.getSpecList().size() >= 3){
					score3 = Integer.parseInt(bmScore3.getText());
					product3 =  dataBase.getSpecList().get(2).getImportance() * Integer.parseInt(bmScore3.getText());					
					DesignSelection selection3 = new DesignSelection(score3, product3);
					bmProduct3.setText(Integer.toString(product3)); 
					dataBase.getBmList().get(coaSelected).addSelection(2, selection3);
				}
				
				if(dataBase.getSpecList().size() >= 4){
					score4 = Integer.parseInt(bmScore4.getText());
					product4 =  dataBase.getSpecList().get(3).getImportance() * Integer.parseInt(bmScore4.getText());					
					DesignSelection selection4 = new DesignSelection(score4, product4);
					bmProduct4.setText(Integer.toString(product4)); 
					dataBase.getBmList().get(coaSelected).addSelection(3, selection4);
				}
				
				if(dataBase.getSpecList().size() >= 5){
					score5 = Integer.parseInt(bmScore5.getText());
					product5 =  dataBase.getSpecList().get(4).getImportance() * Integer.parseInt(bmScore5.getText());					
					DesignSelection selection5 = new DesignSelection(score5, product5);
					bmProduct5.setText(Integer.toString(product5)); 
					dataBase.getBmList().get(coaSelected).addSelection(4, selection5);
				}
				
				if(dataBase.getSpecList().size() >= 6){
					score6 = Integer.parseInt(bmScore6.getText());
					product6 =  dataBase.getSpecList().get(5).getImportance() * Integer.parseInt(bmScore6.getText());					
					DesignSelection selection6 = new DesignSelection(score6, product6);
					bmProduct6.setText(Integer.toString(product6)); 
					dataBase.getBmList().get(coaSelected).addSelection(5, selection6);
				}
				
				if(dataBase.getSpecList().size() >= 7){
					score7 = Integer.parseInt(bmScore7.getText());
					product7 =  dataBase.getSpecList().get(6).getImportance() * Integer.parseInt(bmScore7.getText());					
					DesignSelection selection7 = new DesignSelection(score7, product7);
					bmProduct7.setText(Integer.toString(product7)); 
					dataBase.getBmList().get(coaSelected).addSelection(6, selection7);
				}
				
				if(dataBase.getSpecList().size() >= 8){
					score8 = Integer.parseInt(bmScore8.getText());
					product8 =  dataBase.getSpecList().get(7).getImportance() * Integer.parseInt(bmScore8.getText());					
					DesignSelection selection8 = new DesignSelection(score8, product8);
					bmProduct8.setText(Integer.toString(product8)); 
					dataBase.getBmList().get(coaSelected).addSelection(7, selection8);
				}
				
				if(dataBase.getSpecList().size() >= 9){
					score9 = Integer.parseInt(bmScore9.getText());
					product9 =  dataBase.getSpecList().get(8).getImportance() * Integer.parseInt(bmScore9.getText());					
					DesignSelection selection9 = new DesignSelection(score9, product9);
					bmProduct9.setText(Integer.toString(product9)); 
					dataBase.getBmList().get(coaSelected).addSelection(8, selection9);
				}
				
				if(dataBase.getSpecList().size() >= 10){
					score10 = Integer.parseInt(bmScore10.getText());
					product10 =  dataBase.getSpecList().get(9).getImportance() * Integer.parseInt(bmScore10.getText());					
					DesignSelection selection10 = new DesignSelection(score10, product10);
					bmProduct10.setText(Integer.toString(product10)); 
					dataBase.getBmList().get(coaSelected).addSelection(9, selection10);
				}
				
				if(dataBase.getSpecList().size() >= 11){
					score11 = Integer.parseInt(bmScore11.getText());
					product11 =  dataBase.getSpecList().get(10).getImportance() * Integer.parseInt(bmScore11.getText());					
					DesignSelection selection11 = new DesignSelection(score11, product11);
					bmProduct11.setText(Integer.toString(product11)); 
					dataBase.getBmList().get(coaSelected).addSelection(10, selection11);
				}
				
				if(dataBase.getSpecList().size() >= 12){
					score12 = Integer.parseInt(bmScore12.getText());
					product12 =  dataBase.getSpecList().get(11).getImportance() * Integer.parseInt(bmScore12.getText());					
					DesignSelection selection12 = new DesignSelection(score12, product12);
					bmProduct12.setText(Integer.toString(product12)); 
					dataBase.getBmList().get(coaSelected).addSelection(11, selection12);
				}
				
				if(dataBase.getSpecList().size() >= 13){
					score13 = Integer.parseInt(bmScore13.getText());
					product13 =  dataBase.getSpecList().get(12).getImportance() * Integer.parseInt(bmScore13.getText());					
					DesignSelection selection13 = new DesignSelection(score13, product13);
					bmProduct13.setText(Integer.toString(product13)); 
					dataBase.getBmList().get(coaSelected).addSelection(12, selection13);
				}
				
				if(dataBase.getSpecList().size() >= 14){
					score14 = Integer.parseInt(bmScore14.getText());
					product14 =  dataBase.getSpecList().get(13).getImportance() * Integer.parseInt(bmScore14.getText());					
					DesignSelection selection14 = new DesignSelection(score14, product14);
					bmProduct14.setText(Integer.toString(product14)); 
					dataBase.getBmList().get(coaSelected).addSelection(13, selection14);
				}
				
				if(dataBase.getSpecList().size() >= 15){
					score15 = Integer.parseInt(bmScore15.getText());
					product15 =  dataBase.getSpecList().get(14).getImportance() * Integer.parseInt(bmScore15.getText());					
					DesignSelection selection15 = new DesignSelection(score15, product15);
					bmProduct15.setText(Integer.toString(product15)); 
					dataBase.getBmList().get(coaSelected).addSelection(14, selection15);
				}
				
				if(dataBase.getSpecList().size() >= 16){
					score16 = Integer.parseInt(bmScore16.getText());
					product16 =  dataBase.getSpecList().get(15).getImportance() * Integer.parseInt(bmScore16.getText());					
					DesignSelection selection16 = new DesignSelection(score16, product16);
					bmProduct16.setText(Integer.toString(product16)); 
					dataBase.getBmList().get(coaSelected).addSelection(15, selection16);	
				}
				if(dataBase.getSpecList().size() >= 17){
					score17 = Integer.parseInt(bmScore17.getText());
					product17 =  dataBase.getSpecList().get(16).getImportance() * Integer.parseInt(bmScore17.getText());					
					DesignSelection selection17 = new DesignSelection(score17, product17);
					bmProduct17.setText(Integer.toString(product17)); 
					dataBase.getBmList().get(coaSelected).addSelection(16, selection17);	
				}
				if(dataBase.getSpecList().size() >= 18){
					score18 = Integer.parseInt(bmScore18.getText());
					product18 =  dataBase.getSpecList().get(17).getImportance() * Integer.parseInt(bmScore18.getText());					
					DesignSelection selection18 = new DesignSelection(score18, product18);
					bmProduct18.setText(Integer.toString(product18)); 
					dataBase.getBmList().get(coaSelected).addSelection(17, selection18);	
				}
				if(dataBase.getSpecList().size() >= 19){
					score19 = Integer.parseInt(bmScore19.getText());
					product19 =  dataBase.getSpecList().get(18).getImportance() * Integer.parseInt(bmScore19.getText());					
					DesignSelection selection19 = new DesignSelection(score19, product19);
					bmProduct19.setText(Integer.toString(product19)); 
					dataBase.getBmList().get(coaSelected).addSelection(18, selection19);	
				}
				if(dataBase.getSpecList().size() >= 20){
					score20 = Integer.parseInt(bmScore20.getText());
					product20 =  dataBase.getSpecList().get(19).getImportance() * Integer.parseInt(bmScore20.getText());					
					DesignSelection selection20 = new DesignSelection(score20, product20);
					bmProduct20.setText(Integer.toString(product20)); 
					dataBase.getBmList().get(coaSelected).addSelection(19, selection20);	
				}
				if(dataBase.getSpecList().size() >= 21){
					score21 = Integer.parseInt(bmScore21.getText());
					product21 =  dataBase.getSpecList().get(20).getImportance() * Integer.parseInt(bmScore21.getText());					
					DesignSelection selection21 = new DesignSelection(score21, product21);
					bmProduct21.setText(Integer.toString(product21)); 
					dataBase.getBmList().get(coaSelected).addSelection(20, selection21);	
				}
				if(dataBase.getSpecList().size() >= 22){
					score22 = Integer.parseInt(bmScore22.getText());
					product22 =  dataBase.getSpecList().get(21).getImportance() * Integer.parseInt(bmScore22.getText());					
					DesignSelection selection22 = new DesignSelection(score22, product22);
					bmProduct22.setText(Integer.toString(product22)); 
					dataBase.getBmList().get(coaSelected).addSelection(21, selection22);	
				}
				if(dataBase.getSpecList().size() >= 23){
					score23 = Integer.parseInt(bmScore23.getText());
					product23 =  dataBase.getSpecList().get(22).getImportance() * Integer.parseInt(bmScore23.getText());					
					DesignSelection selection23 = new DesignSelection(score23, product23);
					bmProduct23.setText(Integer.toString(product23)); 
					dataBase.getBmList().get(coaSelected).addSelection(22, selection23);	
				}
				if(dataBase.getSpecList().size() >= 24){
					score24 = Integer.parseInt(bmScore24.getText());
					product24 =  dataBase.getSpecList().get(23).getImportance() * Integer.parseInt(bmScore24.getText());					
					DesignSelection selection24 = new DesignSelection(score24, product24);
					bmProduct24.setText(Integer.toString(product24)); 
					dataBase.getBmList().get(coaSelected).addSelection(23, selection24);	
				}
				if(dataBase.getSpecList().size() >= 25){
					score25 = Integer.parseInt(bmScore25.getText());
					product25 =  dataBase.getSpecList().get(24).getImportance() * Integer.parseInt(bmScore25.getText());					
					DesignSelection selection25 = new DesignSelection(score25, product25);
					bmProduct25.setText(Integer.toString(product25)); 
					dataBase.getBmList().get(coaSelected).addSelection(24, selection25);	
				}

			}catch (NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Not a valid score");
				alert.setHeaderText("Error " + nfe.getMessage());
				alert.setContentText("Please enter one of the following integers: 5, 4, 3, 2, 1, or 0");
				alert.show();
	         }
					
			int product = product1 + product2 + product3 + product4 + product5 + product6 + product7 + product8 +
					product9 + product10 + product11 + product12 + product13 + product14 + product15 
					+ product16 + product17 + product18 + product19 + product20 + product21 + product22 + product23
					+ product24 + product25;
					
			weightedScoreLabel2.setText(Integer.toString(product)); 
			dataBase.getBmList().get(coaSelected).setEvaluation(product);
			CoaSelectedLabel.setText("");
			
			setEvaluationValues();
			refreshTableView6();
		}
		
		setConceptLabel.setText("Select A Concept Before Calculating");

		coaIsSelected = false;
		saved = false;

	}  
	

	
		//////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////BENCHMARKING///////////////////////
		
	public void onBmButton(){  //Creating a new COA
		if(newProject){
		
		if(!editBmSet){  //if it is false or not being edited
		
			bm = new BenchMark(dataBase.getBmNum(), coaName1.getText(),coaDescription1.getText(),coaStrength1.getText(),coaWeakness1.getText());
			
			bmNumber = dataBase.getBmNum();
			
			System.out.println("Add BM");
			editBmSet = false;	
		}
		
		
		
		else {  //if it is being edited
			System.out.println("BM edit");
			int index = bmIndex;
						
			bm = new BenchMark(dataBase.getBmNum(), coaName1.getText(),coaDescription1.getText(),coaStrength1.getText(),coaWeakness1.getText());
						
			bm.setEvaluation(dataBase.getBmList().get(index).getEvaluation());
		
			for(int i = 0; i < selected.size();i++){
				bm.addSelection(i, selected.get(i));
			}
			
			dataBase.getBmList().remove(index);
						
			editBmSet = false;		
		}
		
		
		 
		dataBase.addBm(bm);
		
		dataBase.setBmIndex();
		
		TableViewCoa1.setItems(dataBase.getBmList());
		scoringTableView2.setItems(dataBase.getBmList());
		
		coaName1.setText("");
		coaDescription1.setText("");
		coaStrength1.setText("");
		coaWeakness1.setText("");
		
		refreshTableView5();
		refreshTableView6();

		saved = false;
		
		}
			else nullDatabaseWarning();
		}
		
	public void onDeleteBm(){
		
		
		int index = TableViewCoa1.getSelectionModel().getSelectedIndex();
		//coaName1.setText(dataBase.getBmList().get(index).getCoaName());
		//coaDescription1.setText(dataBase.getBmList().get(index).getDescription());
		//coaStrength1.setText(dataBase.getBmList().get(index).getStrengths());
		//coaWeakness1.setText(dataBase.getBmList().get(index).getWeakness());
			
		dataBase.getBmList().remove(index);
			
		dataBase.setBmIndex();
		refreshTableView5();
		saved = false;
		
	}
		
		public void onMousePressBm(){//editing a Benchmark
			editBmSet = true;
			int index = TableViewCoa1.getSelectionModel().getSelectedIndex();
			bmIndex = index;
			coaName1.setText(dataBase.getBmList().get(index).getCoaName());
			coaDescription1.setText(dataBase.getBmList().get(index).getDescription());
			coaStrength1.setText(dataBase.getBmList().get(index).getStrengths());
			coaWeakness1.setText(dataBase.getBmList().get(index).getWeakness());
			
			selected = dataBase.getBmList().get(index).getSelection();//ArrayList<DesignSelection> 
			
			bmNumber = index + 1;
			
			refreshTableView5();
			refreshTableView6();
		}
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////
	////////////////////////     SPECIFICATIONS     //////////////////////////////////////

	public void onSelectNeed(){   //tab two selection of needs for a spec
		 specEdit = true;
		Specification spec = dataBase.getSpecList().get(needIndex);
		
		 System.out.println("Specification selected: " + spec.getMetric());
		
		 Integer index = tableViewNeeds3.getSelectionModel().getSelectedIndex();
		 System.out.println("Need selected: " + dataBase.getNeedList().get(index).getNeed());
		 
		 spec.setNeeds(index);
		System.out.println("need list size: "+ spec.getNeeds().size());
	
		specificationTableView3.setItems(dataBase.getSpecList());
		
		refreshTableView4();

	}
	
	public void onSelectNeedStrong(){   //tab two selection of needs for a spec
		specEdit = true;
		Specification spec = dataBase.getSpecList().get(needIndex);
		
		System.out.println("Specification selected: " + spec.getMetric());
		
		Integer index = tableViewNeeds3.getSelectionModel().getSelectedIndex();
		System.out.println("Need selected: " + dataBase.getNeedList().get(index).getNeed());
		 
		//spec.setNeeds(index);
		spec.setNeeds(index);
		System.out.println("need list size: "+ spec.getNeeds().size());
	
		specificationTableView3.setItems(dataBase.getSpecList());
		
		refreshTableView4();

	}
	
	public void removeNeed(){
		Specification spec = dataBase.getSpecList().get(needIndex);
		Integer index = tableViewNeeds3.getSelectionModel().getSelectedIndex();
		int remove = dataBase.getNeedList().get(index).getNumber();
		spec.removeNeed(remove);
		refreshTableView4();
	}
	
	public void OnClearNeeds(){
		specEdit = true;
		Integer index = specificationTableView3.getSelectionModel().getSelectedIndex();

		
		Specification spec = dataBase.getSpecList().get(index);

		//tableViewNeeds3
		//int remove = dataBase.getNeedList().get(index).getNumber();
		
		//spec.removeNeedsShown(index);
		
		dataBase.getSpecList().get(index).clearNeeds();;
		
		//Integer num = spec.getNeedsShown().get(index).intValue();
		//spec.removeNeedsShown(num);
		
		refreshTableView4();
		specEdit = false;

	}
	
	public void changeSpecTab(){
		specEdit = false;
		System.out.println("Changing tabs");
	}
	
	public void onSelectSpecification2(){
		try{
		specEdit = false;
		
		needIndex = specificationTableView3.getSelectionModel().getSelectedIndex();
		metrixSelected.setText(dataBase.getSpecList().get(needIndex).getMetric());
		refreshTableView4();
		//return spec = dataBase.getSpecList().get(needIndex);

		}catch(ArrayIndexOutOfBoundsException e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Select Metric");
			alert.setContentText("Make sure you select the Metric's row");
			alert.setHeaderText("Selection Warning");
		}
				 
	}
	
	public void onSpecificationButton(){  //Selecting the DEFINE button
		
		if(newProject){  //are we working in a project

		Integer num = 0;
		if(specVeryImport.isSelected()) num = 5;
		else if(specImport.isSelected()) num = 4;
		else if (specOkay.isSelected()) num = 3;
		else if (specLittleImport.isSelected()) num = 2;
		else if(specNotImport.isSelected()) num = 1;		
		
		if(!specEdit){
			System.out.println("Not Editing Specification");

			spec = new Specification(dataBase.getSpecNum(), num, metricField.getText(),unitField.getText(),
						valueField.getText(), new String(""));
						
		}
		else {
			System.out.println("Editing Specification number " + editSpecIndex);
			dataBase.getSpecList().remove(editSpecIndex);		
			spec = new Specification(dataBase.getSpecList().size()-1, num, metricField.getText(),unitField.getText(),
					valueField.getText(),tempEvaluation);
			specEdit = false;
		}
		
		dataBase.addSpecification(spec);
		
		dataBase.setSpecificationsIndex(); //used to reorder specification number and array index
		
		specificationTableView.setItems(dataBase.getSpecList());
		specificationTableView2.setItems(dataBase.getSpecList());
		specificationTableView3.setItems(dataBase.getSpecList());

		clearSpecificationFields();
		
		setSpecificationLabels();//Sets Labels in the COA Tab
		setCriteriaLabels();
		refreshTableView4();
		
		specEdit = false;  //ensures that specEdit is false
		saved = false;

		}
		else nullDatabaseWarning();
		
	}
	
	
	
	public void onEditSpecification(){  //Selecting the Edit Specification menu on the Context Menu in Specification Tab I
		specEdit = true;		//editing spec
				
		editSpecIndex = specificationTableView.getSelectionModel().getSelectedIndex(); //select the index of the specification object
		
		try{
			metricField.setText(dataBase.getSpecList().get(editSpecIndex).getMetric()); //set the Text fields
			unitField.setText(dataBase.getSpecList().get(editSpecIndex).getUnit());
			valueField.setText(dataBase.getSpecList().get(editSpecIndex).getValue());
			
			Integer num = dataBase.getSpecList().get(editSpecIndex).getImportance(); //Set the Spec importance label
			
			if(num==5) specVeryImport.setSelected(true);
			else if(num==4) specImport.setSelected(true);
			else if(num==3) specOkay.setSelected(true);
			else if(num==2) specLittleImport.setSelected(true);
			else if(num==1) specNotImport.setSelected(true);
			
			tempEvaluation = dataBase.getSpecList().get(editSpecIndex).getEvaluation();
		
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Array out of bounds");
		}
		
		dataBase.getSpecList().get(editSpecIndex).clearNeeds();
		
		refreshTableView4();
	}
	
	
	public void onDeleteSpecification(){  //Deleting a Specification
		
		int index = specificationTableView.getSelectionModel().getSelectedIndex();//Get index
		dataBase.getSpecList().remove(index);
		dataBase.setSpecificationsIndex(); //Recalculates the Specification Number
				
		refreshTableView4();
		refreshTableView4a();
		saved = false;
	}
	
	
	public void onSelectSpecification(){  //Specification II Table Select Spec Context menu
		
		try{
			int index = specificationTableView2.getSelectionModel().getSelectedIndex();
			evaluationIndex = index;
			
			evaluationText.setText(dataBase.getSpecList().get(index).getEvaluation());
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("array out of bounds");
		}
		
		refreshTableView4a();
	}
	
	public void onSelectEvaluationText(){  //Specification II Tab OKAY button
		
		if(newProject){
			String text = evaluationText.getText();
			dataBase.getSpecList().get(evaluationIndex).setEvaluation(text);
			evaluationText.setText(" ");//Clear the evaluation field
			refreshTableView4a();
		}
		else nullDatabaseWarning();
	}
	
	public void onDeleteEvaluation(){
		int index = specificationTableView2.getSelectionModel().getSelectedIndex();
		refreshTableView4a();
	}
	
	
	public void clearSpecificationFields(){  //Clears all the fields in Specification Tab I
		metricField.setText("");
		unitField.setText("");
		valueField.setText("");
		specVeryImport.setSelected(false);
		specImport.setSelected(false);
		specOkay.setSelected(false);
		specLittleImport.setSelected(false);
		specNotImport.setSelected(false);
		
	}
	
	
	///////////////////////////////////////////////////////////////////////////////	
	////////////////////////////////////////Product Needs////////////////////
	
	public void onNeedOkButton(){   //Create a new need

		if(newProject){			//We need a new project or an opened project
				
			Integer num = 0;
			if(needVeryImport.isSelected()) num = 4;
			else if(needImport.isSelected()) num = 3;
			else if (needOkay.isSelected()) num = 2;
			else if(needNotImport.isSelected()) num = 1;
						
			if(!needEdit){
				need = new Needs(dataBase.getNeedNum(), needStatement.getText(),num);
				System.out.println("New Need");
			}
						
			else {
				
				int index = needEdited;
				System.out.println("index added before delete " + Integer.toString(dataBase.getNeedList().size()));
				dataBase.getNeedList().remove(index);
				need = new Needs(dataBase.getNeedList().size()-1, needStatement.getText(), num);
				System.out.println("index added after delete " + Integer.toString(dataBase.getNeedList().size()));
				needEdit = false;
			}
			
			System.out.println("index before add " + Integer.toString(dataBase.getNeedList().size()));
			
			dataBase.setNeedList(need);
			
			System.out.println("index after add " + Integer.toString(dataBase.getNeedList().size()));

			
			dataBase.setNeedsIndex();  //reorders the index for the need st
			
			tableViewNeeds.setItems(dataBase.getNeedList());
						
			needStatement.setText("");
			
			setCriteriaLabels();
			refreshTableView2();
			refreshTableView3();
			
			saved = false;

			}
			
		else nullDatabaseWarning();
		
	}
		
		public void onEditNeedsMenu(){   ///Editing a Product or Customer Need
			needEdit = true;
			
			int index = tableViewNeeds.getSelectionModel().getSelectedIndex();  // get index and set to global variable
			needEdited = index;
			needNumberSet = dataBase.getNeedList().get(index).getNumber();//get Need number
			needString = dataBase.getNeedList().get(index).getNeed();//get need statement
			needImportance = dataBase.getNeedList().get(index).getImportance();//get importance level
			
			needStatement.setText(dataBase.getNeedList().get(index).getNeed());//set Need TextField
			
			Integer num = dataBase.getNeedList().get(index).getImportance();//set importance level
			if(num==4) needVeryImport.setSelected(true);
			else if(num==3) needImport.setSelected(true);
			else if(num==2) needOkay.setSelected(true);
			else if(num==1) needNotImport.setSelected(true);

			
			System.out.println(index + " need edited");
			
			saved = false;
			
			refreshTableView2();
			refreshTableView3();
		
		}
		
		public void onDeleteNeed(){
			System.out.println("Delete Need");
			int index = tableViewNeeds.getSelectionModel().getSelectedIndex();
			dataBase.getNeedList().remove(index);
			dataBase.setNeedsIndex();
			needDeleted = true;
			needWarningLabel.setText("One or more Customer Needs have been deleted.  Please reevaluate your Specifications.");
			
			refreshTableView2();
			refreshTableView3();
			refreshTableView4();
			saved = false;

		}
	
	///////////////////Project Details II//////////////////////////////
		
		public void onEnterProjectDetail(){
			if(newProject){
				
				String goals = projectDetailGoals.getText();
				String primaryMarket = projectDetailPrimaryMarket.getText();
				String secondaryMarket = projectDetailSecondaryMarket.getText();
				String assumptions = projectDetailAssumptions.getText();
				String stakeholders = projectDetailStakeholders.getText();
				
				dataBase.setBusinessGoals(goals);
				dataBase.setPrimaryMarket(primaryMarket);
				dataBase.setSecondaryMarket(secondaryMarket);
				dataBase.setAssumptions(assumptions);
				dataBase.setStakeholders(stakeholders);
				
				
				refreshTableView();
				refreshTableView2();
				refreshTableView3();
				refreshTableView4();
				saved = false;

				
			} else {
							
				nullDatabaseWarning();
				
			}
			
		}
	
		////////////////Project Functions////////////////////
	
	public void onNewProjectButton(){      //Start a new project
		
		if(dataBase != null){
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Save Warning");
			alert.setHeaderText("Save First?");
			alert.setContentText("Do you want to save the existing project before starting a new project?");
			ButtonType save = new ButtonType("Save");
			ButtonType not = new ButtonType("Don't Save");
			alert.getButtonTypes().setAll(save, not);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == save){
				onSave();
			} else  {
			    System.out.println("No Save");
			} 
		}
		dataBase = new DataBase();
		newProject = true;
		beenSaved = false;
		
		if(dataBase != null) {
			newProjectLabel.setText("Active Project");
		}else
		{
			newProjectLabel.setText("No Active Project");
		}
		
		setConceptLabel.setText("Select a Concept First");
		
		projectNameField.clear();
		projectDescriptionField.clear();
		projectDescriptionTableLabel.setText("");
		projectNameTableLabel.setText("");
		//needWarningLabel.setText("");
		projectDetailName.setText("");
		projectDetailDescription.setText("");
		projectDetailGoals.setText("");
		projectDetailPrimaryMarket.setText("");		
		projectDetailSecondaryMarket.setText("");
		projectDetailAssumptions.setText("");
		projectDetailStakeholders.setText("");
		enterTextLabel1.setText("");
		enterTextLabel2.setText("");
		
		refreshTableView();
		refreshTableView2();
		refreshTableView3();
		refreshTableView4();
		saved = false;

	}
	
	public void onEnterProjectDescription(){   ////set the project name and project description
		if(newProject){
			
			
			projectName = projectNameField.getText();

			projectDescription = projectDescriptionField.getText();
			
			dataBase.setProjectName(projectName);
			dataBase.setProjectDescription(projectDescription);
			
			projectNameTableLabel.setText(dataBase.getProjectName());
			projectDescriptionTableLabel.setText(dataBase.getProjectDescription());
			
			projectDetailName.setText(dataBase.getProjectName());
			projectDetailDescription.setText(dataBase.getProjectDescription());
			
			enterTextLabel1.setText("");

			
			refreshTableView();
			refreshTableView2();
			refreshTableView3();
			refreshTableView4();
			
		} else {
						
			nullDatabaseWarning();
			
		}
				
	}//end of onCreateProjectButton
	
	public void nullDatabaseWarning(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("New Project Warning");
		alert.setContentText("You must start a New Project before entering data!");
		alert.showAndWait();
	}
	
	public void onEditProject(){
		if(newProject){
			showProjectFields();
		}
		else {
			nullDatabaseWarning();
		}
	}
	
	public void showProjectFields(){
		projectNameField.setText(dataBase.getProjectName());
		projectDescriptionField.setText(dataBase.getProjectDescription());
		projectDescriptionTableLabel.setText(dataBase.getProjectDescription());
		projectNameTableLabel.setText(dataBase.getProjectName());
		projectDetailName.setText(dataBase.getProjectName());
		projectDetailDescription.setText(dataBase.getProjectDescription());
		projectDetailGoals.setText(dataBase.getBusinessGoals());
		projectDetailPrimaryMarket.setText(dataBase.getPrimaryMarket());
		projectDetailSecondaryMarket.setText(dataBase.getSecondaryMarket());
		projectDetailAssumptions.setText(dataBase.getAssumptions());
		projectDetailStakeholders.setText(dataBase.getStakeholders());
	}
	
	
	////////////////////////////Team Member functions////////////////////////
	
	public void onMemberOkButton(){
		person = new Person(firstNameField.getText(), lastNameField.getText(), globalIdField.getText(),dutiesField.getText(), emailField.getText());
		
		if(newProject){
			dataBase.addPerson(person);
			//System.out.println(person.getFirstName() + " " + person.getLastName());
			tableView.setItems(dataBase.getPersons());
			
			saved = false;
			enterTextLabel2.setText("");
			firstNameField.setText("");
			lastNameField.setText("");
			globalIdField.setText("");
			emailField.setText("");
			dutiesField.setText("");

		}
		else{
			nullDatabaseWarning();
		}
	}
	
	public void onDeleteMember(){
		//System.out.println("Delete Student first");
		int index = tableView.getSelectionModel().getSelectedIndex();
		dataBase.getPersons().remove(index);
		refreshTableView();
		saved = false;

	}
	
	public void onEditMember(){
		//System.out.println("Edit Student");
		try{
		int index = tableView.getSelectionModel().getSelectedIndex();
		firstNameField.setText(dataBase.getPersons().get(index).getFirstName());
		lastNameField.setText(dataBase.getPersons().get(index).getLastName());
		globalIdField.setText(dataBase.getPersons().get(index).getGlobalid());
		emailField.setText(dataBase.getPersons().get(index).getEmail());
		dutiesField.setText(dataBase.getPersons().get(index).getDuties());
		
		dataBase.getPersons().remove(index);	
		
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		enterTextLabel2.setText("Don't Forget To Save Data");
		refreshTableView();
		
	}
	
	
	//////////////////start and stop functions///////////////////////
	
	public void setMain(Main main, Stage stage){
		this.main = main;
		this.primaryStage = stage;
		
	}
	
	public void onExit(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		alert.setTitle("Save Warning");
		alert.setHeaderText("Do you want to save your project?");
		alert.setContentText("Choose your option.");
		ButtonType buttonTypeOne = new ButtonType("Yes");
		ButtonType buttonTypeTwo = new ButtonType("No");
		//ButtonType buttonTypeCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		
		if(!saved){

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne){
				onSaveAs();
			} else if(result.get() == buttonTypeTwo) {
				primaryStage.close();
			}  
		}
		
		primaryStage.close();
		
	}
	
	////////////////Open and Close/////File operations///////////
	
	public void saveProject(){
		if(dataBase != null){

			try {
				if (beenSaved) dataBase.saveToFile(savedFile);
				else onSave();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				nullSaveWarning(); 
			}
		}
		else nullSaveWarning(); 
		
	}
	
	private void nullSaveWarning() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Save Error");
		alert.setHeaderText("Saving an empty project");
		alert.setContentText("Create a New Project before saving");
		alert.show();
	}
	
	public void onSave(){
		if(beenSaved){
			if(savedFile != null){
				try {
					dataBase.saveToFile(savedFile);
					saved = true;
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
					//e.printStackTrace();
				}
			}
		}
		else{
			onSaveAs();
		}
	}

	public void onSaveAs(){

		if(dataBase != null){
			

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Project Data");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PRJ", "*.prj"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
		savedFile = selectedFile;
		beenSaved = true;
		
		if(selectedFile != null){
				try {
					dataBase.saveToFile(selectedFile);
					saved = true;
				} catch (IOException e) {
					alert.setTitle("IO Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText(e.toString());
					alert.show();
					//e.printStackTrace();
				}
			}
		}
		else nullDatabaseWarning();
	
	}
	
	public void onOpenButton(){
		onNewProjectButton();	

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Student Data");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PRJ", "*.prj"));
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		savedFile = selectedFile;
		if(selectedFile != null){
			try {
				dataBase.loadFromFile(selectedFile);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		//setSpecNeedIndex();
		setConceptLabel.setText("Select a Concept First");


		newProject = true;
		beenSaved = true; 
		enterTextLabel1.setText("");
		enterTextLabel2.setText("");
		showProjectFields();
		refreshTableView();
		refreshTableView2();
		refreshTableView3();
		refreshTableView4();
		refreshTableView5();
		refreshTableView6();
		setSpecificationLabels();
		setCriteriaLabels();
	}
	
	//////////////////////Other functions/////////////////
	public void onTextEntered1(){
		textEntered = true;
		System.out.println("Text Entered"); 
		enterTextLabel1.setText("Don't Forget To Enter Data");
		//createProjectButton.
	}
	
	public void onTextEntered2(){
		textEntered = true;
		System.out.println("Text Entered");
		enterTextLabel2.setText("Don't Forget To Enter Data");
	}
	
	public void setEvaluationValues(){  //clears fields in the Concept Scoring tabs
		evaluationScore1.setText("");
		evaluationProduct1.setText("");
		evaluationScore2.setText("");
		evaluationProduct2.setText("");
		evaluationScore3.setText("");
		evaluationProduct3.setText("");
		evaluationScore4.setText("");
		evaluationProduct4.setText("");
		evaluationScore5.setText("");
		evaluationProduct5.setText("");
		evaluationScore6.setText("");
		evaluationProduct6.setText("");
		evaluationScore7.setText("");
		evaluationProduct7.setText("");
		evaluationScore8.setText("");
		evaluationProduct8.setText("");
		evaluationScore9.setText("");
		evaluationProduct9.setText("");
		evaluationScore10.setText("");
		evaluationProduct10.setText("");
		evaluationScore11.setText("");
		evaluationProduct11.setText("");
		evaluationScore12.setText("");
		evaluationProduct12.setText("");
		evaluationScore13.setText("");
		evaluationProduct13.setText("");
		evaluationScore14.setText("");
		evaluationProduct14.setText("");
		evaluationScore15.setText("");
		evaluationProduct15.setText("");
		evaluationScore16.setText("");
		evaluationProduct16.setText("");
		evaluationScore17.setText("");
		evaluationProduct17.setText("");
		evaluationScore18.setText("");
		evaluationProduct18.setText("");
		evaluationScore19.setText("");
		evaluationProduct19.setText("");
		evaluationScore20.setText("");
		evaluationProduct20.setText("");
		evaluationScore21.setText("");
		evaluationProduct21.setText("");
		evaluationScore22.setText("");
		evaluationProduct22.setText("");
		evaluationScore23.setText("");
		evaluationProduct23.setText("");
		evaluationScore24.setText("");
		evaluationProduct24.setText("");
		evaluationScore25.setText("");
		evaluationProduct25.setText("");
		
		criteriaScore1.setText("");
		criteriaProduct1.setText("");
		criteriaScore2.setText("");
		criteriaProduct2.setText("");
		criteriaScore3.setText("");
		criteriaProduct3.setText("");
		criteriaScore4.setText("");
		criteriaProduct4.setText("");
		criteriaScore5.setText("");
		criteriaProduct5.setText("");
		criteriaScore6.setText("");
		criteriaProduct6.setText("");
		criteriaScore7.setText("");
		criteriaProduct7.setText("");
		criteriaScore8.setText("");
		criteriaProduct8.setText("");
		criteriaScore9.setText("");
		criteriaProduct9.setText("");
		criteriaScore10.setText("");
		criteriaProduct10.setText("");
		criteriaScore11.setText("");
		criteriaProduct11.setText("");
		criteriaScore12.setText("");
		criteriaProduct12.setText("");
		criteriaScore13.setText("");
		criteriaProduct13.setText("");
		criteriaScore14.setText("");
		criteriaProduct14.setText("");
		criteriaScore15.setText("");
		criteriaProduct15.setText("");
		criteriaScore16.setText("");
		criteriaProduct16.setText("");
		criteriaScore17.setText("");
		criteriaProduct17.setText("");
		criteriaScore18.setText("");
		criteriaProduct18.setText("");
		criteriaScore19.setText("");
		criteriaProduct19.setText("");
		criteriaScore20.setText("");
		criteriaProduct20.setText("");
		criteriaScore21.setText("");
		criteriaProduct21.setText("");
		criteriaScore22.setText("");
		criteriaProduct22.setText("");
		criteriaScore23.setText("");
		criteriaProduct23.setText("");
		criteriaScore24.setText("");
		criteriaProduct24.setText("");
		criteriaScore25.setText("");
		criteriaProduct25.setText("");
		bmScore1.setText("");
		bmProduct1.setText("");
		bmScore2.setText("");
		bmProduct2.setText("");
		bmScore3.setText("");
		bmProduct3.setText("");
		bmScore4.setText("");
		bmProduct4.setText("");
		bmScore5.setText("");
		bmProduct5.setText("");
		bmScore6.setText("");
		bmProduct6.setText("");
		bmScore7.setText("");
		bmProduct7.setText("");
		bmScore8.setText("");
		bmProduct8.setText("");
		bmScore9.setText("");
		bmProduct9.setText("");
		bmScore10.setText("");
		bmProduct10.setText("");
		bmScore11.setText("");
		bmProduct11.setText("");
		bmScore12.setText("");
		bmProduct12.setText("");
		bmScore13.setText("");
		bmProduct13.setText("");
		bmScore14.setText("");
		bmProduct14.setText("");
		bmScore15.setText("");
		bmProduct15.setText("");
		bmScore16.setText("");
		bmProduct16.setText("");
		bmScore17.setText("");
		bmProduct17.setText("");
		bmScore18.setText("");
		bmProduct18.setText("");
		bmScore19.setText("");
		bmProduct19.setText("");
		bmScore20.setText("");
		bmProduct20.setText("");
		bmScore21.setText("");
		bmProduct21.setText("");
		bmScore22.setText("");
		bmProduct22.setText("");
		bmScore23.setText("");
		bmProduct23.setText("");
		bmScore24.setText("");
		bmProduct24.setText("");
		bmScore25.setText("");
		bmProduct25.setText("");
	}
	
	//////////////////////////////Table Refreshed//////////////
	
	public void refreshTableView(){
		 tableView.setItems(null);
		 tableView.layout();
		 tableView.setItems(dataBase.getPersons());
	 }
	
	public void refreshTableView2(){
		 tableViewNeeds.setItems(null);
		 tableViewNeeds.layout();
		tableViewNeeds.setItems(dataBase.getNeedList());
	 }
	
	public void refreshTableView3(){
		 tableViewNeeds2.setItems(null);
		 tableViewNeeds2.layout();
		 tableViewNeeds2.setItems(dataBase.getNeedList());
		 tableViewNeeds3.setItems(null);
		 tableViewNeeds3.layout();
		 tableViewNeeds3.setItems(dataBase.getNeedList());
	 }
	public void refreshTableView4(){
		specificationTableView.setItems(null);
		specificationTableView.layout();
		specificationTableView.setItems(dataBase.getSpecList());
		specificationTableView2.setItems(null);
		specificationTableView2.layout();
		specificationTableView2.setItems(dataBase.getSpecList());
		specificationTableView3.setItems(null);
		specificationTableView3.layout();
		specificationTableView3.setItems(dataBase.getSpecList());
		coaTableView.setItems(null);
		coaTableView.layout();
		coaTableView.setItems(dataBase.getSpecList());
		coaTableView1.setItems(null);
		coaTableView1.layout();
		coaTableView1.setItems(dataBase.getSpecList());

	 }
	
	public void refreshTableView4a(){
		specificationTableView2.setItems(null);
		specificationTableView2.layout();
		try{
		specificationTableView2.setItems(dataBase.getSpecList());
		}catch(NullPointerException e){
			
		}
	}
	
	public void refreshTableView5(){
		TableViewCoa.setItems(null);
		TableViewCoa.layout();
		TableViewCoa.setItems(dataBase.getCoaList());
		TableViewCoa1.setItems(null);
		TableViewCoa1.layout();
		TableViewCoa1.setItems(dataBase.getBmList());
	}
	
	public void refreshTableView6(){
		scoringTableView.setItems(null);
		scoringTableView.layout();
		scoringTableView.setItems(dataBase.getCoaList());
		scoringTableView1.setItems(null);
		scoringTableView1.layout();
		scoringTableView1.setItems(dataBase.getCoaList());
		scoringTableView2.setItems(null);
		scoringTableView2.layout();
		scoringTableView2.setItems(dataBase.getBmList());
		
	}
	
	
}//EndofClass	
