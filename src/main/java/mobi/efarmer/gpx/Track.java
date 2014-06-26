package mobi.efarmer.gpx;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Maximchuk
 *         date 24-Jun-14.
 */
@Root(name = "trk", strict = false)
public class Track {

    @Element(required = false)
    private String name;

    @Element(required = false)
    private String src;

    @Element(required = false)
    private String type;

    @ElementList(name = "trkseg", inline = true)
    private List<TrackSegment> segments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TrackSegment> getSegments() {
        if (segments == null) {
            segments = new ArrayList<TrackSegment>();
        }
        return segments;
    }


    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSegments(List<TrackSegment> segments) {
        this.segments = segments;
    }

    public List<TrackPoint> getPoints() {
        List<TrackPoint> points = new ArrayList<TrackPoint>();
        for (TrackSegment segment : getSegments()) {
            points.addAll(segment.getPoints());
        }
        return points;
    }

    public <T> List<T> getPoints(Class<T> pointClass) {
        List<T> points = new ArrayList<T>();
        for (TrackSegment segment : getSegments()) {
            for (TrackPoint trackPoint : segment.getPoints()) {
                T point = trackPoint.extractPoint(pointClass);
                points.add(point);
            }
        }
        return points;
    }

    public static class Builder {

        private Gpx gpx;
        private Track track;

        protected Builder(Gpx gpx) {
            this.gpx = gpx;
            track = new Track();
        }

        protected <T> Builder(Gpx gpx, List<T> trackPoints) {
            this(gpx);
            newSegment(trackPoints).commitSegment();
        }

        public void commitTrack() {
            gpx.addTrack(track);
        }

        public TrackSegment.Builder newSegment() {
            return new TrackSegment.Builder(track);
        }

        public <T> TrackSegment.Builder newSegment(List<T> trackPoints) {
            return new TrackSegment.Builder(track, trackPoints);
        }

        public Builder name(String name) {
            track.setName(name);
            return this;
        }

        public Builder src(String src) {
            track.setSrc(src);
            return this;
        }

        public Builder type(String type) {
            track.setType(type);
            return this;
        }

    }

}
