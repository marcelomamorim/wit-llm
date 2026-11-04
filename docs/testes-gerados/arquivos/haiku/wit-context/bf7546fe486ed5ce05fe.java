/*
 * @test
 * @summary Checks public DerValue-based construction of KeyIdentifier, including defensive copy and rejection of non-OCTET STRING values
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main KeyIdentifierWitupTest
 */

import java.io.IOException;
import java.util.Arrays;

import sun.security.util.DerValue;
import sun.security.x509.KeyIdentifier;

public class KeyIdentifierWitupTest {

    public static void main(String[] args) throws Exception {
        testOctetStringValueIsExtracted();
        testReturnedIdentifierIsCopy();
        testNonOctetStringTagIsRejected();
    }

    private static void testOctetStringValueIsExtracted() throws Exception {
        byte[] der = {0x04, 0x03, 0x01, 0x02, 0x03};
        KeyIdentifier ki = new KeyIdentifier(new DerValue(der));
        byte[] expected = {0x01, 0x02, 0x03};
        if (!Arrays.equals(expected, ki.getIdentifier())) {
            throw new AssertionError("Unexpected identifier: "
                    + Arrays.toString(ki.getIdentifier()));
        }
    }

    private static void testReturnedIdentifierIsCopy() throws Exception {
        byte[] der = {0x04, 0x02, 0x0A, 0x0B};
        KeyIdentifier ki = new KeyIdentifier(new DerValue(der));
        byte[] first = ki.getIdentifier();
        first[0] = (byte) 0xFF;
        byte[] second = ki.getIdentifier();
        byte[] expected = {0x0A, 0x0B};
        if (!Arrays.equals(expected, second)) {
            throw new AssertionError("Internal state was modified via returned array");
        }
    }

    private static void testNonOctetStringTagIsRejected() throws Exception {
        byte[] integer = {0x02, 0x01, 0x05};
        boolean thrown = false;
        try {
            new KeyIdentifier(new DerValue(integer));
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for non-OCTET STRING DER value");
        }
    }
}
