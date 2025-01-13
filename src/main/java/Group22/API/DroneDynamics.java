package Group22.API;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class DroneDynamics {
    private LocalDateTime timestamp;
    private int speed;
    private double alignRoll;
    private double alignPitch;
    private double alignYaw;
    private double longitude;
    private double latitude;
    private int batteryStatus;
    private LocalDateTime lastSeen;
    private String status;

    public DroneDynamics(String timestamp,int speed,double alignRoll,double alignPitch,double alignYaw,double longitude,double latitude,int batteryStatus,String lastSeen,String status) {
        this.timestamp = LocalDateTime.parse(timestamp);
        this.speed = speed;
        this.alignRoll = alignRoll;
        this.alignPitch = alignPitch;
        this.alignYaw = alignYaw;
        this.longitude = longitude;
        this.latitude = latitude;
        this.batteryStatus = batteryStatus;
        this.lastSeen = LocalDateTime.parse(lastSeen);
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
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
    public LocalDateTime getLastSeen() {
        return lastSeen;
    }
    public String getStatus() {
        return status;
    }


    public static DroneDynamics parseDroneDynamics(JSONObject o) {
        String timestamp = o.getString("timestamp");
        String lastSeen = o.getString("last_seen");
        String status = o.getString("status");
        double alignRoll = o.getDouble("align_roll");
        double alignPitch = o.getDouble("align_pitch");
        double alignYaw = o.getDouble("align_yaw");
        double latitude = o.getDouble("latitude");
        double longitude = o.getDouble("longitude");
        int batteryStatus = o.getInt("battery_status");
        int speed = o.getInt("speed");

        return new DroneDynamics(timestamp, speed, alignRoll, alignPitch, alignYaw, longitude, latitude, batteryStatus,
                lastSeen, status);
    }
}
