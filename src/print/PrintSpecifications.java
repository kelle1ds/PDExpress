package print;

import java.io.File;
import java.io.IOException;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javafx.collections.ObservableList;
import model.Specification;

public class PrintSpecifications {

	private String dest;
	private File file;
	private Cell cell;
	private Document doc;
	private  PdfPage page;
	private ObservableList<Specification> specList;
	
	public PrintSpecifications(String dest, ObservableList<Specification> specList) {
		this.specList = specList;
		this.dest = dest;
	}
	
	public PrintSpecifications(File file, ObservableList<Specification> specList) {
		this.specList = specList;
		this.file = file;
	}
	
	public void printFile() throws IOException{
		//Initialize PDF writer
        PdfWriter writer = new PdfWriter(file.getPath());
        
        PageSize ps = PageSize.LETTER.rotate();
 
        PdfDocument pdf = new PdfDocument(writer);
        
        Document doc = new Document(pdf,ps);
        
       // PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        //PdfFont font2 = PdfFontFactory.createFont(FontConstants.);

        
        //doc.setFont(font);
        
        Paragraph spec = new Paragraph("Specifications");
        spec.setTextAlignment(TextAlignment.CENTER);
        spec.setFontSize(18);
        
        doc.add(spec);  //first row of table

        Table table = new Table(new float[]{2,2,8,2,2}); //table with five columns
        table.setWidthPercent(100);
       // table.setFont(font2);
        table.setFontSize(12);
        table.setMarginTop(20);
        table.setMarginLeft(20);
        table.setMarginRight(20);
        table.addCell("Number");
        table.addCell("Importance");
        table.addCell("Metric");
        table.addCell("Value");
        table.addCell("Unit");

        for (int i = 0; i < specList.size(); i++)  {
        	
            table.addCell(Integer.toString(specList.get(i).getSpecNumber()));
            table.addCell(Integer.toString(specList.get(i).getImportance()));
            table.addCell(specList.get(i).getMetric());
            table.addCell(specList.get(i).getValue());
            table.addCell(specList.get(i).getUnit());
     
        }
        doc.add(table);
        
        doc.add(new AreaBreak());
        
        //PageSize ps2 = PageSize.LETTER.rotate();
        //PdfPage page2 = pdf.addNewPage(ps2);
        
        Paragraph spec2 = new Paragraph("Specifications Testing");
        spec2.setTextAlignment(TextAlignment.CENTER);
        spec2.setFontSize(18);
        
        doc.add(spec2);  //first row of table
    	
        for (int i = 0; i < specList.size(); i++)  {
        	
        	Table table2;
        	
        	table2 = new Table(new float[]{8});
        	table2.setWidthPercent(100);
            table2.setMarginTop(20);
            table2.setMarginLeft(20);
            table2.setMarginRight(20);
            table2.setFontSize(12);
            
        	table2.addCell(specList.get(i).getMetric());
        	
        	table2.addCell(specList.get(i).getEvaluation());

        	doc.add(table2);
        }
        
        doc.close();
	}
	
}
