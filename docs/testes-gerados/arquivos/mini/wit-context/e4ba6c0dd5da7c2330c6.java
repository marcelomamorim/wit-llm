/*
 * @test
 * @summary Test sun.security.x509.OtherName.subtreeDepth() throws UnsupportedOperationException
 * @run main OtherNameWitupTest
 */

import sun.security.x509.OtherName;

public class OtherNameWitupTest {
    public static void main(String[] args) throws Exception {
        OtherName on = new OtherName() {
            @Override
            public int subtreeDepth() {
                return super.subtreeDepth();
            }
        };

        boolean thrown = false;
        try {
            on.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth() not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        }
    }
}
