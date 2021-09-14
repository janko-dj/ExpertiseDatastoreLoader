package com.janko.expertise.DatastoreLoader.model;

import java.util.List;

public class MachineIds {
    private List<String> machineIdsList;

    public MachineIds() {
    }

    public MachineIds(List<String> machineIdsList) {
        this.machineIdsList = machineIdsList;
    }

    public List<String> getMachineIdsList() {
        return machineIdsList;
    }

    public void setMachineIdsList(List<String> machineIdsList) {
        this.machineIdsList = machineIdsList;
    }
}
