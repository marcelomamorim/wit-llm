/*
 * @test
 * @summary Verify that OtherName.subtreeDepth() throws UnsupportedOperationException for generic OtherName instances
 * @modules java.base/sun.security.util
 * @run main OtherNameWitupTest
 */

import java.io.IOException;

import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.OtherName;

public class OtherNameWitupTest {

    private static final byte[] ENCODED_OTHER_NAME = {
        0x30, 0x0A,
        0x06, 0x03, 0x2A, 0x03, 0x04,
        (byte) 0xA0, 0x03, 0x0C, 0x01, 0x41
    };

    public static void main(String[] args) throws Exception {
        testOidConstructorThrows();
        testDerConstructorThrows();
        testSubtreeDepthIsDeterministic();
    }

    private static void testOidConstructorThrows() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] value = {0x0C, 0x01, 0x41};
        OtherName name = new OtherName(oid, value);
        expectUnsupported(name);
    }

    private static void testDerConstructorThrows() throws IOException {
        DerValue dv = new DerValue(ENCODED_OTHER_NAME);
        OtherName name = new OtherName(dv);
        expectUnsupported(name);
    }

    private static void testSubtreeDepthIsDeterministic() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] value = {0x0C, 0x01, 0x41};
        OtherName name = new OtherName(oid, value);
        expectUnsupported(name);
        expectUnsupported(name);
    }

    private static void expectUnsupported(OtherName name) {
        boolean thrown = false;
        try {
            name.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from subtreeDepth()");
        }
    }
}
