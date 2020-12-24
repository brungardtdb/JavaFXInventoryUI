package InventoryAPI;
import InventoryAPI.AbstractClasses.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the class for product objects in our inventory.
 * @author David Brungardt
 */
public class Product
{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor used to initialize product objects.
     * @param id Product ID.
     * @param name Product name.
     * @param price Product price.
     * @param stock Product inventory #.
     * @param min Product inventory min.
     * @param max Product inventory max.
     * */
    public Product(int id, String name, double price,
                   int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Method used to assign an ID for the product.
     * @param id The ID for the product.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Method used to assign a name to the product.
     * @param name The name for the product.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Method used to assign a price to the product.
     * @param price The product price.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Method used to assign a stock or inventory number to the product.
     * @param stock The product stock or inventory number.
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * Method used to assign inventory minimum to the product.
     * @param min The product minimum.
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * Method used to assign inventory maximum to the product.
     * @param max The product maximum.
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * Method used to retrieve the product ID.
     * @return The product ID.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Method used to retrieve the product name.
     * @return The product name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Method used to retrieve the price or cost of product.
     * @return The price of the product.
     */
    public double getPrice()
    {
        return this.price;
    }

    /**
     * Method used to retrieve the inventory or stock number of the product.
     * @return The product stock number.
     */
    public int getStock()
    {
        return this.stock;
    }

    /**
     * Method used to retrieve the product inventory minimum.
     * @return The product minimum.
     */
    public int getMin()
    {
        return this.min;
    }

    /**
     * Method used to retrieve the product inventory maximum.
     * @return The product maximum.
     */
    public int getMax()
    {
        return this.max;
    }

    /**
     * Method used to associate part objects with the product (adds them to observable list).
     * @param part Associated part we are adding to product.
     */
    public void addAssociatedPart( Part part)
    {
        this.associatedParts.add(part);
    }

    /**
     * Method used to remove part object association with product (removes parts from observable list).
     * @param selectedAssociatedPart Part we are deleting from product.
     * @return true if the part has successfully been deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return this.associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Method used to retrieve all part objects that are associated with the product.
     * @return A list of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return this.associatedParts;
    }
}
