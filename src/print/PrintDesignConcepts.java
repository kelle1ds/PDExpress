package print;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javafx.collections.ObservableList;
import model.CourseOfAction;

public class PrintDesignConcepts {
	
	private ObservableList<CourseOfAction> coaList;
	private File file;
	private int coa;
	private String coaName;
	private String description;
	private String strengths;
	private String weakness;
	 
	public PrintDesignConcepts(File file,ObservableList<CourseOfAction> coaList){
		this.coaList = coaList;
		this.file = file;
	}
	
	
	public void printFile() throws IOException{
		//Initialize PDF writer
        PdfWriter writer = new PdfWriter(file.getPath());
        
        PageSize ps = PageSize.LETTER.rotate();
 
        PdfDocument pdf = new PdfDocument(writer);
        
        Document doc = new Document(pdf,ps);
        
        Paragraph details = new Paragraph("Design Concepts");
        details.setTextAlignment(TextAlignment.CENTER);
        details.setFontSize(24);
        
        doc.add(details);
        
        Table table = new Table(new float[]{6,5,14}); //table with five columns
        table.setWidthPercent(100);
        table.setFontSize(14);
        table.setMarginTop(20);
        table.setMarginLeft(20);
        table.setMarginRight(20);
        table.addCell("Concept Number");
        table.addCell("Concept Name");
        table.addCell("Description");
        
        
        for (int i = 0; i < coaList.size(); i++)  {

        	table.addCell("Concept Number: " + " " + Integer.toString(coaList.get(i).getCoa()));
        	table.addCell(coaList.get(i).getCoaName());
        	table.addCell(coaList.get(i).getDescription());
        }
        doc.add(table);
        
                
        for (int i = 0; i < coaList.size(); i++){
        	Table table1 = new Table(new float[]{5, 12}); //table with five columns
            table1.setWidthPercent(100);
            table1.setFontSize(14);
            table1.setMarginTop(20);
            table1.setMarginLeft(20);
            table1.setMarginRight(20);
            
        	table1.addCell(new Cell(1, 4).add("Design Concept: " + coaList.get(i).getCoaName()));

        	table1.addCell("Stengths for " + coaList.get(i).getCoaName() + ": ");
        	table1.addCell(coaList.get(i).getStrengths());
        	table1.addCell("Weaknesses for " + coaList.get(i).getCoaName() + ": ");
        	table1.addCell(coaList.get(i).getWeakness());
        	table1.setKeepTogether(true);

        	doc.add(table1);

        }
        doc.close();
        
	}
}
