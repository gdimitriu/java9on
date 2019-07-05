package caroperations.factories;

import caroperations.interfaces.*;

public class FactoryVehicle {
    private static FactoryVehicle singleton = new FactoryVehicle();
    public static Vehicle build(TrafficUnit trafficUnit) {
        switch (trafficUnit.getVehicleType()) {
            case CAR:
                return singleton.new CarImpl(trafficUnit.getPassengersCount(), trafficUnit.getWeightPounds(), trafficUnit.getHorsePower());
            case TRUCK:
                return singleton.new TruckImpl(trafficUnit.getPayloadPounds(), trafficUnit.getWeightPounds(), trafficUnit.getHorsePower());
            case CAB_CREW:
                return singleton.new CrewCabImpl(trafficUnit.getPassengersCount(), trafficUnit.getPayloadPounds(), trafficUnit.getWeightPounds(), trafficUnit.getHorsePower());
            default:
                System.out.println("Unexpected vehicle type " + trafficUnit.getVehicleType());
                return singleton.new CrewCabImpl(trafficUnit.getPassengersCount(), trafficUnit.getPayloadPounds(), trafficUnit.getWeightPounds(), trafficUnit.getHorsePower());
        }
    }

    class VehicleImpl implements Vehicle {
        private SpeedModel speedModel;
        private int weightPounds;
        private int horsePower;

        private VehicleImpl(int weightPounds, int horsePower) {
            this.weightPounds = weightPounds;
            this.horsePower = horsePower;
        }
        @Override
        public void setSpeedModel(SpeedModel speedModel) {
            this.speedModel = speedModel;
        }

        @Override
        public double getSpeedMph(double timeSec) {
            return this.speedModel.getSpeedMph(timeSec, weightPounds, horsePower);
        }

    }

    public static Car buildCar(int passengersCount, int weightPounds, int horsePower) {
        return singleton.new CarImpl(passengersCount, weightPounds, horsePower);
    }
    private class CarImpl extends VehicleImpl implements Car {
        private int passengersCount;

        private CarImpl(int passengersCount, int weightPounds, int horsePower) {
            super(weightPounds + passengersCount * 250, horsePower);
            this.passengersCount = passengersCount;
        }

        public int getPassengersCount() {
            return this.passengersCount;
        }
    }
    public static Truck buildTruck(int payloadPounds, int weightPounds, int horsePower) {
        return singleton.new TruckImpl(payloadPounds, weightPounds, horsePower);
    }

    private class TruckImpl extends VehicleImpl implements Truck {
        private int playloadPounds;

        private TruckImpl(int payloadPounds, int weightPounds, int horsePower) {
            super(weightPounds + payloadPounds, horsePower);
            this.playloadPounds = payloadPounds;
        }

        public int getPayloadPounds() {
            return this.playloadPounds;
        }
    }
    public static Vehicle buildCrewCab(int passengerCount,int payloadPounds, int weightPounds, int horsePower) {
        return singleton.new CrewCabImpl(passengerCount, payloadPounds, weightPounds, horsePower);
    }
    private class CrewCabImpl extends VehicleImpl implements Car, Truck {
        private int payloadPounds;
        private int passengersCount;

        private CrewCabImpl(int passengersCount, int payloadPounds, int weightPounds, int horsePower) {
            super (weightPounds + passengersCount * 250 + payloadPounds, horsePower);
            this.payloadPounds = payloadPounds;
            this.passengersCount = passengersCount;

        }

        @Override
        public int getPayloadPounds() {
            return this.payloadPounds;
        }

        @Override
        public int getPassengersCount() {
            return this.passengersCount;
        }
    }
}
