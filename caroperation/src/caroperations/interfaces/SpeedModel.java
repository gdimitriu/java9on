package caroperations.interfaces;

public interface SpeedModel {
    double getSpeedMph(double timeSec, int weightPounds, int horsePower);
    enum RoadCondition {
        DRY(1.0),
        WET(0.2) {
            public double getTraction() {
                return temperature > 60 ? 0.4 : 0.2;
            }
        }, SNOW(0.04);
        public static int temperature;
        private double traction;
        RoadCondition(double traction) {
            this.traction = traction;
        }
        public double getTraction() {
            return this.traction;
        }
    }
    enum TireCondition {
        NEW(1.0), WORN(0.2);
        private double traction;
        TireCondition(double traction) {
            this.traction = traction;
        }
        public double getTraction() {
            return this.traction;
        }
    }
    enum DrivingCondition {
        ROAD_CONDITION, TIRE_CONDITION;
    }
}
