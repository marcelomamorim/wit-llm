/*
 * @test
 * @summary Test BufferedReader(Reader) constructor behavior including IllegalArgumentException on invalid buffer size
 * @run main BufferedReaderWitupTest
 */

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

public class BufferedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testValidConstructor();
        testConstructorWithInvalidBufferSize();
    }

    private static void testValidConstructor() throws Exception {
        Reader r = new StringReader("test");
        BufferedReader br = new BufferedReader(r);
        if (br == null) {
            throw new AssertionError("BufferedReader constructor returned null");
        }
        // No exception expected, basic sanity check
    }

    private static void testConstructorWithInvalidBufferSize() {
        Reader r = new StringReader("test");
        try {
            // Use reflection to invoke BufferedReader(Reader, int) with invalid size 0
            java.lang.reflect.Constructor<BufferedReader> ctor = BufferedReader.class.getConstructor(Reader.class, int.class);
            ctor.newInstance(r, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer size <= 0");
        } catch (java.lang.reflect.InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Expected IllegalArgumentException but got " + cause);
            }
            // Expected exception caught
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
