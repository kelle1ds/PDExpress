package print;

import java.io.File;
import java.io.FileWriter;

import javafx.collections.ObservableList;
import model.Needs;
import model.Person;
import model.Specification;
import java.io.IOException;
import java.io.Writer;



public class ExportProject {

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

 
	public ExportProject(File file, ObservableList<Needs> needList, ObservableList<Specification> specList, ObservableList<Person> personList, 
			String projectName, String projectDescription, String goals, String pMarket, String sMarket, String assume, String stake) {
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
		
		final String COMMA_DELIMITER = ",";
		final String NEW_LINE_SEPARATOR = "\n";
		
		FileWriter fileWriter = null;
		
		
		try {
			fileWriter = new FileWriter(file);
			fileWriter.append("Project Name");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(projectName);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("Project Description");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(projectDescription);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("Project Goals");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(businessGoals);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("Primary Market");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(primaryMarket);
			fileWriter.append(NEW_LINE_SEPARATOR);
			
		/////Customer Needs
			
			fileWriter.append("Need #");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Need Importance");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Customer Need");
			fileWriter.append(NEW_LINE_SEPARATOR);

			for(int i = 0; i < needList.size();i++){
				fileWriter.append(needList.get(i).getNumber().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(needList.get(i).getImportance().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(needList.get(i).getNeed());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
			fileWriter.append(NEW_LINE_SEPARATOR);
			
	///Specifications
			
			fileWriter.append("Spec #");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Need Importance");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Customer Need");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Unit");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Value");
			fileWriter.append(NEW_LINE_SEPARATOR);


			for(int j = 0; j < specList.size();j++){
				fileWriter.append(specList.get(j).getSpecNumber().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(specList.get(j).getImportance().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(specList.get(j).getMetric());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(specList.get(j).getUnit());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(specList.get(j).getValue());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}

		
		
	}
	
	
}
