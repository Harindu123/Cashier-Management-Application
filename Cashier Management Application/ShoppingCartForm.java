import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShoppingCartForm extends JFrame {
    private JTable cartTable;
    private JTextArea discountArea;

    public ShoppingCartForm() {
        // Initialize components
        cartTable = new JTable();
        cartTable.setEnabled(false); // Make the table read-only

        discountArea = new JTextArea();
        discountArea.setEditable(false);

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the frame
        add(new JScrollPane(cartTable), BorderLayout.CENTER);
        add(new JScrollPane(discountArea), BorderLayout.SOUTH);

        // Set frame properties
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose the form when closed
        setSize(400, 400);
        setLocationRelativeTo(null);
        // Ensure the JFrame is not set to exit the application when closed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void displayShoppingCartContents(ShoppingCart shoppingCart, double finalPrice) {
        List<Product> products = shoppingCart.getProducts();

        // Create a table model for the shopping cart
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product Name");
        model.addColumn("Quantity");
        model.addColumn("Price");

        // Populate the table model with shopping cart contents
        for (Product product : products) {
          /*  model.addRow(new Object[]{
                    product.getName(),
                    shoppingCart.getProductQuantity(product),
                    product.getPrice()
            }); */

            //ignore the same type of product and also update the price of the product using quantity
            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{
                        product.getName(),
                        shoppingCart.getProductQuantity(product),
                        product.getPrice() + " £"
                });
            } else {
                boolean flag = false;
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(product.getName())) {
                        int quantity = shoppingCart.getProductQuantity(product);
                        double price = product.getPrice();
                        double total = price * quantity;

                        model.setValueAt(quantity, i, 1);
                        model.setValueAt(total + " £", i, 2);

                        flag = true;
                    }
                }
                if (!flag) {
                    model.addRow(new Object[]{
                            product.getName(),
                            shoppingCart.getProductQuantity(product),
                            product.getPrice() + " £"
                    });
                }
            }

        }

        // Set the table model for the cartTable
        cartTable.setModel(model);

        // Display discounts and final price in the discountArea
        StringBuilder discounts = new StringBuilder();

        double CategoryDiscount = 0;
        double FirstPurchaseDiscount = 0;

        if (finalPrice < calculateTotalCost(products)) {


            discounts.append("Total: \t").append(shoppingCart.calculateTotalCost()).append("\n");

            if (shoppingCart.hasFirstPurchaseDiscount()) {
                // !DEBUG: Print the discount to the console
                System.out.println("First Purchase Discount (10%)");
                FirstPurchaseDiscount = shoppingCart.calculateTotalCost() * 0.1;
                discounts.append("First Purchase Discount (10%)\t").append("-").append(FirstPurchaseDiscount).append("\n");
            }

            if (shoppingCart.hasCategoryDiscount()) {
                // !DEBUG: Print the discount to the console
                System.out.println("Three Items in Same Category Discount (20%)");
                CategoryDiscount = shoppingCart.calculateTotalCost() * 0.2;
                discounts.append("Three Items in Same Category Discount (20%):\t").append("-").append(CategoryDiscount).append("£\n");
            }

        }
        discounts.append("\nFinal Total: \t").append(shoppingCart.calculateTotalCost() - CategoryDiscount - FirstPurchaseDiscount );
        // Set the discounts in the discountArea
        discountArea.setText(discounts.toString());
    }

    private double calculateTotalCost(List<Product> products) {
        double totalCost = 0;

        for (Product product : products) {
            totalCost += product.getPrice();
        }

        return totalCost;
    }
}
