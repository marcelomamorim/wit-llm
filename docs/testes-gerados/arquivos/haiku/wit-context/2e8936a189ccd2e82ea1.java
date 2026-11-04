/*
 * @test
 * @summary Witup-derived checks for DerInputStream.getOID(): valid OID decoding and IOException on truncated or malformed DER input
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidOidReturnsNonNull();
        testTruncatedLengthThrowsIOException();
        testLengthExceedsAvailableDataThrowsIOException();
        testWrongTagThrowsIOException();
    }

    static void testValidOidReturnsNonNull() throws Exception {
        byte[] data = {0x06, 0x01, 0x2A};
        DerInputStream in = new DerInputStream(data);
        ObjectIdentifier oid = in.getOID();
        if (oid == null) {
            throw new AssertionError("getOID() returned null for valid OID encoding");
        }
    }

    static void testTruncatedLengthThrowsIOException() throws Exception {
        expectIOException(new byte[]{0x06}, "missing length byte");
    }

    static void testLengthExceedsAvailableDataThrowsIOException() throws Exception {
        expectIOException(new byte[]{0x06, 0x05, 0x2A}, "declared length exceeds available data");
    }

    static void testWrongTagThrowsIOException() throws Exception {
        expectIOException(new byte[]{0x02, 0x01, 0x2A}, "tag is not OBJECT IDENTIFIER");
    }

    static void expectIOException(byte[] data, String description) throws Exception {
        boolean thrown = false;
        try {
            DerInputStream in = new DerInputStream(data);
            in.getOID();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException: " + description);
        }
    }
}
