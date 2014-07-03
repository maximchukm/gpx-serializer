package mobi.efarmer.gpx;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GpxTest {

    private Gpx gpx;

    @Before
    public void setUp() throws Exception {
        gpx = Gpx.read(getClass().getClassLoader().getResourceAsStream("track.gpx"));
    }

    @After
    public void tearDown() throws Exception {
        File f = new File("test.gpx");
        if (f.exists()) {
            f.delete();
        }
    }

    @Test
    public void testGetTrackPoints() {
        checkGpx(gpx);
    }

    @Test
    public void testGetCustomTrackPoints() {
        try {
            List<TestPoint> testPoints = gpx.getTrackPoints(TestPoint.class);
            Assert.assertFalse(testPoints.isEmpty());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteTrack() {
        try {
            Gpx gpx = new Gpx("testWriteTrack");
            TrackPoint point1 = new TrackPoint();
            point1.setLatitude(43.43553);
            point1.setLongitude(42.32345);
            point1.setTime(new Date());
            point1.setDirection(23d);
            point1.setHeight(143.2233);
            point1.setSpeed(45d);

            TrackPoint point2 = new TrackPoint();
            point2.setLatitude(46.43553);
            point2.setLongitude(42.32345);
            point2.setTime(new Date());
            point2.setDirection(22d);
            point2.setHeight(149.2233);

            TrackPoint point3 = new TrackPoint();
            point3.setLatitude(48.43553);
            point3.setLongitude(42.32345);
            point3.setTime(new Date());
            point3.setDirection(21d);
            point3.setHeight(150.2233);

            List<TrackPoint> points = new ArrayList<TrackPoint>();
            points.add(point1);
            points.add(point2);
            points.add(point3);

            gpx.newTrack(points).name("Test track").src("JUnit test").commitTrack();

            OutputStream os = new FileOutputStream("test.gpx");
            os.write(gpx.serialize());
            os.close();

            checkGpx(Gpx.read(new File("test.gpx")));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteCustomPoint() {
        try {
            Gpx gpx = new Gpx("testWriteCustomPoint");
            Track.Builder trackBuilder = gpx.newTrack().name("Test track").src("JUnit test");
            TrackSegment.Builder segmentBuilder = trackBuilder.newSegment();

            TestPoint point1 = new TestPoint();
            point1.setLatitude(43.43553);
            point1.setLongitude(42.32345);
            point1.setTime(new Date());
            point1.setDirection(23d);
            point1.setHeight(143.2233);
            point1.setSpeed(65d);
            point1.setSattelites(6);

            segmentBuilder.addPoint(point1);

            TestPoint point2 = new TestPoint();
            point2.setLatitude(46.43553);
            point2.setLongitude(42.32345);
            point2.setTime(new Date());
            point2.setDirection(22d);
            point2.setHeight(149.2233);
            point2.setSattelites(4);

            segmentBuilder.addPoint(point2);

            TestPoint point3 = new TestPoint();
            point3.setLatitude(48.43553);
            point3.setLongitude(42.32345);
            point3.setTime(new Date());
            point3.setDirection(21d);
            point3.setHeight(150.2233);
            point3.setSpeed(45d);
            point3.setSattelites(5);

            segmentBuilder.addPoint(point3);

            segmentBuilder.commitSegment();
            trackBuilder.commitTrack();

            OutputStream os = new FileOutputStream("test.gpx");
            os.write(gpx.serialize());
            os.close();

            checkGpx(Gpx.read(new File("test.gpx")));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testAddSegmentWithPointList() {
        try {
            Gpx gpx = new Gpx("testWriteTrack");
            Track.Builder builder = gpx.newTrack();

            TrackPoint point1 = new TrackPoint();
            point1.setLatitude(43.43553);
            point1.setLongitude(42.32345);
            point1.setTime(new Date());
            point1.setDirection(23d);
            point1.setHeight(143.2233);

            TrackPoint point2 = new TrackPoint();
            point2.setLatitude(46.43553);
            point2.setLongitude(42.32345);
            point2.setTime(new Date());
            point2.setDirection(22d);
            point2.setHeight(149.2233);

            TrackPoint point3 = new TrackPoint();
            point3.setLatitude(48.43553);
            point3.setLongitude(42.32345);
            point3.setTime(new Date());
            point3.setDirection(21d);
            point3.setHeight(150.2233);

            List<TrackPoint> points = new ArrayList<TrackPoint>();
            points.add(point1);
            points.add(point2);
            points.add(point3);

            builder.newSegment(points).commitSegment();
            builder.name("Test track").src("JUnit test").commitTrack();

            OutputStream os = new FileOutputStream("test.gpx");
            os.write(gpx.serialize());
            os.close();

            checkGpx(Gpx.read(new File("test.gpx")));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    private void checkGpx(Gpx gpx) {
        try {
            List<TrackPoint> testPoints = gpx.getTrackPoints();
            Assert.assertFalse(testPoints.isEmpty());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
}