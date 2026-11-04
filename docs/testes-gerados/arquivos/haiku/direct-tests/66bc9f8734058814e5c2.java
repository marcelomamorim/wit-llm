/*
 * @test
 * @summary Verify DerInputStream.getSet tag validation and basic set parsing
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWrongTagThrows();
        testEmptySet();
        testSetWithOneInteger();
        testSetTagStoredInStreamField();
    }

    static void testWrongTagThrows() throws Exception {
        byte[] data = {0x30, 0x00};
        DerInputStream in = new DerInputStream(data);
        boolean thrown = false;
        try {
            in.getSet(4);
        } catch (IOException e) {
            thrown = true;
            if (!"Set tag error".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-SET tag");
        }
    }

    static void testEmptySet() throws Exception {
        byte[] data = {0x31, 0x00};
        DerInputStream in = new DerInputStream(data);
        DerValue[] values = in.getSet(4);
        if (values == null || values.length != 0) {
            throw new RuntimeException("Expected empty array for empty SET");
        }
    }

    static void testSetWithOneInteger() throws Exception {
        byte[] data = {0x31, 0x03, 0x02, 0x01, 0x05};
        DerInputStream in = new DerInputStream(data);
        DerValue[] values = in.getSet(4);
        if (values == null || values.length != 1) {
            throw new RuntimeException("Expected one element in SET");
        }
        if (values[0].tag != 0x02) {
            throw new RuntimeException("Expected INTEGER tag, got " + values[0].tag);
        }
    }

    static void testSetTagStoredInStreamField() throws Exception {
        byte[] data = {0x31, 0x00};
        DerInputStream in = new DerInputStream(data);
        in.getSet(0);
        if (in.tag != 0x31) {
            throw new RuntimeException("Expected stream tag 0x31, got " + in.tag);
        }
    }
}
