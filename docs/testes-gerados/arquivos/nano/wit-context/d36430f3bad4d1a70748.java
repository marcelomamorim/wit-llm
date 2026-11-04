/* @test    @summary Test for sun.security.x509.AVA.encode method behavior.    @run main AVAWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.AVA;
import sun.security.util.ObjectIdentifier;

public class AVAWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeNormal();
        testEncodeWithInvalidOffset();
    }

    public static void testEncodeNormal() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        byte[] valueBytes = {0x01, 0x02, 0x03};
        DerOutputStream derStream = new DerOutputStream();
        AVA ava = new AVA(oid, new sun.security.util.DerValue(false, valueBytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ava.encode(new DerOutputStream(baos));
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding produced empty byte array");
        }
        System.out.println("testEncodeNormal passed.");
    }

    public static void testEncodeWithInvalidOffset() {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        byte[] valueBytes = {0x01, 0x02, 0x03};
        try {
            DerOutputStream derStream = new DerOutputStream();
            AVA ava = new AVA(oid, new sun.security.util.DerValue(false, valueBytes));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // Simulate invalid offset by passing null DerOutputStream
            ava.encode(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            System.out.println("testEncodeWithInvalidOffset passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
