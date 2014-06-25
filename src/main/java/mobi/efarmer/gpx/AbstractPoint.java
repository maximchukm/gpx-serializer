package mobi.efarmer.gpx;

import mobi.efarmer.gpx.annotations.Lat;
import mobi.efarmer.gpx.annotations.Lon;
import org.simpleframework.xml.Attribute;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Maxim Maximchuk
 *         date 25-Jun-14.
 */
public abstract class AbstractPoint {

    @Attribute(name = "lat")
    private Double latitude;

    @Attribute(name = "lon")
    private Double longitude;

    protected AbstractPoint() {
        super();
    }

    protected <T> AbstractPoint(T point) {
        try {
            for (Field field : point.getClass().getDeclaredFields()) {
                Double lat = (Double) valueFromPoint(Lat.class, field, point);
                if (lat != null) {
                    setLatitude(lat);
                    continue;
                }
                Double lon = (Double) valueFromPoint(Lon.class, field, point);
                if (lon != null) {
                    setLongitude(lon);
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
                if (valueToPoint(Lat.class, field, point, latitude)) continue;
                if (valueToPoint(Lon.class, field, point, longitude)) continue;
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
