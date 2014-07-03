package mobi.efarmer.gpx;

import mobi.efarmer.gpx.annotation.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author Maxim Maximchuk
 *         date 25-Jun-14.
 */
public abstract class AbstractPoint {

    @Attribute(name = "lat")
    private Double latitude;

    @Attribute(name = "lon")
    private Double longitude;

    @Element(name = "time", required = false)
    private Date time;

    @Element(name = "magvar", required = false)
    private Double direction;

    @Element(name = "geoidheight", required = false)
    private Double height;

    @Element(name = "sat", required = false)
    private Integer sattelites;

    protected AbstractPoint() {
        super();
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getSattelites() {
        return sattelites;
    }

    public void setSattelites(Integer sattelites) {
        this.sattelites = sattelites;
    }

    protected <T> AbstractPoint(T point) {
        try {
            for (Field field : point.getClass().getDeclaredFields()) {
                Double lat = (Double) valueFromPoint(Latitude.class, field, point);
                if (lat != null) {
                    setLatitude(lat);
                    continue;
                }
                Double lon = (Double) valueFromPoint(Longitude.class, field, point);
                if (lon != null) {
                    setLongitude(lon);
                    continue;
                }
                Date tm = (Date) valueFromPoint(Time.class, field, point);
                if (tm != null) {
                    setTime(tm);
                    continue;
                }
                Double dir = (Double) valueFromPoint(Direction.class, field, point);
                if (dir != null) {
                    setDirection(dir);
                    continue;
                }
                Double hg = (Double) valueFromPoint(Altitude.class, field, point);
                if (hg != null) {
                    setHeight(hg);
                    continue;
                }
                Integer sat = (Integer) valueFromPoint(Sattelites.class, field, point);
                if (sat != null) {
                    setSattelites(sat);
                    continue;
                }
                putFieldValue(point, field);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

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

    protected static <T, I extends AbstractPoint> I buildPoint(I instance, T point) {
        return instance;
    }

    protected <T> T extractPoint(Class<T> pointClass) {
        T point = null;
        try {
            point = pointClass.newInstance();
            for (Field field: pointClass.getDeclaredFields()) {
                if (valueToPoint(Latitude.class, field, point, latitude)) continue;
                if (valueToPoint(Longitude.class, field, point, longitude)) continue;
                if (valueToPoint(Time.class, field, point, time)) continue;
                if (valueToPoint(Direction.class, field, point, direction)) continue;
                if (valueToPoint(Altitude.class, field, point, height)) continue;
                extractFieldValue(point, field);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return point;
    }

    protected <T> Object valueFromPoint(Class<? extends Annotation> annotationClass, Field field, T point) throws IllegalAccessException {
        Object value = null;
        if (field.isAnnotationPresent(annotationClass)) {
            field.setAccessible(true);
            value = field.get(point);
        }
        return value;
    }

    protected <T> boolean valueToPoint(Class<? extends Annotation> annotationClass, Field field, T point, Object value) throws IllegalAccessException {
        if (field.isAnnotationPresent(annotationClass)) {
            field.setAccessible(true);
            field.set(point, value);
            return true;
        }
        return false;
    }

    protected abstract <T> void extractFieldValue(T point, Field field) throws IllegalAccessException;

    protected abstract <T> void putFieldValue(T point, Field field) throws IllegalAccessException;

}
