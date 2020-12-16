/**
 *
 * @author David Brungardt
 */
public class InHouse extends Part
{
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId the machine id to set for the in-house part.
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /**
     * @return the machineId for the in-house part
     */
    public int getMachineId()
    {
        return this.machineId;
    }
}
