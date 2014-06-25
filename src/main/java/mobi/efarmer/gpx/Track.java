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

    public void setSegments(List<TrackSegment> segments) {
        this.segments = segments;
    }

    public Track addSegment(TrackSegment segment) {
        getSegments().add(segment);
        return this;
    }

}
