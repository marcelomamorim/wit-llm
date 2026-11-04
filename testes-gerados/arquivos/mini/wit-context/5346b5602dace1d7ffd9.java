/*
 * @test
 * @summary Test sun.security.pkcs10.PKCS10Attributes constructor with DerInputStream
 * @run main PKCS10AttributesWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.pkcs10.PKCS10Attributes;
import sun.security.util.DerInputStream;

public class PKCS10AttributesWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithNullSetThrows();
        testConstructorWithEmptySet();
    }

    private static void testConstructorWithNullSetThrows() {
        // Prepare a DerInputStream that returns null for getSet to trigger IOException
        DerInputStream in = new DerInputStream(new byte[0]) {
            @Override
            public DerValue[] getSet(int tag, boolean explicit) throws IOException {
                return null; // simulate illegal encoding
            }
        };

        try {
            new PKCS10Attributes(in);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!"Illegal encoding of attributes".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorWithEmptySet() throws IOException {
        // Prepare a DerInputStream that returns empty array for getSet
        DerInputStream in = new DerInputStream(new byte[0]) {
            @Override
            public DerValue[] getSet(int tag, boolean explicit) throws IOException {
                return new DerValue[0];
            }
        };

        PKCS10Attributes attrs = new PKCS10Attributes(in);
        if (attrs == null) {
            throw new RuntimeException("PKCS10Attributes constructor returned null");
        }
    }
}
