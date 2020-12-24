package InventoryAPI;
import InventoryAPI.AbstractClasses.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the inventory class that handles all of the inventory management logic for our inventory API.
 * @author David Brungardt
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Method used to add parts to the inventory.
     * @param newPart The part we are adding to the inventory.
     */
    public static void addPart(Part newPart)
    {
        allParts.add((newPart));
    }

    /**
     * Method used to add products to the inventory.
     * @param newProduct The product we are adding to the inventory.
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * Method used to look up parts in inventory based on part ID.
     * @param partID The ID for the part we are trying to return.
     * @return The part if found.
     */
    public static Part lookupPart(int partID)
    {
        // loop through parts and search for ID
        for (int i = 0; i < allParts.size(); i++)
        {   // if match is found, return part
            if (allParts.get(i).getId() == partID)
                return allParts.get(i);
        } // if no match is found, return null
        return null;
    }

    /**
     * Method used to look up products in inventory based on product ID.
     * @param productID The ID for the product we are trying to return.
     * @return The product if found.
     */
    public static Product lookupProduct(int productID)
    {
        // loop through products and search for ID
        for (int i = 0; i < allProducts.size(); i++)
        {   // if match is found, return product
            if (allProducts.get(i).getId() == productID)
                return allProducts.get(i);
        } // if no match is found, return null
        return null;
    }

    /**
     * Method used to look up part(s) in inventory based on part ID.
     * @param partName Name for the part(s) we are trying to return.
     * @return The part(s) if found.
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> returnList = FXCollections.observableArrayList();
        // iterate through parts, checking for a lower case match in name
        allParts.forEach((part -> {
            if (part.getName().toLowerCase().contains(partName.toLowerCase()))
                    returnList.add(part);
        }));
        // return a list containing any matches
        return returnList;
    }

    /**
     * Method used to look up product(s) in inventory based on product ID.
     * @param productName Name for the product(s) we are trying to return.
     * @return The product(s) if found.
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> returnList = FXCollections.observableArrayList();
        // iterate through products, checking for a lower case match in name
        allProducts.forEach((product -> {
            if (product.getName().toLowerCase().contains(productName.toLowerCase()))
                returnList.add(product);
        }));
        // return a list containing any matches
        return returnList;
    }

    /**
     * Method for updating parts in the inventory given an index and part to insert into index location.
     * @param index Position of part we wish to update in list.
     * @param newPart The updated part object we are passing into the list.
     */
    public static void  updatePart(int index, Part newPart)
    {
        // list index must be positive
        if (index < 0)
            throw new IndexOutOfBoundsException("Please provide a valid index.");
        // check to see if index is less than list size
        if (index < allParts.size())
            allParts.set(index, newPart);
        else // if index is too large, add new part
            allParts.add(newPart);
    }

    /**
     * Method for updating products in the inventory given an index and product to insert into index location.
     * @param index Position of product we wish to update in list.
     * @param newProduct The updated product object we are passing into the list.
     */
    public static void  updateProduct(int index, Product newProduct)
    {
        // list index must be positive
        if (index < 0)
            throw new IndexOutOfBoundsException("Please provide a valid index.");
        // check to see if index is less than list size
        if (index < allProducts.size())
            allProducts.set(index, newProduct);
        else // if index is too large, add new part
            allProducts.add(newProduct);
    }

    /**
     * Method for deleting a part from the inventory given a part to delete.
     * @param selectedPart The part(s) we are trying to delete from the inventory.
     * @return True if part was successfully deleted, false if part was not deleted or is not in inventory.
     */
    public static boolean deletePart(Part selectedPart)
    {
        // Check to see if part is in list, delete if found
        if (allParts.contains(selectedPart))
            return allParts.remove(selectedPart);
        else
            return false;
    }

    /**
     * Method for deleting a product from the inventory given a product to delete.
     * @param selectedProduct The product(s) we are trying to delete from the inventory.
     * @return True if product was successfully deleted, false if product was not deleted or is not in inventory.
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        // Check to see if product is in list, delete if found
        if (allProducts.contains(selectedProduct))
            return allProducts.remove(selectedProduct);
        else
            return false;
    }

    /**
     * Method for returning all parts currently in inventory.
     * @return All parts in inventory.
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * Method for returning all products currently in inventory.
     * @return All products in inventory.
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}

