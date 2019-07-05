package functional.lambda.apis.functional.lambda.apis.calculator;

public class CalcImpl implements Calculator {
    private int par1;
    private double par2;
    public CalcImpl(int par1, double par2) {
        this.par1 = par1;
        this.par2 = par2;
    }
    public double calculateSomething() {
        return par1 * par2;
    }
}
