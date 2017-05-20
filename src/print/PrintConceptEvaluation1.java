package print;

import java.io.File;
import java.io.IOException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CourseOfAction;
import model.DesignSelection;
import model.Needs;
import model.Specification;

public class PrintConceptEvaluation1 {
	private ObservableList<CourseOfAction> coaList;
	private ObservableList<Needs> needList;
	private ObservableList<DesignSelection> selection;
	private File file;
	private int coa;
	private String coaName;
	private Integer specNumber;
	private String metric;
	private String unit;
	private Integer importance;
	private String value;
	private String evaluation;
	
	 
	public PrintConceptEvaluation1(File file, ObservableList<CourseOfAction> coaList, ObservableList<Needs> needList) 
	{
		this.coaList = coaList;
		this.needList = needList;
		this.file = file;
	}
	
	
	public void printFile() throws IOException{
		//Initialize PDF writer
        PdfWriter writer = new PdfWriter(file.getPath());
        
        PageSize ps = PageSize.LETTER.rotate();
 
        PdfDocument pdf = new PdfDocument(writer);
        
        Document doc = new Document(pdf,ps);
        Paragraph details = new Paragraph("Concept Scoring Matrices");
        details.setTextAlignment(TextAlignment.CENTER);
        details.setFontSize(20);
        doc.add(details);

        
        
        for (int i = 0; i < coaList.size(); i++)  {
  
        	//table.addCell(coaList.get(i).getCoaName());
        	Table table = new Table(new float[]{8,3,3,3});
        	 table.setWidthPercent(100);
             table.setFontSize(14);
             table.setMarginTop(20);
             table.setMarginLeft(30);
             table.setMarginRight(30);
			String coa = coaList.get(i).getCoaName();
        	table.addCell(new Cell(1, 4).add("Design Concept: " + coa));
        	
        	table.addCell("Customer Need");
            table.addCell("Importance");
            table.addCell("Evaluation");
            table.addCell("Product");
            
        	for(int j = 0; j < needList.size();j++){
                	//table.addCell(specList.get(j).getMetric());
        		try{
                	table.addCell(needList.get(j).getNeed());
                	table.addCell(Integer.toString(needList.get(j).getImportance()));
                	table.addCell(Integer.toString(coaList.get(i).getCriteriaSelection().get(j).getEvaluationScore()));
                	table.addCell(Integer.toString(coaList.get(i).getCriteriaSelection().get(j).getProduct()));
        		}catch(IndexOutOfBoundsException e) {
        			Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Out of Bounds Exception");
					alert.setHeaderText("IO Exception during Save");
					alert.setContentText("Rescore Concept " + coaList.get(i).getCoaName());
					alert.show();
					//e.printStackTrace();
        		
        		}
        	}
        	table.addCell(" ");
        	table.addCell(" ");
        	table.addCell("Total Score: ");
        	table.addCell(Integer.toString(coaList.get(i).getCriteriaEvaluation()));
        	table.setKeepTogether(true);
        	doc.add(table);

        }
       
    	//doc.add(table);
       
        doc.close();
        
	}

}
