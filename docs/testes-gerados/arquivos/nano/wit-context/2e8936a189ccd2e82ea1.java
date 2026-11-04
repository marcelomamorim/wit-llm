/* @test    @summary Test DerInputStream.getOID() for expected behavior and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidGetOID();
        testGetOIDThrowsIOExceptionOnRead();
        testGetOIDThrowsIOExceptionOnConstruct();
    }

    private static void testValidGetOID() throws Exception {
        byte[] data = {0x06, 0x03, 0x55, 0x04, 0x03}; // DER encoding for OID 1.3.14.3
        DerInputStream dis = new DerInputStream(data);
        ObjectIdentifier oid = dis.getOID();
        if (oid == null) {
            throw new AssertionError("Expected non-null ObjectIdentifier");
        }
        String oidStr = oid.toString();
        if (!"2.5.4.3".equals(oidStr)) {
            throw new AssertionError("Unexpected OID string: " + oidStr);
        }
    }

    private static void testGetOIDThrowsIOExceptionOnRead() {
        byte[] data = {(byte)0x80, 0x01, 0x01}; // Invalid DER tag
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getOID();
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testGetOIDThrowsIOExceptionOnConstruct() {
        byte[] data = {(byte)0x06, 0x02, 0x55}; // Incomplete length
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getOID();
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
