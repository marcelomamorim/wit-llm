/* @test    @summary Test BufferedReader constructor with valid and invalid buffer sizes    @run main BufferedReaderWitupTest */

import java.io.BufferedReader;
import java.io.StringReader;

public class BufferedReaderWitupTest {

    public static void main(String[] args) {
        testValidBufferSize();
        testZeroBufferSize();
        testNegativeBufferSize();
    }

    static void testValidBufferSize() {
        StringReader sr = new StringReader("test");
        BufferedReader br = new BufferedReader(sr, 1024);
        assert br != null : "BufferedReader should be created with valid buffer size";
    }

    static void testZeroBufferSize() {
        StringReader sr = new StringReader("test");
        try {
            new BufferedReader(sr, 0);
            assert false : "Expected IllegalArgumentException for buffer size 0";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Buffer size") : "Exception message should mention buffer size";
        }
    }

    static void testNegativeBufferSize() {
        StringReader sr = new StringReader("test");
        try {
            new BufferedReader(sr, -10);
            assert false : "Expected IllegalArgumentException for negative buffer size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Buffer size") : "Exception message should mention buffer size";
        }
    }
}
