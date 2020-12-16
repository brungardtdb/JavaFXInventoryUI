/**
 *
 * @author David Brungardt
 */
public class Outsourced extends Part
{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName the name of the company we are outsourcing the part to
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * @return the name of the company we are outsourcing the part to
     */
    public String getCompanyName()
    {
        return this.companyName;
    }
}
