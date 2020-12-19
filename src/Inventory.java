import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart)
    {
        allParts.add((newPart));

    }

    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

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

    public static boolean deletePart(Part selectedPart)
    {
        // Check to see if part is in list, delete if found
        if (allParts.contains(selectedPart))
            return allParts.remove(selectedPart);
        else
            return false;
    }

    public static boolean deleteProduct(Product selectedProduct)
    {
        // Check to see if product is in list, delete if found
        if (allProducts.contains(selectedProduct))
            return allProducts.remove(selectedProduct);
        else
            return false;
    }

    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }




}

