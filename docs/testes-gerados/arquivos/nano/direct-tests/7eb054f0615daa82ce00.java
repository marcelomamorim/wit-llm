/* @test    @summary Test the encode method of sun.security.x509.Extension for various scenarios    @run main ExtensionWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.ObjectIdentifier;
import sun.security.x509.Extension;

public class ExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidData();
        testEncodeWithNullOID();
        testEncodeWithNullValue();
        testEncodeCriticalFlag();
    }

    static void testEncodeWithValidData() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] value = {0x01, 0x02, 0x03};
        Extension ext = new Extension(oid, true, value);
        DerOutputStream outStream = new DerOutputStream();
        ext.encode(outStream);
        byte[] encoded = outStream.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding with valid data failed: output is null or empty");
        }
    }

    static void testEncodeWithNullOID() {
        try {
            Extension ext = new Extension();
            ext.extensionId = null;
            ext.extensionValue = new byte[]{0x01};
            DerOutputStream outStream = new DerOutputStream();
            ext.encode(outStream);
            throw new AssertionError("Expected IOException for null OID not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    static void testEncodeWithNullValue() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        Extension ext = new Extension();
        ext.extensionId = oid;
        ext.extensionValue = null;
        DerOutputStream outStream = new DerOutputStream();
        try {
            ext.encode(outStream);
            throw new AssertionError("Expected IOException for null extensionValue not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    static void testEncodeCriticalFlag() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] value = {0x0A, 0x0B};
        Extension ext = new Extension(oid, false, value);
        DerOutputStream outStream = new DerOutputStream();
        ext.encode(outStream);
        byte[] encoded = outStream.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding with critical flag false failed: output is null or empty");
        }
    }
}
