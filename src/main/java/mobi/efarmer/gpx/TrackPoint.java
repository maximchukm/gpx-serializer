package mobi.efarmer.gpx;

import mobi.efarmer.gpx.annotation.Speed;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.lang.reflect.Field;

/**
 * @author Maxim Maximchuk
 *         date 24-Jun-14.
 */
@Root(name = "trkpt", strict = false)
public class TrackPoint extends AbstractPoint {

    @Path("extensions")
    @Namespace(prefix = "ef", reference = "http://efarmer.mobi/GPX/1/0")
    @Element(name = "speed", required = false)
    private Double speed;

    public TrackPoint() {
        super();
    }

    public <T> TrackPoint(T point) {
        super(point);
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    protected <T> void extractFieldValue(T point, Field field) throws IllegalAccessException {
        if (valueToPoint(Speed.class, field, point, speed)) return;
    }

    @Override
    protected <T> void putFieldValue(T point, Field field) throws IllegalAccessException {
        Double sp = (Double) valueFromPoint(Speed.class, field, point);
        if (sp != null) {
            speed = sp;
        }
    }

}
