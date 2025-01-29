package de.frauas.fb2.javaproject.ws25.group22.api;

/**
 * Represents a drone type, including specifications like manufacturer, weight, speed, battery capacity, etc.
 *
 * @author Parnia Esfahani
 */
public class DroneType {

    private int id;
    private String manufacturer;
    private String typename;
    private int weight;
    private int maxSpeed;
    private int batteryCapacity;
    private int controlRange;
    private int maxCarriage;

    /**
     * Constructs a DroneType with the specified parameters.
     *
     * @param id              the unique identifier of the drone type
     * @param manufacturer    the manufacturer of the drone type
     * @param typename        the type name
     * @param weight          the weight of the drone
     * @param maxSpeed        the maximum speed of the drone
     * @param batteryCapacity the battery capacity of the drone
     * @param controlRange    the control range of the drone
     * @param maxCarriage     the maximum carriage capacity of the drone
     */
    public DroneType(int id, String manufacturer, String typename, int weight,
                     int maxSpeed, int batteryCapacity, int controlRange, int maxCarriage) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.typename = typename;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        this.batteryCapacity = batteryCapacity;
        this.controlRange = controlRange;
        this.maxCarriage = maxCarriage;
    }

    public int getId() {
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
