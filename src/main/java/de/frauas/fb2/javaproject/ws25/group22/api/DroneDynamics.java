package de.frauas.fb2.javaproject.ws25.group22.api;

import java.time.OffsetDateTime;

/**
 * Represents the dynamics of a drone at a specific point in time, including
 * positional information, alignment, speed, battery, and status information.
 *
 * @author Parnia Esfahani, Younes Wimmer, Tobias Ilcken, Parnia Esfahani
 */
public class DroneDynamics {
    private final OffsetDateTime timestamp;
    private final int speed;
    private final double alignRoll;
    private final double alignPitch;
    private final double alignYaw;
    private final double longitude;
    private final double latitude;
    private final int batteryStatus;
    private final OffsetDateTime lastSeen;
    private final String status;

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

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public int getSpeed() {
        return speed;
    }

    public double getAlignRoll() {
        return alignRoll;
    }

    public double getAlignPitch() {
        return alignPitch;
    }

    public double getAlignYaw() {
        return alignYaw;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public OffsetDateTime getLastSeen() {
        return lastSeen;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Calculates the battery percentage relative to the drone type's battery capacity.
     *
     * @param type the DroneType of the drone
     * @return the battery percentage as a value between 0 and 100.
     */
    public double calculateBatteryPercentage(DroneType type) {
        return ((double) this.batteryStatus / type.getBatteryCapacity()) * 100;
    }

    /**
     * Calculates the battery consumption in percent given a certain battery percentage.
     *
     * @param batteryPercentage the current battery percentage
     * @return the battery consumption percentage
     */
    public double calculateBatteryConsumptionPercentage(double batteryPercentage) {
        return 100.00 - batteryPercentage;
    }
}
