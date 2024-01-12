package imhoff.wgubikerecreated.model;

/**
 * Class: InHouse allows values for InHouse to be entered.
 */
public class InHouse extends Part {
    private int machineID;

    public InHouse(int id, String name, double price, int inStock, int min, int max, int machineID) {
        super(id, name, price, inStock, min, max);
        this.machineID = machineID;
    }

    /**
     * MachineID getter.
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * MachineID setter.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

}