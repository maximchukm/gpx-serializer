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
            point1.setDirection(23f);
            point1.setHeight(143.2233f);

            TrackPoint point2 = new TrackPoint();
            point2.setLatitude(46.43553);
            point2.setLongitude(42.32345);
            point2.setTime(new Date());
            point2.setDirection(22f);
            point2.setHeight(149.2233f);

            TrackPoint point3 = new TrackPoint();
            point3.setLatitude(48.43553);
            point3.setLongitude(42.32345);
            point3.setTime(new Date());
            point3.setDirection(21f);
            point3.setHeight(150.2233f);

            List<TrackPoint> points = new ArrayList<TrackPoint>();
            points.add(point1);
            points.add(point2);
            points.add(point3);

            gpx.putTrackPoints(points);
            OutputStream os = new FileOutputStream("test.gpx");
            os.write(gpx.write());
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
            TestPoint point1 = new TestPoint();
            point1.setLatitude(43.43553);
            point1.setLongitude(42.32345);
            point1.setTime(new Date());
            point1.setDirection(23f);
            point1.setHeight(143.2233f);

            TestPoint point2 = new TestPoint();
            point2.setLatitude(46.43553);
            point2.setLongitude(42.32345);
            point2.setTime(new Date());
            point2.setDirection(22f);
            point2.setHeight(149.2233f);

            TestPoint point3 = new TestPoint();
            point3.setLatitude(48.43553);
            point3.setLongitude(42.32345);
            point3.setTime(new Date());
            point3.setDirection(21f);
            point3.setHeight(150.2233f);

            List<TestPoint> points = new ArrayList<TestPoint>();
            points.add(point1);
            points.add(point2);
            points.add(point3);

            gpx.putTrackPoints(points);
            OutputStream os = new FileOutputStream("test.gpx");
            os.write(gpx.write());
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