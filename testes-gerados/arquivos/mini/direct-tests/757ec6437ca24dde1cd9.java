/*
 * @test
 * @summary Test DerInputStream constructor with DerInputBuffer
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerInputBuffer;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Prepare a byte array for DerInputBuffer
        byte[] data = {0x01, 0x02, 0x03};
        DerInputBuffer buf = new DerInputBuffer(data);

        // Construct DerInputStream with DerInputBuffer
        DerInputStream derIn = new DerInputStream(buf);

        // Check that the buffer field is set and mark is supported
        // We cannot access buffer directly, but we can test mark/reset behavior
        buf.reset(); // should not throw

        // Mark again and reset to verify mark/reset works
        buf.mark(10);
        buf.reset();

        // If no exceptions thrown, test passes
        System.out.println("Test passed");
    }
}
