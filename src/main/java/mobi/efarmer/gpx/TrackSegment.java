package mobi.efarmer.gpx;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Maximchuk
 *         date 24-Jun-14.
 */
@Root(name = "trkseg", strict = false)
public class TrackSegment {

    @ElementList(name = "trkpt", inline = true)
    private List<TrackPoint> points;

    public List<TrackPoint> getPoints() {
        if (points == null) {
            points = new ArrayList<TrackPoint>();
        }
        return points;
    }

    public void setPoints(List<TrackPoint> points) {
        this.points = points;
    }

    public void addPoint(TrackPoint point) {
        getPoints().add(point);
    }

    public <T> void addPoints(List<T> points) {
        if (points.get(0).getClass() == TrackPoint.class) {
            setPoints((List<TrackPoint>) points);
        } else {
            for (T point : points) {
                addPoint(new TrackPoint(point));
            }
        }
    }

    public static class Builder {

        private Track track;
        private TrackSegment segment;

        public Builder(Track track) {
            this.track = track;
            segment = new TrackSegment();
        }

        public <T> Builder(Track track, List<T> trackPoints) {
            this(track);
            segment.addPoints(trackPoints);
        }

        public TrackSegment commitSegment() {
            track.getSegments().add(segment);
            return segment;
        }

        public <T> Builder addPoint(T point) {
            if (point instanceof TrackPoint) {
                segment.addPoint((TrackPoint)point);
            } else {
                segment.addPoint(new TrackPoint(point));
            }
            return this;
        }

    }

}
