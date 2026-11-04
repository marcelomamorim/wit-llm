/*
 * @test
 * @summary Witup-derived checks for DerValue.getUTCTime tag validation and valid UTCTime decoding
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testNonUtcTimeTagThrowsIOException();
        testUtcTimeTagDecodesValidValue();
    }

    static void testNonUtcTimeTagThrowsIOException() throws Exception {
        DerValue value = new DerValue(DerValue.tag_Integer, new byte[] {0x01});
        boolean thrown = false;
        try {
            value.getUTCTime();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-UtcTime tag");
        }
    }

    static void testUtcTimeTagDecodesValidValue() throws Exception {
        byte[] content = "130101000000Z".getBytes(StandardCharsets.US_ASCII);
        DerValue value = new DerValue(DerValue.tag_UtcTime, content);
        Date date = value.getUTCTime();
        if (date == null) {
            throw new RuntimeException("getUTCTime returned null for valid UtcTime");
        }
        Date expected = new Date(Date.UTC(113, 0, 1, 0, 0, 0));
        if (date.getTime() != expected.getTime()) {
            throw new RuntimeException("Unexpected UTCTime value: " + date.getTime()
                    + " expected " + expected.getTime());
        }
    }
}
