package com.simulationFrameworkDT.restService.implementation;


import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulationFrameworkDT.dataSource.DataSourceSystem;
import com.simulationFrameworkDT.model.factorySITM.SITMCalendar;
import com.simulationFrameworkDT.model.factorySITM.SITMLine;
import com.simulationFrameworkDT.model.factorySITM.SITMPlanVersion;
import com.simulationFrameworkDT.model.factorySITM.SITMStop;
import com.simulationFrameworkDT.restService.dataTransfer.ProjectDTO;
import com.simulationFrameworkDT.restService.interfaces.IDataSourceRest;
import com.simulationFrameworkDT.simulation.state.Project;
import com.simulationFrameworkDT.simulation.state.StateController;

@RequestMapping("simulation/datasource")
@RestController
@CrossOrigin(origins = "*")
public class DataSourceRest implements IDataSourceRest{

	@Autowired
	private DataSourceSystem dataSource;
	
	@Autowired
	private StateController projectController;
	
	@GetMapping("/datagrams")
	public String[] getFileNames() {
		return dataSource.getFileNames();
	}
	
	@GetMapping("planversion")
	public long findPlanVersion(@RequestBody ProjectDTO project) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date initdate;
		Date finaldate;
		long planVersionId = 0;
		
		try {
			initdate = new Date(dateFormat.parse(project.getInitialDate()).getTime());
			finaldate = new Date(dateFormat.parse(project.getFinalDate()).getTime());
			planVersionId = dataSource.findPlanVersionByDate(project.getFileType(), initdate, finaldate).getPlanVersionId();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return planVersionId;
	}
	
	
	@GetMapping("/planversions")
	public ArrayList<SITMPlanVersion> findAllPlanVersions(String type) {
		return dataSource.findAllPlanVersions(type);
	}

	@GetMapping("/calendars")
	public ArrayList<SITMCalendar> findAllCalendarsByPlanVersion(String type, long planVersionId) {
		return dataSource.findAllCalendarsByPlanVersion(type, planVersionId);
	}
	
	@GetMapping("/dates")
		public ArrayList<Date> findDatesByPlanVersion(String type, long planVersionId) {
		
		ArrayList<SITMCalendar> calendars = dataSource.findAllCalendarsByPlanVersion(type, planVersionId);
		ArrayList<Date> dates = new ArrayList<>();

		dates.add(calendars.get(0).getOperationDay());
		dates.add(calendars.get(calendars.size() - 1).getOperationDay());
		return dates;
	}

	@GetMapping("/lines")
	public ArrayList<SITMLine> findAllLinesByPlanVersion(String type, long planVersionId) {
		return dataSource.findAllLinesByPlanVersion(type, planVersionId);
	}

	@GetMapping("/stops")
	public ArrayList<SITMStop> findAllStopsByLine(String type, long planVersionId, long lineId) {
		return dataSource.findAllStopsByLine(type, planVersionId, lineId);
	}
	
	@GetMapping("/headers")
	public String[] getHeaders(String projectName) {
		Project project = projectController.loadProject(projectName+".dat");
		return dataSource.getHeaders(project);
	}

}
