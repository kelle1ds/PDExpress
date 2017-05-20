package print;

import java.io.File;
import java.io.IOException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javafx.collections.ObservableList;
import model.Needs;
import model.Person;
import model.Specification;

public class PrintProject {

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
 
	
	public PrintProject(File file, ObservableList<Needs> needList, ObservableList<Person> personList, 
			String projectName, String projectDescription, String goals, String pMarket, String sMarket, String assume, String stake) {
		this.needList = needList;
		this.personList = personList;
		this.projectDescription = projectDescription;
		this.projectName = projectName;
		this.file = file;
		this.businessGoals = goals;
		this.primaryMarket = pMarket;
		this.secondaryMarket = sMarket;
		this.assumptions = assume;
		this.stakeholders = stake;
	}
	
	public void printFile() throws IOException{
		//Initialize PDF writer
        PdfWriter writer = new PdfWriter(file.getPath());
        
        PageSize ps = PageSize.LETTER.rotate();
 
        PdfDocument pdf = new PdfDocument(writer);
        
        Document doc = new Document(pdf,ps);
        
        Paragraph details = new Paragraph("Project Details");
        details.setTextAlignment(TextAlignment.CENTER);
        details.setFontSize(18);
        
        doc.add(details);  //first row of table
        
        Table table1 = new Table(new float[]{5,15});
        table1.setWidthPercent(100);
        table1.setFontSize(12);
        table1.setMarginTop(20);
        table1.setMarginLeft(20);
        table1.setMarginRight(20);
    	table1.addCell(new Cell(1, 4).add("Mission Statement ").setTextAlignment(TextAlignment.CENTER));

        table1.addCell("Project Name");
        table1.addCell(projectName);
        table1.addCell("Project Description");
        table1.addCell(projectDescription);
        table1.addCell("Key Business Goals");
        table1.addCell(businessGoals);
        table1.addCell("Primary Market");
        table1.addCell(primaryMarket);
        table1.addCell("Secondary Market");
        table1.addCell(secondaryMarket);
        table1.addCell("Assumptions and Constraints");
        table1.addCell(assumptions);
        table1.addCell("Stakeholders");
        table1.addCell(stakeholders);
        doc.add(table1);
        
        doc.add(new AreaBreak());
        
        Table table = new Table(new float[]{9,6,8,10}); //table with four columns
        table.setWidthPercent(100);
        table.setFontSize(14);
        table.setMarginTop(20);
        table.setMarginLeft(20);
        table.setMarginRight(20);
        table.addCell(new Cell(1, 4).add("Team Members"));
        table.addCell("Name");
        table.addCell("GlobalID");
        table.addCell("Email");
        table.addCell("Duties");
        
        for (int i = 0; i < personList.size(); i++)  {

        	table.addCell(personList.get(i).getFirstName() + " " + personList.get(i).getLastName());
        	table.addCell(personList.get(i).getGlobalid());
        	table.addCell(personList.get(i).getEmail());
        	table.addCell(personList.get(i).getDuties());
        	
        }

        doc.add(table);
   
        doc.close();
	}
	
}
