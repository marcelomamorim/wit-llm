/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getSet(int) for correct exception and normal behavior
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetSetThrowsOnWrongTag();
        testGetSetNormalBehavior();
    }

    static void testGetSetThrowsOnWrongTag() {
        // Prepare input with first byte != DerValue.tag_Set
        // DerValue.tag_Set is public static final byte, use it
        byte wrongTag = (byte)(DerValue.tag_Set + 1); // definitely not tag_Set
        byte[] data = new byte[] { wrongTag, 0x00 };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getSet(0);
            throw new RuntimeException("Expected IOException not thrown for wrong tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Set tag error")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    static void testGetSetNormalBehavior() throws IOException {
        // Prepare input with first byte == DerValue.tag_Set
        // and minimal valid data to allow readVector to proceed
        // Since readVector is not detailed here, we test that getSet returns non-null array
        byte[] data = new byte[] { DerValue.tag_Set, 0x00 };
        DerInputStream dis = new DerInputStream(data);
        DerValue[] set = dis.getSet(0);
        if (set == null) {
            throw new RuntimeException("getSet returned null array");
        }
    }
}
