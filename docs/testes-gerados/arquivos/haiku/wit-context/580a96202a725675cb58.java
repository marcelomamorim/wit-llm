/*
 * @test
 * @summary X500Name(byte[]) rejects truncated DER input and round-trips a valid encoding
 * @modules java.base/sun.security.x509
 * @run main X500NameWitupTest
 */
import java.io.IOException;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyInputThrowsIOException();
        testSingleByteInputThrowsIOException();
        testValidEncodingRoundTrip();
    }

    static void testEmptyInputThrowsIOException() {
        try {
            new X500Name(new byte[0]);
            throw new RuntimeException("Expected IOException for empty input");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testSingleByteInputThrowsIOException() {
        try {
            new X500Name(new byte[] { 0x30 });
            throw new RuntimeException("Expected IOException for single-byte input");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testValidEncodingRoundTrip() throws Exception {
        X500Name original = new X500Name("CN=Test, O=Example, C=US");
        byte[] encoded = original.getEncoded();
        X500Name decoded = new X500Name(encoded);
        if (!original.equals(decoded)) {
            throw new RuntimeException("Round trip mismatch: " + decoded);
        }
        if (!original.toString().equals(decoded.toString())) {
            throw new RuntimeException("String form mismatch: " + decoded);
        }
    }
}
