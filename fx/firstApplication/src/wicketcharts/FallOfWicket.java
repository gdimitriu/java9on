package wicketcharts;

public class FallOfWicket {
    private Double over;

    private Integer score;

    public FallOfWicket(String[] elements) {
        over = Double.parseDouble(elements[1]);
        score = Integer.parseInt(elements[2]);
    }

    public Double getOver() {
        return over;
    }

    public Integer getScore() {
        return score;
    }

}
