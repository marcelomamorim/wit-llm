/*
 * @test
 * @summary Verifies PushbackInputStream(InputStream, int) argument validation and delegation to the wrapped stream
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonPositiveSizeThrows() {
        int[] sizes = {0, -1, Integer.MIN_VALUE};
        for (int size : sizes) {
            InputStream in = new ByteArrayInputStream(new byte[] {1, 2, 3});
            boolean thrown = false;
            try {
                new PushbackInputStream(in, size);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "Expected IllegalArgumentException for size " + size);
        }
    }

    static void testPositiveSizeConstructs() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[] {10, 20, 30});
        PushbackInputStream pis = new PushbackInputStream(in, 4);
        check(pis.read() == 10, "First byte read through constructed stream should be 10");
        check(pis.read() == 20, "Second byte read through constructed stream should be 20");
    }

    static void testDefaultSizeConstructorDelegates() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[] {7, 8});
        PushbackInputStream pis = new PushbackInputStream(in);
        check(pis.read() == 7, "Default constructor stream should read first byte 7");
        check(pis.read() == 8, "Default constructor stream should read second byte 8");
        check(pis.read() == -1, "Default constructor stream should be at end after two bytes");
    }

    public static void main(String[] args) throws Exception {
        testNonPositiveSizeThrows();
        testPositiveSizeConstructs();
        testDefaultSizeConstructorDelegates();
    }
}
