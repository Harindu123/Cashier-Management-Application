public class Electronic extends Product {

    private String brand;
    private int warranty;

    public Electronic(String id, String name, int noOfAvailable, double price, String brand, int warranty) {
        super(id, name, noOfAvailable, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String toString() {
        return super.toString() + ", brand=" + brand + ", warranty=" + warranty;
    }

}
