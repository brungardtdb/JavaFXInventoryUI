import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author David Brungardt
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart the part we are adding to the inventory
     */
    public static void addPart(Part newPart)
    {
        allParts.add((newPart));
    }

    /**
     * @param newProduct the product we are adding to the inventory
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * @param partID the ID for the part we are trying to return
     * @return the part if found
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
     * @param productID the ID for the product we are trying to return
     * @return the product if found
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
     * @param partName name for the part(s) we are trying to return
     * @return the part(s) if found
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
     * @param productName name for the product(s) we are trying to return
     * @return the product(s) if found
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
     * @param index position of part we wish to update in list
     * @param newPart the updated part object we are passing into the list
     * replaces a part in the list with a new part object at specified index
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
     * @param index position of product we wish to update in list
     * @param newProduct the updated product object we are passing into the list
     * replaces a product in the list with a new product object at specified index
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
     * @param selectedPart the part(s) we are trying to delete from the inventory
     * @return true if part was successfully deleted, false if part was not deleted or is not in inventory
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
     * @param selectedProduct the product(s) we are trying to delete from the inventory
     * @return true if product was successfully deleted, false if product was not deleted or is not in inventory
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
     * @return all parts in inventory
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * @return all products in inventory
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}

