package Group22.API;

import Group22.Util.Util;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class Drone {
    private int id;
    private int droneTypeId;
    private  String created;
    private  String serialNumber;
    private  int carriageWeight;
    private  String carriageType;
    private List<DroneDynamics> droneDynamicsList = new ArrayList<>();
    private double totalDistanceTravelled = 0.0;
    private boolean dynamicsFetched = false;

    public Drone(int id, int droneTypeId, String created, String serialNumber, int carriageWeight, String carriageType) {
        this.id = id;
        this.droneTypeId = droneTypeId;
        this.created = created;
        this.serialNumber = serialNumber;
        this.carriageWeight = carriageWeight;
        this.carriageType = carriageType;
    }

    public int getID(){
        return id;
    }
    public int getDroneTypeId(){
        return droneTypeId;
    }
    public String getCreated(){
        return created;
    }
    public String getSerialNumber(){
        return serialNumber;
    }
    public int getCarriageWeight(){
        return carriageWeight;
    }
    public String getCarriageType(){
        return carriageType;
    }

    public List<DroneDynamics> getDroneDynamicsList(){
        return droneDynamicsList;
    }
    public double getTotalDistanceTravelled() {
        return totalDistanceTravelled;
    }

    public boolean getDynamicsFetched(){
        return dynamicsFetched;
    }

    public int getDynamicsCount(){
        return droneDynamicsList.size();
    }

    public DroneDynamics getDynamics(int offset){
        return droneDynamicsList.get(offset);
    }

    public void setDynamicsFetched(boolean dynamicsFetched) {
        this.dynamicsFetched = dynamicsFetched;
    }

    public static Drone parseDrone(JSONObject o) {
        int id = o.getInt("id");
        String created = o.getString("created");
        String serialNumber = o.getString("serialnumber");
        String droneType = o.getString("dronetype");
        int droneTypeId = Util.cut_id(droneType);
        int carriageWeight = o.getInt("carriage_weight");
        String carriageType = o.getString("carriage_type");

        return new Drone(id,droneTypeId,created,serialNumber,carriageWeight,carriageType);
    }

    public void calculateTotalDistance(int offset) {
        if(offset == 0){
            totalDistanceTravelled = 0.0;
        }else{
            double lon1 = droneDynamicsList.get(offset - 1).getLongitude();
            double lat1 = droneDynamicsList.get(offset -1).getLatitude();
            double lon2 = droneDynamicsList.get(offset).getLongitude();
            double lat2 = droneDynamicsList.get(offset).getLatitude();

            double distance = haversine(lon1, lat1, lon2, lat2);
            totalDistanceTravelled += distance;
        }
    }

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

    public double calculateAverageSpeedDistanceTime() {
        if (droneDynamicsList.size() < 2) {
            return droneDynamicsList.getFirst().getSpeed();
        }
        DroneDynamics first = droneDynamicsList.getFirst();
        DroneDynamics last = droneDynamicsList.getLast();
        long seconds = java.time.Duration.between(first.getTimestamp(), last.getTimestamp()).getSeconds(); //SEKUNDEN
        double totalHours = seconds / 3600.0;
        if (totalHours == 0) {
            return 0.0;
        }
        return totalDistanceTravelled / totalHours;
    }
}


