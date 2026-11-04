/*
 * @test
 * @summary WITUP-derived behavioral checks for ByteArrayOutputStream(int size) constructor
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeSizeThrowsIllegalArgumentException();
        testZeroSizeAcceptsWrites();
        testPositiveSizeStartsEmptyAndRetainsWrittenBytes();
    }

    static void testNegativeSizeThrowsIllegalArgumentException() {
        try {
            new ByteArrayOutputStream(-1);
            throw new RuntimeException("Expected IllegalArgumentException for size -1");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("-1")) {
                throw new RuntimeException("Unexpected message: " + msg);
            }
        }
    }

    static void testZeroSizeAcceptsWrites() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        if (out.size() != 0) {
            throw new RuntimeException("Initial size should be 0, was " + out.size());
        }
        out.write(7);
        byte[] data = out.toByteArray();
        if (data.length != 1 || data[0] != 7) {
            throw new RuntimeException("Unexpected content after write on zero-capacity stream");
        }
    }

    static void testPositiveSizeStartsEmptyAndRetainsWrittenBytes() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(2);
        if (out.size() != 0) {
            throw new RuntimeException("Initial size should be 0, was " + out.size());
        }
        byte[] input = {1, 2, 3};
        out.write(input, 0, input.length);
        if (out.size() != 3) {
            throw new RuntimeException("Expected size 3, was " + out.size());
        }
        byte[] data = out.toByteArray();
        if (data.length != 3 || data[0] != 1 || data[1] != 2 || data[2] != 3) {
            throw new RuntimeException("Unexpected content after growth");
        }
    }
}
