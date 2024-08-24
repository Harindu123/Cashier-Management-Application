import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class ProductGUI extends JFrame {
    private final JComboBox<String> productTypeComboBox;
    private final JTable productTable;
    private final JButton viewShoppingCartButton;
    private final JTextArea productDetailsArea;

    private final List<Product> productList;

    public ProductGUI(List<Product> productList, ShoppingCart shoppingCart) {
        this.productList = productList;


        // Initialize components
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        productTable = new JTable();
        JButton addToCartButton = new JButton("Add to Cart");
        viewShoppingCartButton = new JButton();
        ImageIcon imageIcon = new ImageIcon("cart.png");
        viewShoppingCartButton.setIcon(imageIcon);
        viewShoppingCartButton.setPreferredSize(new Dimension(60, 50));
        viewShoppingCartButton.setBackground(Color.WHITE);
        productDetailsArea = new JTextArea();

        //set the size of the text area
        productDetailsArea.setPreferredSize(new Dimension(200, 300));

        // Set layout manager
        setLayout(new BorderLayout());

        // Create a panel for product selection and buttons
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Product Category:"));
        topPanel.add(productTypeComboBox);

        topPanel.add(viewShoppingCartButton);

        //create a bottm panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(new JScrollPane(productDetailsArea));
        bottomPanel.add(addToCartButton);


        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Set up action listeners
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductTable((String) Objects.<Object>requireNonNull(productTypeComboBox.getSelectedItem()));
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to add selected product to the shopping cart
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the product from the selected row
                    String productId = (String) productTable.getValueAt(selectedRow, 0);
                    Product selectedProduct = getProductById(productId);

                    // Add the selected product to the shopping cart
                    shoppingCart.addProduct(selectedProduct);
                } else {
                    JOptionPane.showMessageDialog(ProductGUI.this, "Please select a product to add to the cart.",
                            "No Product Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        viewShoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of the shopping cart form
                ShoppingCartForm shoppingCartForm = new ShoppingCartForm();

                // Pass the shopping cart contents and final price to the shopping cart form
                shoppingCartForm.displayShoppingCartContents(shoppingCart, shoppingCart.calculateFinalCost());

                // Optionally, display the shopping cart form
                shoppingCartForm.setVisible(true);
            }
        });

        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                // Get the product from the selected row
                String productId = (String) productTable.getValueAt(selectedRow, 0);
                Product selectedProduct = getProductById(productId);

                // Update the product details area with the selected product
                updateProductDetailsArea(selectedProduct);
            }
        });

        // Initialize the product table with all products
        updateProductTable("All");

        // Set frame properties
        setTitle("Westminster Shopping Center");
        // Proper way to close the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to update the product table based on the selected product type
    private void updateProductTable(String productType) {
        List<Product> filteredProducts;

        if (productType.equals("All")) {
            // Display all products
            filteredProducts = productList;
        } else {
            // Filter products based on the selected product type
            filteredProducts = productList.stream()
                    .filter(product -> {
                        if (productType.equals("Electronics") && product instanceof Electronic) {
                            return true;
                        } else return productType.equals("Clothes") && product instanceof Clothing;
                    })
                    .collect(Collectors.toList());
        }

        // Update the table with the filtered products
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Price (£)");
        model.addColumn("Info");

        for (Product product : filteredProducts) {
            model.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product instanceof Electronic ? ((Electronic) product).getBrand() + ", " + ((Electronic) product).getWarranty() + " Months warranty" :
                            ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor()
            });
        }

        productTable.setModel(model);
    }

    // Method to update the product details area based on the selected product
    private void updateProductDetailsArea(Product selectedProduct) {
        if (selectedProduct != null) {
            String details = "Selected Product Details:\n";
            details += "Product ID: " + selectedProduct.getId() + "\n" +
                    "Product Name: " + selectedProduct.getName() + "\n" +
                    "Available Items: " + selectedProduct.getNoOfAvailable() + "\n" +
                    "Price: " + selectedProduct.getPrice();

            if (selectedProduct instanceof Electronic electronicsProduct) {
                details += "\nBrand: " + electronicsProduct.getBrand() + "\n" +
                        "Warranty Period: " + electronicsProduct.getWarranty() + " months";
            } else if (selectedProduct instanceof Clothing clothingProduct) {
                details += "\nSize: " + clothingProduct.getSize() + "\n" +
                        "Color: " + clothingProduct.getColor();
            }

            productDetailsArea.setText(details);
        } else {
            productDetailsArea.setText(""); // Clear details area if no product is selected
        }
    }



    // Helper method to get a product by its ID
    private Product getProductById(String productId) {
        for (Product product : productList) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }




}
