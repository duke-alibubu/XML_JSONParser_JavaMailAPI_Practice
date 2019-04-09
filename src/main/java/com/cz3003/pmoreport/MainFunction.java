package com.cz3003.pmoreport;

import org.bson.Document;
import java.lang.StringBuilder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class MainFunction {

	public static void main(String[] args) {

		ReportDb reportDb = new ReportDb();
		DengueDb dengueDb = new DengueDb();
		
		while(true) {
			// XMLReader central = new XMLReader("dengue-cases-central-kml.kml");
			// XMLReader ne = new XMLReader("dengue-cases-north-east-kml.kml");
			// XMLReader nw = new XMLReader("dengue-cases-north-west-kml.kml");
			// XMLReader se = new XMLReader("dengue-cases-south-east-kml.kml");
			// XMLReader sw = new XMLReader("dengue-cases-south-west-kml.kml");
			JSONReader jsonReader = new JSONReader();
			EmailSender emailSender = new EmailSender();
			List<Document> reports = reportDb.getReportsPast30Min();

			StringBuilder toSent = new StringBuilder();
			try {		
				// central.run();
				// ne.run();
				// nw.run();
				// se.run();
				// sw.run();
				// int total = central.getCasecount()+ ne.getCasecount() + nw.getCasecount() + se.getCasecount()+sw.getCasecount();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
				String date = sdf.format(new Date(System.currentTimeMillis()));
				toSent.append("PRIME MINISTER OFFICE REPORT FOR " + date + "hrs\n\n\n\n");


				List<Document> dengueCases = dengueDb.getDengueCases();
				Stream<DengueDocument> dengueCasesStream = dengueCases.stream().map(DengueDocument::from).distinct().sorted(Comparator.comparingInt(DengueDocument::getSize));
				toSent.append("DENGUE REPORT\n");
				toSent.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
				toSent.append("Number of dengue cases: " + dengueCasesStream.map(DengueDocument::getSize).reduce(Integer::sum).get().toString() + "\n\n");
				dengueCasesStream.forEach(d -> toSent.append(d.getSize() + " cases at " + d.getLocation() + "\n"));
				toSent.append("\n");

				// toSent.append("Central: " + central.getCasecount() + "\n");
				// toSent.append("North-east: " + ne.getCasecount() + "\n");
				// toSent.append("South-east: " + se.getCasecount() + "\n");
				// toSent.append("North-west: " + nw.getCasecount() + "\n");
				// toSent.append("South-west: " + sw.getCasecount() + "\n");
				// toSent.append("\n");
				// toSent.append("Locations of the cases: \n");
				//
				// toSent.append(central.getCases());
				// toSent.append(ne.getCases());
				// toSent.append(nw.getCases());
				// toSent.append(se.getCases());
				// toSent.append(sw.getCases());
			
				toSent.append("\n");
				toSent.append("POLLUTANT STANDARDS INDEX \n");
				toSent.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				toSent.append("\n");

				toSent.append(jsonReader.process("https://api.data.gov.sg/v1/environment/psi"));

				toSent.append("\n\n");

				toSent.append("INCIDENTS REPORTED \n");
				toSent.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				toSent.append("\n");
				if (reports.size() == 0) {
					toSent.append("No incidents reported in the past 30 minutes.\n");
				}
				for (Document report: reports) {
					toSent.append("\n");
					toSent.append("Status: " + report.getString("status") + "\n");
					toSent.append("Location: " + report.getString("address") + "\n");
					toSent.append("Details: " + report.getString("details") + "\n");
					toSent.append("Handled by: " + report.getString("agency") + "\n");
					toSent.append("Last updated: " + report.getDate("time_last_updated") + "\n\n");
				}
				
			}
			catch (Exception e){
				//do nothing
				System.out.println(e.getClass() + " occurred!");
			}
			System.out.println(toSent.toString());
			
			emailSender.sendEmail("examplepmo@gmail.com", toSent.toString());
			
			try {
				Thread.sleep(2*60*1000); //repeat once every 2 mins
			}
			catch (Exception e) {
				//do nothing
			}
		//examplepmo@gmail.com
		//shireReceive123
		}
		
	}

}
