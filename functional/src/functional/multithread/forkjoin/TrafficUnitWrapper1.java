package functional.multithread.forkjoin;

import functional.multithread.forkjoin.api.SpeedModel;
import functional.multithread.forkjoin.api.TrafficUnit;
import functional.multithread.forkjoin.api.Vehicle;

public class TrafficUnitWrapper1 {
    private double speed;
    private Vehicle vehicle;
    private TrafficUnit trafficUnit;

    public TrafficUnitWrapper1(TrafficUnit trafficUnit) {
        this.trafficUnit = trafficUnit;
        this.vehicle = FactoryVehicle.build(trafficUnit);
    }

    public TrafficUnitWrapper1 setSpeedModel(SpeedModel speedModel) {
        this.vehicle.setSpeedModel(speedModel);
        return this;
    }
    TrafficUnit getTrafficUnit() { return this.trafficUnit;}

    public double getSpeed() {
        return speed;
    }

    public TrafficUnitWrapper1 calcSpeed(double timeSec) {
        double speed = this.vehicle.getSpeedMph(timeSec);
        this.speed = Math.round(speed * this.trafficUnit.getTraction());
        return this;
    }
}
