package com.simulationFrameworkDT.restService.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simulationFrameworkDT.model.Operation;
import com.simulationFrameworkDT.restService.interfaces.ISimulationRest;
import com.simulationFrameworkDT.simulation.SimController;

@RequestMapping("simulation/controller")
@RestController
@CrossOrigin(origins = "*")
public class SimulationRest implements ISimulationRest {
	
	@Autowired
	private SimController SimController;

	@PutMapping("/simulation/{id}/{headway}")
	@ResponseStatus(HttpStatus.OK)
	public String simulation(@PathVariable("id") String projectName, @PathVariable("headway") int headwayDesigned) {
		SimController.startSimulation(projectName,131,headwayDesigned*60);
		return projectName;
	}
	
	@PutMapping("/operation/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Operation getOperation(@PathVariable("id") String projectName) {
		return SimController.getSimulationThread().getOperation();
	}
	
	@PutMapping("/start/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void start(@PathVariable("id") String projectName) {
		SimController.startVisualization(projectName+".dat");
	}

	@PutMapping("/pause/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void pause(@PathVariable("id") String projectName) {
		SimController.pauseVisualization();
	}

	@PutMapping("/resume/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void resume(@PathVariable("id") String projectName) {
		SimController.resumeVisualization();
	}

	@PutMapping("/finish/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void stop(@PathVariable("id") String projectName) {
		SimController.finishVisualization();
	}

}
