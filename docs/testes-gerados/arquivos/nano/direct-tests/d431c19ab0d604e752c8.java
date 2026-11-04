/* @test    @summary Test the encode method of sun.security.x509.Extension for correct DER encoding.    @run main ExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.Extension;

public class ExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeCriticalExtension();
        testEncodeNonCriticalExtension();
        testEncodeNullOutputStream();
    }

    static void testEncodeCriticalExtension() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        boolean critical = true;
        byte[] value = {0x01, 0x02, 0x03};
        Extension ext = new Extension(oid, critical, value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        // Basic check: encoded should contain the OID and the value
        String encodedHex = bytesToHex(encoded);
        assert encodedHex.contains(bytesToHex(oid.getOIDBytes())) : "OID not found in encoding";
        assert encodedHex.contains(bytesToHex(value)) : "Value not found in encoding";
        assert encodedHex.contains("01") : "Critical boolean not encoded";
    }

    static void testEncodeNonCriticalExtension() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.29.19");
        boolean critical = false;
        byte[] value = {0x0A, 0x0B};
        Extension ext = new Extension(oid, critical, value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        String encodedHex = bytesToHex(encoded);
        assert encodedHex.contains(bytesToHex(oid.getOIDBytes())) : "OID not found in encoding";
        assert encodedHex.contains(bytesToHex(value)) : "Value not found in encoding";
        assert !encodedHex.contains("01") : "Critical boolean should not be encoded";
    }

    static void testEncodeNullOutputStream() {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549");
        byte[] value = {0x00};
        Extension ext;
        try {
            ext = new Extension(oid, false, value);
            ext.encode(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
    }

    static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
