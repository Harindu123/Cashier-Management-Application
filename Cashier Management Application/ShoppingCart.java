import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private List<Product> products;
    private Map<String, Integer> categoryCounts; // Map to store the count of products per category
    private boolean firstPurchase; // Flag to track the first purchase


    // Constructor
    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.categoryCounts = new HashMap<>();
        this.firstPurchase = false;
    }

    public ShoppingCart getInstance() {
        return this;
    }

    // Methods
    public void addProduct(Product product) {
        products.add(product);

        // Update the category count
        String category = getProductCategory(product);
        categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
    }

    public void removeProduct(Product product) {
        products.remove(product);

        // Update the category count
        String category = getProductCategory(product);
        categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) - 1);
    }


    public List<Product> getProducts() {
        return products;
    }

    public double calculateTotalCost() {
        double totalCost = 0;

        for (Product product : products) {
            totalCost += product.getPrice();
        }

        return totalCost;
    }

    public double calculateFinalCost() {
        double totalCost = calculateTotalCost();

        // Apply first purchase discount (10%)
        if (firstPurchase) {
            totalCost *= 0.9;
        }

        // Apply category discount (20%) if the user buys at least three products of the same category
        for (int count : categoryCounts.values()) {
            if (count >= 3) {
                totalCost *= 0.8; // 20% discount

            }
        }

        return totalCost;
    }

    public boolean hasFirstPurchaseDiscount() {
        return firstPurchase;
    }

    public boolean hasCategoryDiscount() {
        for (int count : categoryCounts.values()) {
            if (count >= 3) {
                return true;
            }
        }
        return false;
    }

    private String getProductCategory(Product product) {
        if (product instanceof Electronic) {
            return "Electronics";
        } else if (product instanceof Clothing) {
            return "Clothing";
        } else {
            return "Other";
        }
    }

    public int getProductQuantity(Product product) {
        int count = 0;

        for (Product p : products) {
            if (p.equals(product)) {
                count++;
            }
        }

        return count;
    }


    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }
}