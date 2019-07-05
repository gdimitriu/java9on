package functional.referencemethods;

public class Food {
    private String name;
    public Food() {
        this.name = "Donut!";
    }
    public Food(String name) {
        this.name = name;
    }
    public Food(String name, String anotherName) {
        this.name = name + " and " + anotherName;
    }
    public Food(String name1, String name2, String name3) {
        this.name = name1 + " and " + name2 + " and " + name3;
    }
    public String getFavorite() {
        return this.name;
    }
    public String getFavorite(int num ) {
        return num >1 ? String.valueOf(num) + " donuts !" : "Donut!";
    }
}
