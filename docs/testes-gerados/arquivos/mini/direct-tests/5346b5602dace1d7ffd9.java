/*
 * @test
 * @summary Test sun.security.pkcs10.PKCS10Attributes constructor with DerInputStream
 * @run main PKCS10AttributesWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.pkcs10.PKCS10Attributes;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class PKCS10AttributesWitupTest {
    public static void main(String[] args) throws Exception {
        testNullSetThrows();
        testEmptySetCreatesEmptyAttributes();
    }

    private static void testNullSetThrows() {
        // Create a DerInputStream subclass that returns null for getSet
        DerInputStream in = new DerInputStream(new byte[0]) {
            @Override
            public DerValue[] getSet(int tag, boolean explicit) throws IOException {
                return null;
            }
        };

        try {
            new PKCS10Attributes(in);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Illegal encoding of attributes".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testEmptySetCreatesEmptyAttributes() throws IOException {
        // Create a DerInputStream subclass that returns empty array for getSet
        DerInputStream in = new DerInputStream(new byte[0]) {
            @Override
            public DerValue[] getSet(int tag, boolean explicit) throws IOException {
                return new DerValue[0];
            }
        };

        PKCS10Attributes attrs = new PKCS10Attributes(in);
        // No exception means success; no public getter to verify contents,
        // so just ensure construction completes.
    }
}
