package caroperations.factories;

import caroperations.interfaces.SpeedModel;
import caroperations.interfaces.TrafficUnit;

public class FactorySpeedModel {

    private static FactorySpeedModel singleton = new FactorySpeedModel();

    public static SpeedModel generateSpeedModel(SpeedModel.RoadCondition road, SpeedModel.TireCondition tire) {
        return singleton.new SpeedModelImpl(road, tire);
    }

    public static SpeedModel generateSpeedModel(TrafficUnit trafficUnit) {
        return singleton.new SpeedModelImpl(trafficUnit);
    }

    private class SpeedModelImpl implements SpeedModel {
        private RoadCondition road;
        private TireCondition tire;
        private TrafficUnit trafficUnit;
        SpeedModelImpl(RoadCondition road, TireCondition tire) {
            this.road = road;
            this.tire = tire;
        }
        SpeedModelImpl(TrafficUnit trafficUnit) {
            this.trafficUnit = trafficUnit;
        }
        /* old method
        @Override
        public double getSpeedMph(double timeSec, int weightPounds, int horsePower) {
            System.out.println("Road contion is " + road.name() + " => traction=" + road.getTraction());
            System.out.println("Tire contion is " + tire.name() + " => traction=" + tire.getTraction());
            double v = 2.0 * horsePower * 746;
            v = v * timeSec * 32.174 / weightPounds;
            v = v * road.getTraction()*tire.getTraction();
            return Math.round(Math.sqrt(v) * 0.68);
        } */
        @Override
        public double getSpeedMph(double timeSec, int weightPounds, int horsePower) {
            double traction = trafficUnit.getTraction();
            double v = 2.0 * horsePower * 746 * timeSec * 32.174 /weightPounds;
            return Math.round(Math.sqrt(v) * 0.68 * traction);
        }
    }
}
