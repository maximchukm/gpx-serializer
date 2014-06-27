package mobi.efarmer.gpx;

import mobi.efarmer.gpx.annotation.Altitude;
import mobi.efarmer.gpx.annotation.Direction;
import mobi.efarmer.gpx.annotation.Time;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author Maxim Maximchuk
 *         date 24-Jun-14.
 */
@Root(name = "trkpt", strict = false)
public class TrackPoint extends AbstractPoint {

    @Element(name = "time", required = false)
    private Date time;

    @Element(name = "magvar", required = false)
    private Float direction;

    @Element(name = "geoidheight", required = false)
    private Double height;

    public TrackPoint() {
        super();
    }

    public <T> TrackPoint(T point) {
        super(point);
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    protected <T> void extractFieldValue(T point, Field field) throws IllegalAccessException {
        if (valueToPoint(Time.class, field, point, time)) return;
        if (valueToPoint(Direction.class, field, point, direction)) return;
        if (valueToPoint(Altitude.class, field, point, height)) return;
    }

    @Override
    protected <T> void putFieldValue(T point, Field field) throws IllegalAccessException {
        Date tm = (Date) valueFromPoint(Time.class, field, point);
        if (tm != null) {
            time = tm;
            return;
        }
        Float dir = (Float) valueFromPoint(Direction.class, field, point);
        if (dir != null) {
            direction = dir;
            return;
        }
        Double hg = (Double) valueFromPoint(Altitude.class, field, point);
        if (hg != null) {
            height = hg;
            return;
        }
    }

}
