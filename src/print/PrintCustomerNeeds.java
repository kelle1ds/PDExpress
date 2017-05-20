package print;

import java.io.File;
import java.io.IOException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javafx.collections.ObservableList;
import model.Needs;
import model.Person;

public class PrintCustomerNeeds {
	private File file;
	private ObservableList<Needs> needList; 
	
	public PrintCustomerNeeds(File file, ObservableList<Needs> needList) {
		
		this.needList = needList;
		
		this.file = file;
	}
	
	public void printFile() throws IOException{
		//Initialize PDF writer
        PdfWriter writer = new PdfWriter(file.getPath());
        
        PageSize ps = PageSize.LETTER;
 
        PdfDocument pdf = new PdfDocument(writer);
        
        Document doc = new Document(pdf,ps);
        
        Paragraph details = new Paragraph("Customer Needs");
        details.setTextAlignment(TextAlignment.CENTER);
        details.setFontSize(18);
        
        doc.add(details);  //first row of table
        
        
        Table table = new Table(new float[]{3,3,12}); //table with four columns
        table.setWidthPercent(100);
        table.setFontSize(12);
        table.setMarginTop(20);
        table.setMarginLeft(20);
        table.setMarginRight(20);
        table.addCell("Number");
        table.addCell("Importance");
        table.addCell("Customer Need");
       
        
        for (int i = 0; i < needList.size(); i++)  {

        	table.addCell(Integer.toString(needList.get(i).getNumber()));
        	table.addCell(Integer.toString(needList.get(i).getImportance()));
        	table.addCell(needList.get(i).getNeed());
        }

        doc.add(table);
   
        doc.close();
	}
}
