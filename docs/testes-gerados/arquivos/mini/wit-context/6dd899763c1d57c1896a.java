/*
 * @test
 * @summary Test BufferedReader constructor with buffer size validation
 * @run main BufferedReaderWitupTest
 */

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

public class BufferedReaderWitupTest {
    public static void main(String[] args) {
        testValidBufferSize();
        testZeroBufferSize();
        testNegativeBufferSize();
        System.out.println("PASS");
    }

    private static void testValidBufferSize() {
        Reader r = new StringReader("");
        // Valid buffer size should create BufferedReader without exception
        BufferedReader br = new BufferedReader(r, 1);
        if (br == null) {
            throw new AssertionError("BufferedReader instance is null");
        }
    }

    private static void testZeroBufferSize() {
        Reader r = new StringReader("");
        try {
            new BufferedReader(r, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Buffer size <= 0")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testNegativeBufferSize() {
        Reader r = new StringReader("");
        try {
            new BufferedReader(r, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Buffer size <= 0")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
