package mobi.efarmer.gpx;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Maximchuk
 *         date 24-Jun-14.
 */
@Root(strict = false)
public class Gpx {

    @Attribute(required = false)
    private String xmlns = "http://www.topografix.com/GPX/1/1";

    @Attribute
    private String version = "1.1";

    @Attribute
    private String creator;

    @ElementList(name = "trk", inline = true)
    private List<Track> tracks;

    public Gpx() {
        super();
    }

    public Gpx(String creator) {
        this();
        this.creator = creator;
    }

    public static Gpx read(File gpxFile) throws Exception {
        Gpx gpx;
        InputStream is = new FileInputStream(gpxFile);
        try {
            gpx = read(is);
        } finally {
            is.close();
        }
        return gpx;
    }

    public static Gpx read(InputStream gpxStream) throws Exception {
        byte[] data = new byte[gpxStream.available()];
        gpxStream.read(data);
        return read(new String(data));
    }

    public static Gpx read(String gpxXml) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(Gpx.class, gpxXml);
    }

    public byte[] serialize() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        new Persister().write(this, os);
        os.flush();
        return os.toByteArray();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<Track> getTracks() {
        if (tracks == null) {
            tracks = new ArrayList<Track>();
        }
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<TrackPoint> getTrackPoints() throws Exception {
        List<TrackPoint> points = new ArrayList<TrackPoint>();
        for (Track track: tracks) {
            points.addAll(track.getPoints());
        }
        return points;
    }

    public <T> List<T> getTrackPoints(Class<T> pointClass) throws Exception {
        List<T> points = new ArrayList<T>();
        for (Track track: tracks) {
            points.addAll(track.getPoints(pointClass));
        }
        return points;
    }

    public void addTrack(Track track) {
        getTracks().add(track);
    }

    public Track.Builder newTrack() {
        return new Track.Builder(this);
    }

    public <T> Track.Builder newTrack(List<T> trackPoints) {
        return new Track.Builder(this, trackPoints);
    }

}
