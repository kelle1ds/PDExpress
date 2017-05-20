package print;

import java.io.File;
import java.io.FileWriter;

import javafx.collections.ObservableList;
import model.Needs;
import model.Person;
import model.Specification;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;



public class ExportNeedSpec {

	private File file;
	private ObservableList<Needs> needList;
	private ObservableList<Specification> specList;
	private ArrayList<Integer> specNeeds;

 
	public ExportNeedSpec(File file, ObservableList<Needs> needList, ObservableList<Specification> specList) {
		this.needList = needList;
		this.specList = specList;
		this.file = file;
		
	}
	
	public void export(){
		
		final String COMMA_DELIMITER = ",";
		final String NEW_LINE_SEPARATOR = "\n";
		
		FileWriter fileWriter = null;
		
		
		try {
	
			fileWriter = new FileWriter(file);
			
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			
			for (int i = 0; i < needList.size(); i++)  {  //first row of table
					
					fileWriter.append(needList.get(i).getNeed());
					fileWriter.append(COMMA_DELIMITER);
			}
			    
			fileWriter.append(NEW_LINE_SEPARATOR);

			for(int i = 0; i < specList.size();i++){ //start of left column and second row
			    	
			    	fileWriter.append(specList.get(i).getMetric()); //Specification from speclist
					fileWriter.append(COMMA_DELIMITER);

			    	specNeeds = specList.get(i).getNeeds();
			    	
			    	for(int j = 0; j < needList.size();j++){
			    		
			    		if(specNeeds.get(j).intValue()>0){
			    			fileWriter.append("X");
							fileWriter.append(COMMA_DELIMITER);

			    			//fileWriter.append("");
			    		}
			    		else{
			    			fileWriter.append("");
							fileWriter.append(COMMA_DELIMITER);
			    		}
			    		
			    	}
			    	
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
