package functional.streams;

public class Thing {
    private int someInt;
    public Thing(int i) {
        this.someInt = i;
    }
    public int getSomeInt() {
        return someInt;
    }
    public String getSomeString() {
        return Integer.toString(someInt);
    }
}
