public class Clothing extends Product{
    private String size;
    private String color;

    public Clothing(String id, String name, int noOfAvailable, double price, String size, String color) {
        super(id, name, noOfAvailable, price);
        this.size = size;
        this.color = color;
    }


    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String toString() {
        return super.toString() + ", size=" + size + ", color=" + color;
    }
}
