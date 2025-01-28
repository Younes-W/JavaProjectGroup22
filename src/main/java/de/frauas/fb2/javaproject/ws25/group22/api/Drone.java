package de.frauas.fb2.javaproject.ws25.group22.api;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a drone with attributes such as type, creation time, serial number, and associated dynamics data.
 * Provides utility methods to calculate distance traveled and average speed based on its dynamics.
 * @author Younes Wimmer, Tobias Ilcken, Parnia Esfahani
 */

public class Drone {
    private int id;
    private int droneTypeId;
    private OffsetDateTime created;
    private String serialNumber;
    private int carriageWeight;
    private String carriageType;
    private List<DroneDynamics> droneDynamicsList = Collections.synchronizedList(new ArrayList<>());
    private boolean dynamicsFetched = false;

    /**
     * Constructs a Drone with specified attributes.
     *
     * @param id             the drone identifier
     * @param droneTypeId    the identifier of the drone type
     * @param created        the creation timestamp of the drone
     * @param serialNumber   the serial number of the drone
     * @param carriageWeight the weight of the carriage
     * @param carriageType   the type of the carriage
     */
    public Drone(int id, int droneTypeId, String created, String serialNumber, int carriageWeight, String carriageType) {
        this.id = id;
        this.droneTypeId = droneTypeId;
        this.created = OffsetDateTime.parse(created);
        this.serialNumber = serialNumber;
        this.carriageWeight = carriageWeight;
        this.carriageType = carriageType;
    }

    /** @return the unique identifier of the drone */
    public int getId() {
        return id;
    }

    /** @return the drone type identifier */
    public int getDroneTypeId() {
        return droneTypeId;
    }

    /** @return the creation timestamp of the drone */
    public OffsetDateTime getCreated() {
        return created;
    }

    /** @return the serial number of the drone */
    public String getSerialNumber() {
        return serialNumber;
    }

    /** @return the weight of the carriage */
    public int getCarriageWeight() {
        return carriageWeight;
    }

    /** @return the type of the carriage */
    public String getCarriageType() {
        return carriageType;
    }

    /** @return the list of dynamics data associated with this drone */
    public List<DroneDynamics> getDroneDynamicsList() {
        return droneDynamicsList;
    }

    /** @return true if dynamics data has been fetched, false otherwise */
    public boolean getDynamicsFetched() {
        return dynamicsFetched;
    }

    /** @return the number of dynamics available for this drone */
    public int getDynamicsCount() {
        return droneDynamicsList.size();
    }

    /**
     * Retrieves the dynamics at the specified offset in the dynamics list.
     *
     * @param offset the index of the desired dynamics data
     * @return the DroneDynamics object at the given offset
     */
    public DroneDynamics getDynamics(int offset) {
        return droneDynamicsList.get(offset);
    }

    /**
     * Sets the flag indicating whether the dynamics have been fetched.
     * @param dynamicsFetched true if dynamics data has been fetched, false otherwise.
     */
    public void setDynamicsFetched(boolean dynamicsFetched) {
        this.dynamicsFetched = dynamicsFetched;
    }

    /**
     * Calculates the total distance traveled by the drone up to the specified dynamics offset.
     * Uses the Haversine formula between consecutive dynamics coordinates.
     *
     * @param offset the index up to which the distance should be calculated
     * @return the total distance traveled in kilometers.
     */
    public double calculateDistanceUpTo(int offset) {
        if (offset >= getDynamicsCount()) {
            offset = getDynamicsCount() - 1;
        }
        double sum = 0.0;
        for (int i = 1; i <= offset; i++) {
            double lon1 = droneDynamicsList.get(i - 1).getLongitude();
            double lat1 = droneDynamicsList.get(i - 1).getLatitude();
            double lon2 = droneDynamicsList.get(i).getLongitude();
            double lat2 = droneDynamicsList.get(i).getLatitude();
            sum += haversine(lon1, lat1, lon2, lat2);
        }
        return sum;
    }

    /**
     * Calculates the average speed of the drone from the beginning up to the specified dynamics offset.
     * Average speed is computed as total distance divided by total time elapsed in hours.
     *
     * @param offset the index up to which the average speed should be calculated.
     * @return the average speed in kilometers per hour (km/h).
     */
    public double calculateAverageSpeedUpTo(int offset) {
        if (offset < 1 || offset >= getDynamicsCount()) {
            return 0.0;
        }
        DroneDynamics first = droneDynamicsList.getFirst();
        DroneDynamics nth   = droneDynamicsList.get(offset);

        long seconds = java.time.Duration.between(first.getTimestamp(), nth.getTimestamp()).getSeconds();
        if (seconds == 0) {
            return 0.0;
        }
        double hours = seconds / 3600.0;

        double distanceUpToN = calculateDistanceUpTo(offset);
        return distanceUpToN / hours;
    }

    /**
     * Computes the distance between two geographic coordinates using the Haversine formula.
     *
     * @param lon1 longitude of the first point
     * @param lat1 latitude of the first point
     * @param lon2 longitude of the second point
     * @param lat2 latitude of the second point
     * @return the distance in kilometers between the two points.
     */
    private double haversine(double lon1, double lat1, double lon2, double lat2) {
        final int R = 6371;
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2)
                + Math.cos(phi1) * Math.cos(phi2)
                * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
