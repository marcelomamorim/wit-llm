/*
 * @test
 * @summary Verifies DerInputStream.getEnumerated() returns the decoded enumerated value for valid ENUMERATED encodings and throws IOException when the tag is not ENUMERATED
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        checkEquals(5, new DerInputStream(new byte[]{0x0A, 0x01, 0x05}).getEnumerated(), "enumerated 5");
        checkEquals(127, new DerInputStream(new byte[]{0x0A, 0x01, 0x7F}).getEnumerated(), "enumerated 127");
        checkEquals(0, new DerInputStream(new byte[]{0x0A, 0x01, 0x00}).getEnumerated(), "enumerated 0");

        expectIOException(new byte[]{0x02, 0x01, 0x05}, "INTEGER tag rejected");
        expectIOException(new byte[]{0x04, 0x01, 0x05}, "OCTET STRING tag rejected");
        expectIOException(new byte[]{0x30, 0x00}, "SEQUENCE tag rejected");
    }

    private static void checkEquals(int expected, int actual, String what) {
        if (expected != actual) {
            throw new RuntimeException(what + ": expected " + expected + " but was " + actual);
        }
    }

    private static void expectIOException(byte[] data, String what) throws Exception {
        DerInputStream in = new DerInputStream(data);
        try {
            int value = in.getEnumerated();
            throw new RuntimeException(what + ": expected IOException but returned " + value);
        } catch (IOException expected) {
            // expected
        }
    }
}
