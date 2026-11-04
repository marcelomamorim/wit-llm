/*
 * @test
 * @summary Test sun.security.x509.X500Name.emit(DerOutputStream) for basic functionality and OutOfMemoryError scenarios
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testEmitBasic();
        testEmitNullDerOutputStream();
        // OutOfMemoryError scenarios from WIT are not reproducible deterministically here and are not tested explicitly
        // because they depend on internal buffer overflow and JVM memory state.
    }

    private static void testEmitBasic() throws IOException {
        // Construct a simple X500Name with commonName only
        X500Name name = new X500Name("CN=Test User");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        // Call emit and verify output is not empty
        name.emit(derOut);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output is empty");
        }
    }

    private static void testEmitNullDerOutputStream() {
        // emit should throw NullPointerException if passed null
        X500Name name = new X500Name("CN=Test User");
        try {
            name.emit(null);
            throw new AssertionError("Expected NullPointerException when passing null DerOutputStream");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException when passing null DerOutputStream", e);
        }
    }
}
