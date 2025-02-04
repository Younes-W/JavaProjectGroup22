package de.frauas.fb2.javaproject.ws25.group22.api;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a drone with attributes such as type, creation time, serial number, and associated dynamics data.
 * Provides utility methods to calculate distance traveled and average speed based on its dynamics.
 *
 * @author Younes Wimmer, Tobias Ilcken, Parnia Esfahani
 */
public class Drone {
    private final int id;
    private final int droneTypeId;
    private final OffsetDateTime created;
    private final String serialNumber;
    private final int carriageWeight;
    private final String carriageType;
    private final List<DroneDynamics> droneDynamicsList = Collections.synchronizedList(new ArrayList<>());
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

    public int getId() {
        return id;
    }

    public int getDroneTypeId() {
        return droneTypeId;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getCarriageWeight() {
        return carriageWeight;
    }

    public String getCarriageType() {
        return carriageType;
    }

    public List<DroneDynamics> getDroneDynamicsList() {
        return droneDynamicsList;
    }

    public boolean isDynamicsFetched() {
        return dynamicsFetched;
    }

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
     *
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
            double longitude1 = droneDynamicsList.get(i - 1).getLongitude();
            double latitude1 = droneDynamicsList.get(i - 1).getLatitude();
            double longitude2 = droneDynamicsList.get(i).getLongitude();
            double latitude2 = droneDynamicsList.get(i).getLatitude();
            sum += haversine(longitude1, latitude1, longitude2, latitude2);
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
        DroneDynamics firstDynamics = droneDynamicsList.getFirst();
        DroneDynamics nthDynamics = droneDynamicsList.get(offset);

        long secondsElapsed = java.time.Duration.between(firstDynamics.getTimestamp(), nthDynamics.getTimestamp()).getSeconds();
        if (secondsElapsed == 0) {
            return 0.0;
        }
        double hoursElapsed = secondsElapsed / 3600.0;

        double distanceTraveled = calculateDistanceUpTo(offset);
        return distanceTraveled / hoursElapsed;
    }

    private double haversine(double lon1, double lat1, double lon2, double lat2) {
        final int R = 6371; // Radius of the Earth in kilometers
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
