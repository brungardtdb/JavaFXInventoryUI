package InventoryAPI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
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
     * @param id the ID for the product
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @param name the name for the product
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param price the product price
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * @param stock the product stock number
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * @param min the product minimum
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * @param max the product maximum
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * @return the product ID
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * @return the product name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return the price of the product
     */
    public double getPrice()
    {
        return this.price;
    }

    /**
     * @return the product stock number
     */
    public int getStock()
    {
        return this.stock;
    }

    /**
     * @return the product minimum
     */
    public int getMin()
    {
        return this.min;
    }

    /**
     * @return the product maximum
     */
    public int getMax()
    {
        return this.max;
    }

    /**
     * currently adds part to list of associated parts
     */
    public void addAssociatedPart( Part part)
    {
        this.associatedParts.add(part);
    }

    /**
     * @return true if the part has successfully been deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return this.associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return a list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return this.associatedParts;
    }
}
