package PMOReport;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;



public class XMLReader {
	
	private int casecount;
	private File xmlFile;
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private String cases;
	

	public XMLReader(String filename) {
		casecount = 0;
		// TODO Auto-generated method stub
		xmlFile = new File(filename);	
		cases = "";
	}
	
	public void run() throws Exception {
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.parse(xmlFile);
		
		NodeList list = doc.getElementsByTagName("Placemark");
		for (int i = 0; i<list.getLength();i++) {
			Node node = list.item(i);
			casecount++;
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)node;

				Node polygonNode = element.getElementsByTagName("Polygon").item(0);
				Element polygon = (Element)polygonNode;
				
				Node outerBoundaryIs = polygon.getElementsByTagName("outerBoundaryIs").item(0);
				Element outerBoundary = (Element)outerBoundaryIs;
				
				cases += outerBoundary.getElementsByTagName("LinearRing").item(0).getTextContent();
			}
		}
	}
	public String getCases() {
		return this.cases;
	}
	public int getCasecount() {
		return this.casecount;
	}

}
