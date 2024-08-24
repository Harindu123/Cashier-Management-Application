public abstract class Product {
    private String id;
    private String name;
    private int noOfAvailable;
    private double price;

    public Product(String id, String name, int noOfAvailable, double price) {
        this.id = id;
        this.name = name;
        this.noOfAvailable = noOfAvailable;
        this.price = price;
    }

    Product(Product p){
        this.id = p.id;
        this.name = p.name;
        this.noOfAvailable = p.noOfAvailable;
        this.price = p.price;
    }


    public String getId() {
        return id;
    }66

    public String getName() {
        return name;
    }

    public int getNoOfAvailable() {
        return noOfAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoOfAvailable(int noOfAvailable) {
        this.noOfAvailable = noOfAvailable;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return "Product id=" + id + ", name=" + name + ", noOfAvailable=" + noOfAvailable + ", price=" + price;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Product) {
            Product p = (Product) obj;
            return this.id.equals(p.id);
        }

        return false;
    }

}
