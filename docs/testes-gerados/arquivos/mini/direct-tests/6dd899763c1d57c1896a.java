/*
 * @test
 * @summary Test BufferedReader constructor with buffer size parameter
 * @run main BufferedReaderWitupTest
 */

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

public class BufferedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBufferSize();
        testZeroBufferSize();
        testNegativeBufferSize();
    }

    private static void testValidBufferSize() throws Exception {
        Reader r = new StringReader("test");
        BufferedReader br = new BufferedReader(r, 10);
        if (br == null) throw new Exception("BufferedReader instance is null");
    }

    private static void testZeroBufferSize() {
        Reader r = new StringReader("");
        try {
            new BufferedReader(r, 0);
            throw new RuntimeException("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testNegativeBufferSize() {
        Reader r = new StringReader("");
        try {
            new BufferedReader(r, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
