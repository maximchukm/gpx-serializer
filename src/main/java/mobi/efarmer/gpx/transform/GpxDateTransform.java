package mobi.efarmer.gpx.transform;

import org.simpleframework.xml.transform.Transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Maxim Maximchuk
 *         date 03-Jul-14.
 */
public class GpxDateTransform implements Transform<Date> {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S z";
    private static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    @Override
    public Date read(String value) throws Exception {
        Date date;
        try {
            date = new SimpleDateFormat(ISO_8601_DATE_FORMAT).parse(value);
        } catch (ParseException e) {
            date = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(value);
        }
        return date;
    }

    @Override
    public String write(Date value) throws Exception {
        return new SimpleDateFormat(ISO_8601_DATE_FORMAT).format(value);
    }
}
