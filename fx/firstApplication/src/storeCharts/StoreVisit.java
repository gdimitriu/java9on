package storeCharts;

public class StoreVisit {
    private Integer hour;
    private Integer visits;
    private Integer sales;

    public StoreVisit(String[] elements) {
        hour = Integer.parseInt(elements[0]);
        visits = Integer.parseInt(elements[1]);
        sales = Integer.parseInt(elements[2]);
    }
    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }
}
