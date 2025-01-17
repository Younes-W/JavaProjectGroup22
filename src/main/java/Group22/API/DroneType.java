package Group22.API;

import org.json.JSONObject;

public class DroneType {
    private int id;
    private String manufacturer;
    private String typename;
    private int weight;
    private int maxSpeed;
    private int batteryCapacity;
    private int controlRange;
    private int maxCarriage;

    public DroneType(int id,String manufacturer,String typename,int weight,int maxSpeed,int batteryCapacity,int controlRange,int maxCarriage) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.typename = typename;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        this.batteryCapacity = batteryCapacity;
        this.controlRange = controlRange;
        this.maxCarriage = maxCarriage;
    }

    public int getID() {
        return id;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getTypename() {
        return typename;
    }
    public int getWeight() {
        return weight;
    }
    public int getMaxSpeed() {
        return maxSpeed;
    }
    public int getBatteryCapacity() {
        return batteryCapacity;
    }
    public int getControlRange() {
        return controlRange;
    }
    public int getMaxCarriage() {
        return maxCarriage;
    }

}
