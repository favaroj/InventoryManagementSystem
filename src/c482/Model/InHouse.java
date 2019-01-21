/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.Model;

/**
 *
 * @author john6
 */
public class InHouse extends Part {
    
    private int machineId;
    
    public InHouse() {
        super();
    }
    
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    public int getMachineId() {
        return machineId;
    }
}
