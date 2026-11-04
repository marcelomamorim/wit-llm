/* @test    @summary Test the subtreeDepth method of sun.security.x509.OtherName for generic case    @run main sun.security.x509.OtherNameWitupTest */

import sun.security.x509.OtherName;
import java.io.IOException;

public class OtherNameWitupTest {

    public static void main(String[] args) throws IOException {
        testSubtreeDepthThrows();
    }

    public static void testSubtreeDepthThrows() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
            byte[] value = {0x05, 0x00};
            OtherName otherName = new OtherName(oid, value);
            int result = otherName.subtreeDepth();
            if (result != 0) {
                throw new AssertionError("Expected subtreeDepth to throw UnsupportedOperationException, but got: " + result);
            }
            throw new AssertionError("subtreeDepth() did not throw as expected");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
