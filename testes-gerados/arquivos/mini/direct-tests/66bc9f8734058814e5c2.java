/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getSet(int) method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetSetWithValidSetTag();
        testGetSetWithInvalidTag();
    }

    private static void testGetSetWithValidSetTag() throws Exception {
        // Construct DER encoding of a SET with one INTEGER element
        // DER encoding: 31 03 02 01 05
        // 31 = tag_Set
        // 03 = length 3 bytes
        // 02 01 05 = INTEGER tag, length 1, value 5
        byte[] derSet = new byte[] { 0x31, 0x03, 0x02, 0x01, 0x05 };
        DerInputStream dis = new DerInputStream(derSet);
        DerValue[] values = dis.getSet(derSet.length);
        if (values == null || values.length != 1) {
            throw new RuntimeException("Expected one element in set");
        }
        DerValue val = values[0];
        if (val.tag != DerValue.tag_Integer) {
            throw new RuntimeException("Expected INTEGER tag in set element");
        }
        int intValue = val.getInteger();
        if (intValue != 5) {
            throw new RuntimeException("Expected integer value 5, got " + intValue);
        }
    }

    private static void testGetSetWithInvalidTag() throws Exception {
        // Construct DER encoding with invalid tag (not 0x31)
        byte[] invalidTag = new byte[] { 0x30, 0x00 };
        DerInputStream dis = new DerInputStream(invalidTag);
        try {
            dis.getSet(invalidTag.length);
            throw new RuntimeException("Expected IOException for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Set tag error".equals(msg)) {
                throw new RuntimeException("Expected 'Set tag error' message, got: " + msg);
            }
        }
    }
}
