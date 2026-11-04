/*
 * @test
 * @summary Verify KeyIdentifier(DerValue) reads the octet string and rejects non-octet-string tags
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main KeyIdentifierWitupTest
 */

import java.io.IOException;
import java.util.Arrays;

import sun.security.util.DerValue;
import sun.security.x509.KeyIdentifier;

public class KeyIdentifierWitupTest {

    public static void main(String[] args) throws Exception {
        testOctetStringIsRead();
        testEmptyOctetString();
        testNonOctetStringTagRejected();
        testReturnedArrayIsCopy();
    }

    static void testOctetStringIsRead() throws Exception {
        byte[] data = {0x01, 0x02, 0x03, (byte) 0xFF};
        DerValue dv = new DerValue(DerValue.tag_OctetString, data);
        KeyIdentifier ki = new KeyIdentifier(dv);
        if (!Arrays.equals(data, ki.getIdentifier())) {
            throw new RuntimeException("identifier does not match octet string content");
        }
    }

    static void testEmptyOctetString() throws Exception {
        DerValue dv = new DerValue(DerValue.tag_OctetString, new byte[0]);
        KeyIdentifier ki = new KeyIdentifier(dv);
        if (ki.getIdentifier().length != 0) {
            throw new RuntimeException("expected empty identifier");
        }
    }

    static void testNonOctetStringTagRejected() throws Exception {
        DerValue dv = new DerValue(DerValue.tag_Integer, new byte[] {0x05});
        try {
            new KeyIdentifier(dv);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException for non-octet-string tag");
    }

    static void testReturnedArrayIsCopy() throws Exception {
        byte[] data = {0x0A, 0x0B};
        KeyIdentifier ki = new KeyIdentifier(new DerValue(DerValue.tag_OctetString, data));
        byte[] first = ki.getIdentifier();
        first[0] = 0x00;
        byte[] second = ki.getIdentifier();
        if (second[0] != 0x0A) {
            throw new RuntimeException("getIdentifier must return a defensive copy");
        }
    }
}
