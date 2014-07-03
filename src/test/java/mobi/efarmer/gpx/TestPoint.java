package mobi.efarmer.gpx;

import mobi.efarmer.gpx.annotation.*;

import java.util.Date;

/**
 * @author Maxim Maximchuk
 *         date 25-Jun-14.
 */
public class TestPoint {

    @Latitude
    private Double latitude;

    @Longitude
    private Double longitude;

    @Altitude
    private Double height;

    @Time
    private Date time;

    @Direction
    private Double direction;

    @Speed
    private Double speed;

    @Sattelites
    private Integer sattelites;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getDirection() {
        return direction;
    }

    public void setDirection(Double direction) {
        this.direction = direction;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getSattelites() {
        return sattelites;
    }

    public void setSattelites(Integer sattelites) {
        this.sattelites = sattelites;
    }
}
