/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getEnumerated() method
 * @run main DerInputStreamWitupTest_P45bb21279dbb
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerInputStreamWitupTest_P45bb21279dbb {
    public static void main(String[] args) throws Exception {
        testValidEnumerated();
        testInvalidTag();
    }

    private static void testValidEnumerated() throws IOException {
        // DER encoding for Enumerated with value 5
        // Tag = 0x0A (tag_Enumerated), Length = 1, Value = 5
        byte[] data = new byte[] { 0x0A, 0x01, 0x05 };
        DerInputStream der = new DerInputStream(data);
        int val = der.getEnumerated();
        if (val != 5) {
            throw new RuntimeException("Expected enumerated value 5 but got " + val);
        }
    }

    private static void testInvalidTag() {
        // Data with invalid tag (0x02 = INTEGER) instead of Enumerated (0x0A)
        byte[] data = new byte[] { 0x02, 0x01, 0x05 };
        try {
            DerInputStream der = new DerInputStream(data);
            der.getEnumerated();
            throw new RuntimeException("Expected IOException due to tag error but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Enumerated tag error")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
