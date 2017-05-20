package print;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.GroupLayout.Alignment;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import javafx.collections.ObservableList;
import model.CourseOfAction;
import model.Needs;
import model.Specification;

public class PrintNeedSpecMatrix {
	private String dest;
	private File file;
	private Cell cell;
	private Document doc;
	private  PdfPage page;
	private ObservableList<Specification> specList;
	private ObservableList<Needs> needList;
	private ArrayList<Integer> specNeeds;
	private int size;
	
	
public PrintNeedSpecMatrix(File file, ObservableList<Specification> specList, ObservableList<Needs> needList) {
		this.specList = specList;
		this.file = file;
		this.needList = needList;
	}
	
public void printFile() throws IOException{
	
	
	//Initialize PDF writer
    PdfWriter writer = new PdfWriter(file.getPath());
    
    PageSize ps = PageSize.TABLOID.rotate();

    PdfDocument pdf = new PdfDocument(writer);
    
    Document doc = new Document(pdf,ps);
    
   // PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
    //PdfFont font2 = PdfFontFactory.createFont(FontConstants.);

    
    //doc.setFont(font);
    
    Paragraph spec = new Paragraph("Needs-Specifications Matrix");
    spec.setTextAlignment(TextAlignment.CENTER);
    spec.setFontSize(18);
    
    doc.add(spec);  //
    
    int width = 400;

    Table table = new Table(needList.size()+1); 
    table.setWidthPercent(100);
    table.setFontSize(10);
    table.setMarginTop(20);
    table.setMarginLeft(20);
    Cell cell1 = new Cell();
    cell1.add("");
    cell1.setWidth(width);
    table.setMarginRight(20);        
   
	table.addCell(cell1);//first cell, first row of table

    for (int i = 0; i < needList.size(); i++)  {  //first row of table
    	Cell cell = new Cell();
    	cell.setFontSize(10);
    	cell.add(needList.get(i).getNeed());
    	table.addCell(cell);
    }
    
    
    for(int i = 0; i < specList.size();i++){ //start of left column and second row
    	
    	table.addCell(specList.get(i).getMetric()); //Specification from speclist
    	System.out.println("Spec number is " + specList.get(i).getSpecNumber());
    	specNeeds = specList.get(i).getNeeds();
    	System.out.println("Shown need size: " + specList.get(i).getNeedsShown().size());
    	
    	for(int j = 0; j < needList.size();j++){
    		System.out.println("Need: " + needList.get(j).getNeed());
    		int ans=0;
    		
    		Cell cell = new Cell();
    		cell.setTextAlignment(TextAlignment.CENTER);
    		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
    		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
    		cell.setFontSize(14);
    		
    		if(specNeeds.get(j).intValue()>0){
    			cell.add("X");
    			table.addCell(cell);
    		}
    		else{
    			table.addCell("");
    		}
    		
    	}
    }
    
    doc.add(table);

    doc.close();
}
}



