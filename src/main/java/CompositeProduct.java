import java.util.List;

public class CompositeProduct extends MenuItem {

    List<MenuItem> products;
    private String title;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getSodium() {
        return sodium;
    }

    public int getPrice() {
        return price;
    }

    public CompositeProduct(String name, List<MenuItem> products) {
        this.title = name;
        this.products = products;
        this.price = computePrice();
        this.rating = computeRating();
        this.calories = computeCalories();
        this.protein = computeProtein();
        this.fat = computeFat();
        this.sodium = computeSodium();
    }

    @Override
    public int computePrice() {
        int price = 0;
        for (MenuItem product : products)
            price += product.getPrice();
        return price;
    }

    public double computeRating() {
        double rating = 0;
        for (MenuItem product : products)
            rating += product.getRating();
        return rating / products.size();
    }

    public int computeCalories() {
        int calories = 0;
        for (MenuItem product : products)
            calories += product.getPrice();
        return calories;
    }

    public int computeProtein() {
        int protein = 0;
        for (MenuItem product : products)
            protein += product.getProtein();
        return protein;
    }

    public int computeFat() {
        int fat = 0;
        for (MenuItem product : products)
            fat += product.getFat();
        return fat;
    }

    public int computeSodium() {
        int sodium = 0;
        for (MenuItem product : products)
            sodium += product.getSodium();
        return sodium;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public void setProtein(int protein) {
        this.protein = protein;
    }

    @Override
    public void setFat(int fat) {
        this.fat = fat;
    }

    @Override
    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }
}
