/*
 * @test
 * @summary Verifies that OtherName.subtreeDepth() throws UnsupportedOperationException for a generic OtherName
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main OtherNameWitupTest
 */

import java.io.IOException;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.OtherName;

public class OtherNameWitupTest {

    public static void main(String[] args) throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.3.6.1.4.1.311.20.2.3");
        byte[] value = {0x0C, 0x01, 0x41};
        OtherName name = new OtherName(oid, value);
        try {
            name.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth")) {
                throw new RuntimeException("unexpected message: " + msg);
            }
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
