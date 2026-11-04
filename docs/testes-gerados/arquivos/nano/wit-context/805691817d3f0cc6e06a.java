/* @test    @summary Test for sun.security.x509.X500Name.encode(DerOutputStream out) method    @run main X500NameWitupTest */

import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;
import java.io.IOException;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeNormal();
        testEncodeWithLargeNames();
        testEncodeOutOfMemoryError();
    }

    private static void testEncodeNormal() throws IOException {
        // Setup a simple X500Name with minimal RDNs
        X500Name name = new X500Name("CN=Test, O=Example");
        DerOutputStream out = new DerOutputStream();
        name.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding returned null or empty byte array");
        }
    }

    private static void testEncodeWithLargeNames() throws IOException {
        // Create a name with many RDNs to simulate large input
        StringBuilder dnBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            dnBuilder.append("CN=Name" + i + ",");
        }
        dnBuilder.append("O=LargeTest");
        X500Name name = new X500Name(dnBuilder.toString());
        DerOutputStream out = new DerOutputStream();
        name.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding with large names failed");
        }
    }

    private static void testEncodeOutOfMemoryError() {
        // Simulate OutOfMemoryError by passing a custom DerOutputStream that throws
        DerOutputStream mockOut = new DerOutputStream() {
            @Override
            public void write(byte[] b) throws IOException {
                throw new OutOfMemoryError("Simulated OOM during write");
            }
        };
        try {
            X500Name name = new X500Name("CN=Test");
            name.encode(mockOut);
            throw new AssertionError("Expected OutOfMemoryError was not thrown");
        } catch (OutOfMemoryError e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
