import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingManagerTest {

    @Test
    public void testAddProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronic electronic = new Electronic("E001", "iPhone 12", 10, 1000.00, "Apple", 12);
        shoppingManager.addProduct(electronic);
        assertEquals(1, shoppingManager.getProductList().size());
    }

    @Test
    public void testRemoveProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronic electronic = new Electronic("E001", "iPhone 12", 10, 1000.00, "Apple", 12);
        shoppingManager.addProduct(electronic);
        shoppingManager.deleteProduct("E001");
        assertEquals(0, shoppingManager.getProductList().size());
    }

    @Test
    public void testSaveProducts() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronic electronic = new Electronic("E001", "iPhone 12", 10, 1000.00, "Apple", 12);
        shoppingManager.addProduct(electronic);
        shoppingManager.saveProducts();
        assertEquals(1, shoppingManager.getProductList().size());
        //test to see if the file is created
        File file = new File("products.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testLoadProducts() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronic electronic = new Electronic("E001", "iPhone 12", 10, 1000.00, "Apple", 12);
        shoppingManager.addProduct(electronic);
        shoppingManager.saveProducts();
        shoppingManager.deleteProduct("E001");
        shoppingManager.loadProducts();
        assertEquals(1, shoppingManager.getProductList().size());
    }

    @Test
    public void testPrintProducts() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronic electronic = new Electronic("E001", "iPhone 12", 10, 1000.00, "Apple", 12);
        shoppingManager.addProduct(electronic);
        shoppingManager.printProducts();
        //test products are printing correctly
        assertEquals("Product [id=E001, name=iPhone 12, noOfAvailable=10, price=1000.0, brand=Apple, warranty=12]", shoppingManager.getProductList().get(0).toString());


    }

}
