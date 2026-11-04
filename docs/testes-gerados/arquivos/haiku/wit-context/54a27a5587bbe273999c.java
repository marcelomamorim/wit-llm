/*
 * @test
 * @summary Verifies observable single-byte write(int) behavior of the Base64 encoder output stream
 * @run main Base64WitupTest
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testSingleBytesEncodeAsWhole();
        testSingleByteWithPadding();
        testHighBitsAreMasked();
        testWriteAfterCloseThrowsIOException();
    }

    private static void testSingleBytesEncodeAsWhole() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getEncoder().wrap(bos);
        os.write('f');
        os.write('o');
        os.write('o');
        os.close();
        check("Zm9v", bos.toString("US-ASCII"));
    }

    private static void testSingleByteWithPadding() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getEncoder().wrap(bos);
        os.write('f');
        os.close();
        check("Zg==", bos.toString("US-ASCII"));
    }

    private static void testHighBitsAreMasked() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = Base64.getEncoder().wrap(bos);
        os.write(0x166);
        os.close();
        check("Zg==", bos.toString("US-ASCII"));
    }

    private static void testWriteAfterCloseThrowsIOException() throws Exception {
        OutputStream os = Base64.getEncoder().wrap(new ByteArrayOutputStream());
        os.close();
        try {
            os.write(1);
            throw new RuntimeException("Expected IOException after close");
        } catch (IOException expected) {
        }
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
