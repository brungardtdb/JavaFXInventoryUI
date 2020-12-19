import javafx.collections.ObservableList;

import java.security.PublicKey;

public class InventoryTest {

    public InventoryTest()
    {
        /*CREATE IN HOUSE PART*/
        InHouse inHouse = new InHouse(1, "testPart1", 5.00, 5, 1, 10, 55);

        System.out.println("\nIn-House\n");
        System.out.println(inHouse.getName());
        System.out.println(inHouse.getMachineId());
        inHouse.setMachineId(15);
        System.out.println(inHouse.getMachineId());

        /*CREATE OUTSOURCED PART*/
        Outsourced outSourced = new Outsourced(2, "testPart2", 5.00, 5, 1, 10, "Boeing");

        System.out.println("\nOutsourced\n");
        System.out.println(outSourced.getName());
        System.out.println(outSourced.getCompanyName());
        outSourced.setCompanyName("Smith Optics");
        System.out.println((outSourced.getCompanyName()));

        /*CREATE PRODUCT*/
        Product product = new Product(505, "The Cat's Pajamas", 5.00, 10, 1, 50);
        product.addAssociatedPart(inHouse);
        product.addAssociatedPart(outSourced);

        System.out.println("\nProduct\n");
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getStock());
        System.out.println(product.getMin());
        System.out.println(product.getMax());
        System.out.println(product.getAllAssociatedParts());

        /*CREATE INVENTORY AND TEST METHODS*/
        Inventory newInventory = new Inventory();
        System.out.println("\n");
        newInventory.addPart(outSourced);
        newInventory.addPart(inHouse);
        Part newPart = newInventory.lookupPart(1);
        System.out.println(newPart.getName());

        System.out.println("\nCreate Inventory and add Parts/Products\n");

        newInventory.addProduct(product);
        Product returnedProduct = newInventory.lookupProduct(505);
        System.out.println(returnedProduct.getName());

        /* TEST RETURNING ALL PARTS AND PRODUCTS*/
        System.out.println("\nPrinting Inventory");
        ObservableList<Part> currentParts = newInventory.getAllParts();
        ObservableList<Product> currentProducts = newInventory.getAllProducts();

        System.out.println("\nGet all parts\n");

        currentParts.forEach((part -> {
            System.out.println(part.getName());
        }));

        System.out.println("\nGet all products\n");

        currentProducts.forEach((thisProduct ->{
            System.out.println(thisProduct.getName());
        }));

        /* TEST PART AND PRODUCT LOOKUP AND UPDATES*/
        ObservableList<Part> newParts= newInventory.lookupPart("test");

        System.out.println("\nPrinting Parts with test\n");

        newParts.forEach((part ->{
            System.out.println(part.getName());
            System.out.println(part.getMax());
        }));

        System.out.println("\nUpdating Part at index 1\n");
        newPart.setMax(200);
        newInventory.updatePart(1, newPart);

        newParts.forEach((part ->{
            System.out.println(part.getName());
            System.out.println(part.getMax());
        }));

        ObservableList<Product> newProducts = newInventory.lookupProduct("cat");

        System.out.println("\nPrinting Products\n");

        newProducts.forEach((thisProduct -> {
            System.out.println(thisProduct.getName());
            System.out.println(thisProduct.getStock());
        }));

        Product changeProduct = newProducts.get(0);
        changeProduct.setStock(20);
        System.out.println("\nUpdating Product\n");
        Product newProduct =  new Product(506, "The Cat's Meow", 5.00, 10, 1, 50);
        newInventory.updateProduct(0, changeProduct);
        newInventory.updateProduct(50, newProduct);


        ObservableList<Product> moreNewProducts = newInventory.lookupProduct("cat");

        moreNewProducts.forEach((thisProduct -> {
            System.out.println(thisProduct.getName());
            System.out.println(thisProduct.getStock());
        }));

        ObservableList<Part> allParts = newInventory.getAllParts();
        ObservableList<Product> allProducts = newInventory.getAllProducts();

        System.out.println("\nGet all parts\n");

        allParts.forEach((part -> {
            System.out.println(part.getName());
        }));

        System.out.println("\nGet all products\n");

        allProducts.forEach((thisProduct ->{
            System.out.println(thisProduct.getName());
        }));

        /* TEST DELETING PARTS AND PRODUCTS*/
        System.out.println("\nDeleting Part");

        if (newInventory.deletePart(newPart))
            newInventory.getAllParts().forEach((thisPart ->{
                System.out.println(thisPart.getName());
            }));

        if (newInventory.deletePart(newPart)) // shouldn't run if first was success
            newInventory.getAllParts().forEach((thisPart ->{
                System.out.println(thisPart.getName());
            }));

        System.out.println("\nDeleting Product");

        if (newInventory.deleteProduct(newProduct))
            newInventory.getAllProducts().forEach((thisProduct ->{
                System.out.println(thisProduct.getName());
            }));

        if (newInventory.deleteProduct(newProduct)) // shouldn't run if first was success
            newInventory.getAllProducts().forEach((thisProduct ->{
                System.out.println(thisProduct.getName());
            }));
    }
}
