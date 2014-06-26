package mobi.efarmer.gpx;

import mobi.efarmer.gpx.annotations.*;

import java.util.Date;

/**
 * @author Maxim Maximchuk
 *         date 25-Jun-14.
 */
public class TestPoint {

    @Lat
    private Double latitude;

    @Lon
    private Double longitude;

    @Altitude
    private Float height;

    @Time
    private Date time;

    @Direction
    private Float direction;

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

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Float getDirection() {
        return direction;
    }

    public void setDirection(Float direction) {
        this.direction = direction;
    }
}
