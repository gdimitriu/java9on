package caroperations;

import caroperations.factories.FactorySpeedModel;
import caroperations.factories.FactoryVehicle;
import caroperations.interfaces.Car;
import caroperations.interfaces.SpeedModel;

public class MainTest {
    public static void main(String... arg) {
        double timeSec = 10.0;
        for (SpeedModel.RoadCondition road : SpeedModel.RoadCondition.values()) {
            for (SpeedModel.TireCondition tire: SpeedModel.TireCondition.values()) {
                road.temperature = 63;
                SpeedModel speedModel = FactorySpeedModel.generateSpeedModel(road, tire);
                Car car = FactoryVehicle.buildCar(4, 4000, 246);
                car.setSpeedModel(speedModel);
                System.out.println("Car speed (" + timeSec + " sec) = " + car.getSpeedMph(timeSec) + " mph");
            }
        }
    }
}
