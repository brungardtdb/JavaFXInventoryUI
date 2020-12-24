package InventoryAPI;
import InventoryAPI.AbstractClasses.Part;

/**
 * This is the class for in-house parts, it extends the provided "Part" abstract class.
 * @author David Brungardt
 */
public class InHouse extends Part
{
    private int machineId;

    /**
     * Constructor used to initialize in-house part objects
     * @param id Part ID.
     *  @param name Part name.
     *  @param price Part price.
     *  @param stock Part inventory #.
     *  @param min Part inventory min.
     *  @param max Part inventory max.
     * @param machineId Machine ID for machine where part is manufactured.
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Method used for assigning a machine ID to in-house parts.
     * @param machineId The machine id to set for the in-house part.
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /**
     * Method for retrieving the machine ID from in-house parts.
     * @return The machine ID for the in-house part.
     */
    public int getMachineId()
    {
        return this.machineId;
    }
}
