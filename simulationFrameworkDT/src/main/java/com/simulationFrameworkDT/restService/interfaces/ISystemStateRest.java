package com.simulationFrameworkDT.restService.interfaces;

import java.util.ArrayList;

import com.simulationFrameworkDT.systemState.factorySITM.SITMBus;

public interface ISystemStateRest {

	public ArrayList<SITMBus> findAllBuses(long lineId);

}
