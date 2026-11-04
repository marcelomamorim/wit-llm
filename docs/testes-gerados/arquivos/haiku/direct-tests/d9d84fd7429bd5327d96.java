/*
 * @test
 * @summary Verifies DerInputStream.getUTCTime() decodes valid UTCTime values and rejects an invalid tag
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    static byte[] utcTimeEncoding(int tag, String text) throws Exception {
        byte[] content = text.getBytes("US-ASCII");
        byte[] out = new byte[content.length + 2];
        out[0] = (byte) tag;
        out[1] = (byte) content.length;
        System.arraycopy(content, 0, out, 2, content.length);
        return out;
    }

    static long utcMillis(int year, int month, int day, int hour, int min, int sec) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        cal.clear();
        cal.set(year, month - 1, day, hour, min, sec);
        return cal.getTimeInMillis();
    }

    public static void main(String[] args) throws Exception {
        // tag 0x17 (UTCTime), YYMMDDHHMMSSZ
        byte[] t1 = utcTimeEncoding(0x17, "130101000000Z");
        Date d1 = new DerInputStream(t1).getUTCTime();
        long e1 = utcMillis(2013, 1, 1, 0, 0, 0);
        if (d1.getTime() != e1) {
            throw new RuntimeException("Expected " + e1 + " but got " + d1.getTime());
        }

        // YY >= 50 maps to 19YY
        byte[] t2 = utcTimeEncoding(0x17, "991231235959Z");
        Date d2 = new DerInputStream(t2).getUTCTime();
        long e2 = utcMillis(1999, 12, 31, 23, 59, 59);
        if (d2.getTime() != e2) {
            throw new RuntimeException("Expected " + e2 + " but got " + d2.getTime());
        }

        // GeneralizedTime tag (0x18) must be rejected by getUTCTime()
        byte[] bad = utcTimeEncoding(0x18, "130101000000Z");
        boolean threw = false;
        try {
            new DerInputStream(bad).getUTCTime();
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("IOException expected for invalid UTCTime tag");
        }
    }
}
