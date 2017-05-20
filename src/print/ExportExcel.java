package print;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.ObservableList;
import model.Needs;
import model.Person;
import model.Specification;

public class ExportExcel {
	
	private File file;
	private String projectName;
	private String projectDescription;
	private String businessGoals;
	private String primaryMarket;
	private String secondaryMarket;
	private String assumptions;
	private String stakeholders;
	private ObservableList<Needs> needList;
	private ObservableList<Person> personList;
	private ObservableList<Specification> specList;
	private ArrayList<Integer> specNeeds;

	
	public ExportExcel(File file, ObservableList<Needs> needList, ObservableList<Specification> specList, 
			ObservableList<Person> personList, String projectName, String projectDescription, 
			String goals, String pMarket, String sMarket, String assume, String stake){
		
		this.needList = needList;
		this.personList = personList;
		this.specList = specList;
		this.projectDescription = projectDescription;
		this.projectName = projectName;
		this.file = file;
		this.businessGoals = goals;
		this.primaryMarket = pMarket;
		this.secondaryMarket = sMarket;
		this.assumptions = assume;
		this.stakeholders = stake;
		
	}
	
	public void export(){
		
		FileOutputStream  fip;
		try {
			fip = new FileOutputStream (file);
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			XSSFRow rowA1, rowA2, rowA3, rowA4, rowB1, rowB2, rowB3, 
			rowC1, rowC2, rowC3, rowD1, rowD2, rowD3,
			rowE1, rowE2, rowE3, rowE4;
			
			//////////////////PROJECT//////////////////

			XSSFSheet project = workbook.createSheet("Project");			
			
			project.setColumnWidth(0, 5000);
			project.setColumnWidth(1, 17000);
			
			CellStyle cs = workbook.createCellStyle();

			cs.setWrapText(true);
		    cs.setVerticalAlignment(VerticalAlignment.TOP);

			PropertyTemplate ptProject = new PropertyTemplate();
			ptProject.drawBorders(new CellRangeAddress(0,12,0,1), BorderStyle.MEDIUM, BorderExtent.ALL);

			rowA1 = project.createRow(0);	
			Cell projectCell = rowA1.createCell(0);
			projectCell.setCellValue("Project");
			Cell projectNameCell = rowA1.createCell(1);
			projectNameCell.setCellValue(projectName);

			rowA2 = project.createRow(2);
			
			Cell projectDescriptionCell = rowA2.createCell(0);
			projectDescriptionCell.setCellValue("Project Description");
			projectDescriptionCell.setCellStyle(cs);
			
			Cell projectDescriptionCell2 = rowA2.createCell(1);
			projectDescriptionCell2.setCellValue(projectDescription);
			projectDescriptionCell2.setCellStyle(cs);

			
			rowA2 = project.createRow(4);
			Cell projectGoals1 = rowA2.createCell(0);
			projectGoals1.setCellValue("Project Goals");

			Cell projectGoalsCell1 = rowA2.createCell(1);
			projectGoalsCell1.setCellValue(businessGoals);
			projectGoalsCell1.setCellStyle(cs);
			
			rowA2 = project.createRow(6);
			Cell primaryMarketCell1 = rowA2.createCell(0);
			primaryMarketCell1.setCellValue("Primary Market");
			primaryMarketCell1.setCellStyle(cs);

			Cell primaryMarketCell2 = rowA2.createCell(1);
			primaryMarketCell2.setCellValue(primaryMarket);
			primaryMarketCell2.setCellStyle(cs);
			
			rowA2 = project.createRow(8);
			Cell secondaryMarketCell1 = rowA2.createCell(0);
			secondaryMarketCell1.setCellValue("Secondary Market");
			secondaryMarketCell1.setCellStyle(cs);

			Cell secondaryMarketCell2 = rowA2.createCell(1);
			secondaryMarketCell2.setCellValue(secondaryMarket);
			secondaryMarketCell2.setCellStyle(cs);
			
			rowA2 = project.createRow(10);
			Cell assumptionsCell1 = rowA2.createCell(0);
			assumptionsCell1.setCellValue("Assumptions and Constraints");
			assumptionsCell1.setCellStyle(cs);

			Cell assumptionsCell2 = rowA2.createCell(1);
			assumptionsCell2.setCellValue(assumptions);
			assumptionsCell2.setCellStyle(cs);
			
			rowA2 = project.createRow(12);
			Cell stakeholdersCell1 = rowA2.createCell(0);
			stakeholdersCell1.setCellValue("Stakeholders");
			stakeholdersCell1.setCellStyle(cs);

			Cell stakeholdersCell2 = rowA2.createCell(1);
			stakeholdersCell2.setCellValue(stakeholders);
			stakeholdersCell2.setCellStyle(cs);
			
			ptProject.applyBorders(project);

			
			//////////team member list///////////////
			XSSFSheet members = workbook.createSheet("Team Members");
			
			PropertyTemplate ptTeam = new PropertyTemplate();
			ptTeam.drawBorders(new CellRangeAddress(0,1,0,3), BorderStyle.MEDIUM, BorderExtent.OUTSIDE);
			ptTeam.drawBorders(new CellRangeAddress(2,personList.size()+2,0,3), BorderStyle.MEDIUM, BorderExtent.ALL);

			rowB1 = members.createRow(0);//first row
			Cell teamMembers = rowB1.createCell(0);
			teamMembers.setCellValue("Team Members");
			
			rowB3 = members.createRow(2);
			
			Cell name = rowB3.createCell(0);
			name.setCellValue("Name");
			
			Cell email1 = rowB3.createCell(1);
			email1.setCellValue("Email");
			
			Cell globalID = rowB3.createCell(2);
			globalID.setCellValue("GlobalID");
			
			Cell duties1 = rowB3.createCell(3);
			duties1.setCellValue("Duties");
			
			for(int i = 0; i < personList.size();i++){

				rowB2 = members.createRow(3+i);
				
				Cell member = rowB2.createCell(0);
				member.setCellValue(personList.get(i).getFirstName() + " " + personList.get(i).getLastName());
				
				Cell email = rowB2.createCell(1);
				email.setCellValue(personList.get(i).getEmail());
				
				Cell id = rowB2.createCell(2);
				id.setCellValue(personList.get(i).getGlobalid());
				
				Cell duties = rowB2.createCell(3);
				duties.setCellValue(personList.get(i).getDuties());
				
				members.setColumnWidth(0, 7000);
				members.setColumnWidth(1, 7000);
				members.setColumnWidth(2, 3000);
				members.setColumnWidth(3, 14000);
			}
			
			ptTeam.applyBorders(members);
			
			//////////Customer needs///////////////
			XSSFSheet needs = workbook.createSheet("Customer Needs");
			
			PropertyTemplate ptNeeds = new PropertyTemplate();

			ptNeeds.drawBorders(new CellRangeAddress(0,1,0,2), BorderStyle.MEDIUM, BorderExtent.OUTSIDE);
			ptNeeds.drawBorders(new CellRangeAddress(2,needList.size()+2,0,2), BorderStyle.MEDIUM, BorderExtent.ALL);

			
			needs.setColumnWidth(0, 2000);
			needs.setColumnWidth(1, 3000);
			needs.setColumnWidth(2, 18000);

				
			rowC1 = needs.createRow(0);
			Cell customerNeeds = rowC1.createCell(0);
				
			customerNeeds.setCellValue("Customer Needs");
				
			rowC3 = needs.createRow(2);
			Cell needNum = rowC3.createCell(0);
			needNum.setCellValue("Number");
				
			Cell needImport = rowC3.createCell(1);
			needImport.setCellValue("Importance");
				
			Cell needStatement = rowC3.createCell(2);
			needStatement.setCellValue("Need");
					
			for(int j = 0; j < needList.size();j++){
				rowC2 = needs.createRow(3+j);  //row for need
					
				Cell num = rowC2.createCell(0);  //first column of row
				num.setCellValue(needList.get(j).getNumber());
					
				Cell importance = rowC2.createCell(1);
				importance.setCellValue(needList.get(j).getImportance());
					
				Cell need = rowC2.createCell(2);
				need.setCellValue(needList.get(j).getNeed());
					
			}
			
			ptNeeds.applyBorders(needs);

				
		//////////Specification///////////////
				XSSFSheet specifications = workbook.createSheet("Specifications");
				
				PropertyTemplate ptSpec = new PropertyTemplate();
				ptSpec.drawBorders(new CellRangeAddress(0,1,0,4), BorderStyle.MEDIUM, BorderExtent.OUTSIDE);
				ptSpec.drawBorders(new CellRangeAddress(2,specList.size()+2,0,4), BorderStyle.MEDIUM, BorderExtent.ALL);

				rowD1 = specifications.createRow(0);
				Cell specs = rowD1.createCell(0);
				specs.setCellValue("Engineering Specifications");
				
				specifications.setColumnWidth(1, 2000);
				specifications.setColumnWidth(2, 10000);
				specifications.setColumnWidth(3, 2000);
				specifications.setColumnWidth(4, 3000);

				rowD3 = specifications.createRow(2);//row three
				
				Cell specNum = rowD3.createCell(0);
				specNum.setCellValue("Num");
				specifications.setColumnWidth(0, 1500);

				
				Cell specImport = rowD3.createCell(1);
				specImport.setCellValue("Importance");
		
				Cell specMetric = rowD3.createCell(2);
				specImport.setCellValue("Metric");

				Cell specUnit = rowD3.createCell(3);
				specUnit.setCellValue("Unit");
				
				Cell specValue = rowD3.createCell(4);
				specValue.setCellValue("Unit");
				
				for(int j = 0; j < specList.size();j++){
					rowD2 = specifications.createRow(3+j);  //row for need
							
					Cell num = rowD2.createCell(0);  //first column of row
					num.setCellValue(specList.get(j).getSpecNumber());
							
					Cell importance = rowD2.createCell(1);
					importance.setCellValue(specList.get(j).getImportance());
							
					Cell metric = rowD2.createCell(2);
					metric.setCellValue(specList.get(j).getMetric());
							
					Cell unit = rowD2.createCell(3);
					unit.setCellValue(specList.get(j).getUnit());
					
					Cell value = rowD2.createCell(4);
					value.setCellValue(specList.get(j).getValue());
							
					}
				
				ptSpec.applyBorders(specifications);
				
		////////////////NEED-SPECIFICATION MATRIX/////////////////////
				XSSFSheet needSpec = workbook.createSheet("Need-Spec Matrix");
				
				PropertyTemplate ptNeedSpec = new PropertyTemplate();
				ptNeedSpec.drawBorders(new CellRangeAddress(0,1,0,needList.size()+1), BorderStyle.MEDIUM, BorderExtent.OUTSIDE);
				ptNeedSpec.drawBorders(new CellRangeAddress(1,2,0,needList.size()+1), BorderStyle.MEDIUM, BorderExtent.OUTSIDE);
				ptNeedSpec.drawBorders(new CellRangeAddress(1,2,2,needList.size()+1), BorderStyle.MEDIUM, BorderExtent.ALL);

				ptNeedSpec.drawBorders(new CellRangeAddress(2,specList.size()+2,0,needList.size()+1), BorderStyle.MEDIUM, BorderExtent.ALL);
				
				rowE1 = needSpec.createRow(0);
				Cell needSpecCell1 = rowE1.createCell(0);
				needSpecCell1.setCellValue("Needs Specifications Matrix");
				
				needSpec.setColumnWidth(0, 8000);
				needSpec.setColumnWidth(1, 2000);
				
				rowE2 = needSpec.createRow(1);
				rowE2.setHeight((short) 3000);
				
				CellStyle cs2 = workbook.createCellStyle();

				cs2.setWrapText(true);
			    cs2.setRotation((short) 90);
			    cs2.setAlignment(HorizontalAlignment.CENTER);
			    cs2.setVerticalAlignment(VerticalAlignment.BOTTOM);
			   
				for (int i = 0; i < needList.size(); i++)  {  //first row of table
					
					Cell needSpecCell2 = rowE2.createCell(i+2);
					needSpecCell2.setCellStyle(cs2);
					needSpecCell2.setCellValue(needList.get(i).getNeed());
				}
				
				rowE4 = needSpec.createRow(2);
				CellStyle cs4 = workbook.createCellStyle();
				cs4.setAlignment(HorizontalAlignment.CENTER);
				cs4.setVerticalAlignment(VerticalAlignment.BOTTOM);
				
				for (int i = 0; i < needList.size(); i++)  {  //first row of table
					
					Cell needSpecCell2a = rowE4.createCell(i+2);
					needSpecCell2a.setCellStyle(cs4);
					needSpecCell2a.setCellValue(needList.get(i).getImportance());
				}
			
				for(int i = 0; i < specList.size();i++){ //start of left column and second row
					
					rowE3 = needSpec.createRow(3+i);
					//rowE2.setHeight((short) 3000);
					
					Cell needSpecCell3 = rowE3.createCell(0);
					needSpecCell3.setCellValue(specList.get(i).getMetric());

			    	specNeeds = specList.get(i).getNeeds();
			    	
			    	CellStyle cs3 = workbook.createCellStyle();

			    	cs3.setAlignment(HorizontalAlignment.CENTER);
			    	cs3.setVerticalAlignment(VerticalAlignment.CENTER);
			    	
			    	Cell needSpecCell5 = rowE3.createCell(1);
			    	needSpecCell5.setCellStyle(cs3);
					needSpecCell5.setCellValue(specList.get(i).getImportance());
			    	
			    	
			    	for(int j = 0; j < needList.size();j++){

			    		if(specNeeds.get(j).intValue()>0){
			    			//fileWriter.append("X");
			    			Cell needSpecCell4 = rowE3.createCell(j+2);
			    			needSpecCell4.setCellStyle(cs3);
			    			
			    			needSpecCell4.setCellValue("X");

			    		}
			    		else{
			    			Cell needSpecCell4 = rowE3.createCell(j+2);

			    			needSpecCell4.setCellValue("");
			    		}
			    		
			    	}
			    	
			    	ptNeedSpec.applyBorders(needSpec);

				}

			/////////////////////////////////////////////////
				
			workbook.write(fip);
			fip.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
	}//end of method
	
	

}//end of class
