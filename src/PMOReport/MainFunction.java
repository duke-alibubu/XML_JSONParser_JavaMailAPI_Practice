package PMOReport;


public class MainFunction {
	public static void main(String[] args) {
		
		while(true) {
			XMLReader central = new XMLReader("dengue-cases-central-kml.kml");
			XMLReader ne = new XMLReader("dengue-cases-north-east-kml.kml");
			XMLReader nw = new XMLReader("dengue-cases-north-west-kml.kml");
			XMLReader se = new XMLReader("dengue-cases-south-east-kml.kml");
			XMLReader sw = new XMLReader("dengue-cases-south-west-kml.kml");
			JSONReader jsonReader = new JSONReader();
			EmailSender emailSender = new EmailSender();
			String toSent = "";
			try {		
				central.run();
				ne.run();
				nw.run();
				se.run();
				sw.run();
				int total = central.getCasecount()+ ne.getCasecount() + nw.getCasecount() + se.getCasecount()+sw.getCasecount();
				toSent += "DENGUE \n";
				toSent += "Number of dengue cases: "+ total + "\n";
				toSent += "\n";
				toSent += "Locations of the cases: \n";
				
				toSent += central.getCases();
				toSent += ne.getCases();
				toSent += nw.getCases();
				toSent += se.getCases();
				toSent += sw.getCases();
			
				toSent += "\n";
				toSent += "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
				toSent += "\n";
				toSent += "POLLUTANT STANDARDS INDEX \n \n";
		
				toSent += jsonReader.process("https://api.data.gov.sg/v1/environment/psi");
				
			}
			catch (Exception e){
				//do nothing
				System.out.println(e.getClass() + " occurred!");
			}
			
			emailSender.sendEmail("examplepmo@gmail.com", toSent);
			
			try {
				Thread.sleep(30*60*1000); //repeat once every 30 mins
			}
			catch (Exception e) {
				//do nothing
			}
		//examplepmo@gmail.com
		//shireReceive123
		
		}
		
	}
	
	
}
