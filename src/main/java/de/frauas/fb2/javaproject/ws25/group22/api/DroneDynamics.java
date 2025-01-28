package de.frauas.fb2.javaproject.ws25.group22.api;

import java.time.OffsetDateTime;

/**
 * Represents the dynamics of a drone at a specific point in time, including
 * positional information, alignment, speed, battery, and status information.
 * @author Younes Wimmer, Tobias ilcken, Parnia Esfahani
 */

public class DroneDynamics {
    private OffsetDateTime timestamp;
    private int speed;
    private double alignRoll;
    private double alignPitch;
    private double alignYaw;
    private double longitude;
    private double latitude;
    private int batteryStatus;
    private OffsetDateTime lastSeen;
    private String status;

    /**
     * Constructs a DroneDynamics object with specified attributes.
     *
     * @param timestamp     timestamp of the dynamics.
     * @param speed         the speed of the drone at the given timestamp.
     * @param alignRoll     roll alignment value.
     * @param alignPitch    pitch alignment value.
     * @param alignYaw      yaw alignment value.
     * @param longitude     longitude coordinate.
     * @param latitude      latitude coordinate.
     * @param batteryStatus current battery status.
     * @param lastSeen      timestamp indicating when the drone was last seen.
     * @param status        status of the drone.
     */
    public DroneDynamics(String timestamp, int speed, double alignRoll, double alignPitch, double alignYaw,
                         double longitude, double latitude, int batteryStatus, String lastSeen, String status) {
        this.timestamp = OffsetDateTime.parse(timestamp);
        this.speed = speed;
        this.alignRoll = alignRoll;
        this.alignPitch = alignPitch;
        this.alignYaw = alignYaw;
        this.longitude = longitude;
        this.latitude = latitude;
        this.batteryStatus = batteryStatus;
        this.lastSeen = OffsetDateTime.parse(lastSeen);
        this.status = status;
    }

    /** @return the timestamp of these dynamics data */
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /** @return the speed at the given timestamp */
    public int getSpeed() {
        return speed;
    }

    /** @return the roll alignment value */
    public double getAlignRoll() {
        return alignRoll;
    }

    /** @return the pitch alignment value */
    public double getAlignPitch() {
        return alignPitch;
    }

    /** @return the yaw alignment value */
    public double getAlignYaw() {
        return alignYaw;
    }

    /** @return the longitude coordinate */
    public double getLongitude() {
        return longitude;
    }

    /** @return the latitude coordinate */
    public double getLatitude() {
        return latitude;
    }

    /** @return the current battery status */
    public int getBatteryStatus() {
        return batteryStatus;
    }

    /** @return the timestamp when the drone was last seen */
    public OffsetDateTime getLastSeen() {
        return lastSeen;
    }

    /** @return the status of the drone */
    public String getStatus() {
        return status;
    }

    /**
     * Calculates the battery percentage relative to the drone type's battery capacity.
     *
     * @param type the DroneType of the drone
     * @return the battery percentage as a value between 0 and 100.
     */
    public double getBatteryPercentage(DroneType type) {
        return ((double) this.batteryStatus / type.getBatteryCapacity()) * 100;
    }

    /**
     * Calculates the battery consumption in percent given a certain battery percentage.
     *
     * @param BatteryPercentage the current battery percentage
     * @return the battery consumption percentage
     */
    public double getBatteryConsumptionInPercent(double BatteryPercentage) {
        return 100.00 - BatteryPercentage;
    }
}
