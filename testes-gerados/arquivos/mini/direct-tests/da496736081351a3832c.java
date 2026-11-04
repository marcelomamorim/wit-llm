/*
 * @test
 * @summary Test PKCS12Attribute constructor with byte[] encoded input
 * @run main PKCS12AttributeWitupTest
 */

import java.security.PKCS12Attribute;
import java.io.IOException;

public class PKCS12AttributeWitupTest {
    public static void main(String[] args) throws Exception {
        testNullEncodedThrowsNPE();
        testValidEncodedCreatesInstance();
        testInvalidEncodedThrowsIAE();
    }

    private static void testNullEncodedThrowsNPE() {
        try {
            new PKCS12Attribute((byte[]) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidEncodedCreatesInstance() throws Exception {
        // A minimal valid encoding for PKCS12Attribute is not trivial to produce without
        // internal ASN.1 encoding knowledge, so we use a valid encoding from the other constructor.
        // Construct a PKCS12Attribute with name and value, then get its encoded form.
        PKCS12Attribute attr = new PKCS12Attribute("1.2.3.4", "value");
        byte[] encoded = attr.getEncoded();

        PKCS12Attribute attr2 = new PKCS12Attribute(encoded);
        if (!java.util.Arrays.equals(encoded, attr2.getEncoded())) {
            throw new RuntimeException("Encoded bytes differ after round-trip");
        }
        if (!"1.2.3.4".equals(attr2.getName())) {
            throw new RuntimeException("Name mismatch after decoding");
        }
        if (!"value".equals(attr2.getValue())) {
            throw new RuntimeException("Value mismatch after decoding");
        }
    }

    private static void testInvalidEncodedThrowsIAE() {
        // Provide an invalid encoding (empty array) to trigger IOException and IllegalArgumentException
        try {
            new PKCS12Attribute(new byte[0]);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            if (expected.getCause() == null || !(expected.getCause() instanceof IOException)) {
                throw new RuntimeException("IllegalArgumentException cause is not IOException");
            }
        }
    }
}
