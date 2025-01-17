package Group22.API;

import org.json.JSONObject;

import java.time.OffsetDateTime;

public class DroneDynamics {
    private int droneInt;
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

    public DroneDynamics(String timestamp,int speed,double alignRoll,double alignPitch,double alignYaw,double longitude,double latitude,int batteryStatus,String lastSeen,String status) {
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

    public double getBatteryPercentage(DroneType type) {
        return ((double) this.batteryStatus / type.getBatteryCapacity()) * 100;
    }
    public double getBatteryConsumptionInPercent(double BatteryPercentage) {
        return 100.00 - BatteryPercentage;
    }

}
