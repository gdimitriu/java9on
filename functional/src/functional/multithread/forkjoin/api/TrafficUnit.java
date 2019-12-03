package functional.multithread.forkjoin.api;

import functional.multithread.forkjoin.api.SpeedModel.RoadCondition;
import functional.multithread.forkjoin.api.SpeedModel.TireCondition;
import functional.multithread.forkjoin.api.Vehicle.VehicleType;


public interface TrafficUnit {
    VehicleType getVehicleType();
    int getHorsePower();
    int getWeightPounds();
    int getPayloadPounds();
    int getPassengersCount();
    double getSpeedLimitMph();
    double getTraction();
    RoadCondition getRoadCondition();
    TireCondition getTireCondition();
    int getTemperature();
}



