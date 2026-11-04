/*
 * @test
 * @summary Verify DerInputStream.getOctetString parsing of DER octet strings and error cases
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSimpleOctetString();
        testEmptyOctetString();
        testWrongTagThrowsIOException();
        testShortReadThrowsIOException();
    }

    static void testSimpleOctetString() throws Exception {
        byte[] data = {0x04, 0x02, 0x01, 0x02};
        DerInputStream in = new DerInputStream(data);
        byte[] result = in.getOctetString();
        if (result.length != 2 || result[0] != 0x01 || result[1] != 0x02) {
            throw new RuntimeException("Unexpected octet string content");
        }
    }

    static void testEmptyOctetString() throws Exception {
        byte[] data = {0x04, 0x00};
        DerInputStream in = new DerInputStream(data);
        byte[] result = in.getOctetString();
        if (result == null || result.length != 0) {
            throw new RuntimeException("Expected empty octet string");
        }
    }

    static void testWrongTagThrowsIOException() throws Exception {
        byte[] data = {0x02, 0x01, 0x00};
        DerInputStream in = new DerInputStream(data);
        try {
            in.getOctetString();
            throw new RuntimeException("Expected IOException for wrong tag");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testShortReadThrowsIOException() throws Exception {
        byte[] data = {0x04, 0x03, 0x01};
        DerInputStream in = new DerInputStream(data);
        try {
            in.getOctetString();
            throw new RuntimeException("Expected IOException for short read");
        } catch (IOException expected) {
            // expected
        }
    }
}
