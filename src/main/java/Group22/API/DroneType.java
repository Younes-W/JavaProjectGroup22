package Group22.API;

public class DroneType {

    /**
     * Represents a dronetype, including specifications like manufacturer, weight, speed, battery capacity, etc.
     */

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

    /** @return the unique identifier of this drone type */
    public int getId() {
        return id;
    }

    /** @return the manufacturer of this drone type */
    public String getManufacturer() {
        return manufacturer;
    }

    /** @return the type name of this drone type */
    public String getTypename() {
        return typename;
    }

    /** @return the weight of this drone type */
    public int getWeight() {
        return weight;
    }

    /** @return the maximum speed of this drone type */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /** @return the battery capacity of this drone type */
    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    /** @return the control range of this drone type */
    public int getControlRange() {
        return controlRange;
    }

    /** @return the maximum carriage capacity of this drone type */
    public int getMaxCarriage() {
        return maxCarriage;
    }
}
