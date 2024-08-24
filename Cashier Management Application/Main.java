import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    //display menu
    static Scanner scanner = new Scanner(System.in);
    static WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

    public static void main(String[] args) {
        shoppingManager.loadProducts();

        int option = 0;

        while (option != 6) {

            try {
                System.out.println("Welcome to Westminster Shopping Manager!");

                System.out.println("1. Add a product");
                System.out.println("2. Delete a product");
                System.out.println("3. Print all products");
                System.out.println("4. Save products to file");
                System.out.println("5. Open SHopping GUI");
                System.out.println("6. Exit");
                System.out.print("Please select an option : ");

                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        System.out.print("Please enter the product ID : ");
                        String productId = scanner.next();

                        shoppingManager.deleteProduct(productId);
                        break;
                    case 3:
                        shoppingManager.printProducts();
                        break;
                    case 4:
                        shoppingManager.saveProducts();
                        break;
                    case 5:
                        System.out.print("Enter Customer NIC : ");
                        String nic = scanner.next();
                        shoppingManager.openShoppingGUI(nic);
                        break;
                    case 6:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }
        }

        System.out.println(" Thank you for using Westminster Shopping Manager!");
    }
    static void addProduct(){
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Please select a product type : ");
        int productType = scanner.nextInt();
        if (productType == 1) {
            System.out.println("Please enter the product details  ");
            System.out.print("Product ID : ");
            String productId = scanner.next();
            System.out.print("Product Name : ");
            String productName = scanner.next();
            System.out.print("Available Items :" );
            int availableItems = scanner.nextInt();
            System.out.print("Price : ");
            double price = scanner.nextDouble();
            System.out.print("Brand : ");
            String brand = scanner.next();
            System.out.print("Warranty Period (In Months) : ");
            int warrantyPeriod = scanner.nextInt();

            Electronic electronic = new Electronic(productId, productName, availableItems, price, brand, warrantyPeriod);
            shoppingManager.addProduct(electronic);

        } else if (productType == 2) {
            System.out.println("Please enter the product details  ");
            System.out.print("Product ID : ");
            String productId = scanner.next();
            System.out.print("Product Name : ");
            String productName = scanner.next();
            System.out.print("Available Items : ");
            int availableItems = scanner.nextInt();
            System.out.print("Price : ");
            double price = scanner.nextDouble();
            System.out.print("Size : ");
            String size = scanner.next();
            System.out.print("Color : ");
            String color = scanner.next();

            Clothing clothing = new Clothing(productId, productName, availableItems, price, size, color);
            shoppingManager.addProduct(clothing);
        } else {
            System.out.println("Invalid product type. Please try again.");
        }
    }
}
