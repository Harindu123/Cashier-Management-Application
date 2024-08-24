import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class WestminsterShoppingManager implements ShoppingManager {
    private final List<Product> productList;

    private final List<String> CustomerNICList = new ArrayList<>();

    // Constructor
    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }

    // Methods from ShoppingManager interface
    @Override
    public void addProduct(Product product) {
        productList.add(product);
        System.out.println("Product added successfully!");
    }

    @Override
    public void deleteProduct(String productId) {
        for (Product product : productList) {
            if (product.getId().equals(productId)) {

                System.out.println("Product deleted successfully!");
                String f = String.valueOf(product).replace("[", "").replace("]", "");
                String [] xx = f.split(",");
                for (String s : xx) {
                    System.out.println(s);
                }
                productList.remove(product);
                System.out.println("Remaining Amount of Products = " + productList.size());
                return;
            }
        }
        System.out.println("Product not found!");
    }

    @Override
    public void printProducts() {
        if (!productList.isEmpty()){
            ArrayList<Product> sortedProductList = new ArrayList<>(productList);
            sortedProductList.sort((Comparator.comparing(Product::getId)));
            for (Product product: sortedProductList){
                System.out.println(product);
            }
        } else {
            System.out.println("No products");
        }
    }

    @Override
    public void saveProducts() {
        // save to a file
        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            for (Product product : productList) {
                if (product instanceof Electronic electronicsProduct) {
                    // If the product is an instance of Electronics, save specific details
                    writer.println("Electronics," +
                            product.getId() + "," +
                            product.getName() + "," +
                            product.getNoOfAvailable() + "," +
                            product.getPrice() + "," +
                            electronicsProduct.getBrand() + "," +
                            electronicsProduct.getWarranty());
                } else if (product instanceof Clothing clothingProduct) {
                    // If the product is an instance of Clothing, save specific details
                    writer.println("Clothing," +
                            product.getId() + "," +
                            product.getName() + "," +
                            product.getNoOfAvailable() + "," +
                            product.getPrice() + "," +
                            clothingProduct.getSize() + "," +
                            clothingProduct.getColor());
                }
            }
            System.out.println("Products saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving products to file: " + e.getMessage());
        }
    }


    @Override
    public void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    String type = parts[0].trim();
                    if (type.equals("Electronics")) {
                        Electronic electronicsProduct = new Electronic(
                                parts[1].trim(),  // productId
                                parts[2].trim(),  // productName
                                Integer.parseInt(parts[3].trim()),  // availableItems
                                Double.parseDouble(parts[4].trim()),  // price
                                parts[5].trim(),  // brand
                                Integer.parseInt(parts[6].trim())  // warrantyPeriod
                        );
                        productList.add(electronicsProduct);
                    } else if (type.equals("Clothing")) {
                        Clothing clothingProduct = new Clothing(
                                parts[1].trim(),  // productId
                                parts[2].trim(),  // productName
                                Integer.parseInt(parts[3].trim()),  // availableItems
                                Double.parseDouble(parts[4].trim()),  // price
                                parts[5].trim(),  // size
                                parts[6].trim()   // color
                        );
                        productList.add(clothingProduct);
                    } else {
                        System.out.println("Invalid product type. Please try again.");
                    }
                }
            }
            System.out.println("Products loaded from file successfully.");
        } catch (IOException e) {
            System.err.println("Error loading products from file: " + e.getMessage());
        }
    }


    public void openShoppingGUI(String customerNIC) {
        //check if the customer is already in the list
        boolean flag = false;
        for (String nic : CustomerNICList) {
            if (nic.equals(customerNIC)) {
                flag = true;
                break;
            }
        }

        ShoppingCart shoppingCart = new ShoppingCart();

        if (!flag) {
            CustomerNICList.add(customerNIC);
            shoppingCart.setFirstPurchase(true);
        }

        ProductGUI productGUI = new ProductGUI(productList, shoppingCart);

        productGUI.setVisible(true);

    }

    public List<Product> getProductList() {
        return productList;
    }




}