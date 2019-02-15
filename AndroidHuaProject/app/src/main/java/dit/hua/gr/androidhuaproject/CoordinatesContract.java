package dit.hua.gr.androidhuaproject;

public class CoordinatesContract {
    private String user_id;
    private String longitude;
    private String latitude;
    private String dt;

    public CoordinatesContract(String user_id, String longitude, String latitude, String dt) {
        this.user_id = user_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dt = dt;
    }

    public String getUser_Id() {
        return user_id;
    }

    public void setUser_Id(String uid) {
        this.user_id = uid;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }
}
