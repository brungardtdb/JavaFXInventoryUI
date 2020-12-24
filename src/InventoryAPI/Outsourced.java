package InventoryAPI;
import InventoryAPI.AbstractClasses.Part;

/**
 * This is the class for outsourced parts, it extends the provided "Part" abstract class.
 * @author David Brungardt
 */
public class Outsourced extends Part
{
    private String companyName;

    /**
     * Constructor used to initialize outsourced part objects.
     * @param id Part ID.
     *  @param name Part name.
     *  @param price Part price.
     *  @param stock Part inventory #.
     *  @param min Part inventory min.
     *  @param max Part inventory max.
     * @param companyName Name of company supplying the part.
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Method used for assigning the company name (name of company manufacturing the part) of parts we are outsourcing.
     * @param companyName The name of the company we are outsourcing the part to.
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * Method used to retrieve the company name (name of the company manufacturing the part) of parts we are outsourcing.
     * @return The name of the company we are outsourcing the part to.
     */
    public String getCompanyName()
    {
        return this.companyName;
    }
}
