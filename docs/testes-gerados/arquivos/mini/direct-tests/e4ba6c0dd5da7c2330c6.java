/*
 * @test
 * @summary Test that OtherName.subtreeDepth() throws UnsupportedOperationException
 * @run main sun.security.x509.OtherNameWitupTest
 */

public class OtherNameWitupTest {
    public static void main(String[] args) {
        sun.security.x509.OtherName otherName = new sun.security.x509.OtherNameStub();
        try {
            otherName.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth() not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}

// Stub class to allow instantiation without full constructor dependencies
class OtherNameStub extends sun.security.x509.OtherName {
    OtherNameStub() {
        super(new sun.security.util.ObjectIdentifier(new int[] {1,2,3}), new byte[] {0});
    }

    @Override
    public int subtreeDepth() {
        return super.subtreeDepth();
    }
}
