package com.simulationFrameworkDT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.simulationFrameworkDT.dataSource.DataSourceSystem;
import com.simulationFrameworkDT.model.factorySITM.SITMCalendar;
import com.simulationFrameworkDT.model.factorySITM.SITMLine;
import com.simulationFrameworkDT.model.factorySITM.SITMPlanVersion;
import com.simulationFrameworkDT.model.factorySITM.SITMStop;
import com.simulationFrameworkDT.simulation.SimController;
import com.simulationFrameworkDT.simulation.event.eventProccessor.EventProcessorController;
import com.simulationFrameworkDT.simulation.event.eventProvider.EventProviderController;
import com.simulationFrameworkDT.simulation.state.Project;
import com.simulationFrameworkDT.simulation.state.StateController;
import com.simulationFrameworkDT.analytics.*;

public class SimulationMain {

	public static void main(String[] args) throws IOException, ParseException {
		
//		dataTest();
//		visualizationTest();
		simulationTest();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		reader.readLine();
	}
	
	
	public static void dataTest(){
		
		DataSourceSystem ds = new DataSourceSystem();
		ds.initializeCsv();
		
		System.out.println("plan Versions ========================================================================================================================================\n");
		ArrayList<SITMPlanVersion> planversions = ds.findAllPlanVersions(DataSourceSystem.FILE_CSV);
		for (int i = 0; i < planversions.size(); i++) {System.out.println(planversions.get(i));}
		System.out.println();
		
		
		System.out.println("calendars ========================================================================================================================================\n");
		ArrayList<SITMCalendar> calendars = ds.findAllCalendarsByPlanVersion(DataSourceSystem.FILE_CSV,261);
		for (int i = 0; i < calendars.size(); i++) {System.out.println(calendars.get(i));}
		System.out.println();
		
		
		System.out.println("lines =========================================================================================================================================\n");
		ArrayList<SITMLine> lines = ds.findAllLinesByPlanVersion(DataSourceSystem.FILE_CSV,261);
		for (int i = 0; i < lines.size(); i++) {System.out.println(lines.get(i));}
		System.out.println();
		
		
		System.out.println("Stops ========================================================================================================================================\n");
		ArrayList<SITMStop> stops = ds.findAllStopsByLine(DataSourceSystem.FILE_CSV,261, 131);
		for (int i = 0; i < stops.size(); i++) {System.out.println(stops.get(i));}
		System.out.println();
	}

	public static void saveProject(SimController sm) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Date init = new Date(dateFormat.parse("2019-06-20 05:00:00").getTime());
		Date last = new Date(dateFormat.parse("2019-06-20 06:00:00").getTime());
		
		StateController pc = sm.getProjectController();
		Project project = new Project();
		project.setProjectName("test");
		project.setInitialDate(init);
		project.setFinalDate(last);
		project.setPlanVersionId(261);
		project.setLineId(131);
		project.setFileName("datagrams.csv");
		project.setFileSplit(",");
		project.setFileType(DataSourceSystem.FILE_CSV);
		pc.saveProject(project);
	}
	
	public static void visualizationTest() throws ParseException{
		SimController sm =  new SimController();
		sm.setDataSource(new DataSourceSystem());
		sm.setAnalytics(new Analytics());
		sm.getAnalytics().setDataSource(sm.getDataSource());
		sm.setProjectController(new StateController());
		sm.setEventProcessorController(new EventProcessorController());
		sm.setEventProvirderController(new EventProviderController());
		sm.getEventProvirderController().getEventFecher().setDataSource(sm.getDataSource());
		saveProject(sm);
		sm.start("test.dat");
	}
	
	public static void simulationTest() throws ParseException {
		SimController sm =  new SimController();
		sm.setDataSource(new DataSourceSystem());
		sm.setAnalytics(new Analytics());
		sm.getAnalytics().setDataSource(sm.getDataSource());
		sm.setProjectController(new StateController());
		sm.setEventProcessorController(new EventProcessorController());
		sm.setEventProvirderController(new EventProviderController());
		sm.getEventProvirderController().getEventFecher().setDataSource(sm.getDataSource());
		saveProject(sm);
		sm.startSimulation("test.dat",360);
	}
}
