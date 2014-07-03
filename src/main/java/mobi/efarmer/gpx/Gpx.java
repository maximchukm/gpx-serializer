package mobi.efarmer.gpx;

import mobi.efarmer.gpx.transform.GpxDateTransform;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Maxim Maximchuk
 *         date 24-Jun-14.
 */
@Root(strict = false)
@NamespaceList(value = {
    @Namespace(reference = "http://www.topografix.com/GPX/1/1"),
    @Namespace(prefix = "ef", reference = "http://efarmer.mobi/GPX/1/0")
})
public class Gpx {

    @Version(revision=1.1)
    private double version;

    @Attribute(required = false)
    private String creator;

    @ElementList(name = "trk", inline = true)
    private List<Track> tracks;

    private static Persister persister;

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
        return getPersister().read(Gpx.class, gpxXml);
    }

    public byte[] serialize() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        getPersister().write(this, os);
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

    private static Persister getPersister() {
        if (persister == null) {
            RegistryMatcher m = new RegistryMatcher();
            m.bind(Date.class, new GpxDateTransform());
            persister = new Persister(m);
        }
        return persister;
    }

}
