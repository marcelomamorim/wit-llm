/*
 * @test
 * @summary Verifies observable behavior of ImageStream.skip(int), including negative and overflowing skip values
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testSkipZeroKeepsPosition();
        testSkipAdvancesPosition();
        testSkipToEnd();
        testSkipNegativeThrows();
        testSkipBeyondRemainingThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSkipZeroKeepsPosition() {
        ImageStream s = new ImageStream(new byte[] {1, 2, 3});
        s.skip(0);
        check(s.get() == 1, "skip(0) must not advance the position");
    }

    static void testSkipAdvancesPosition() {
        ImageStream s = new ImageStream(new byte[] {1, 2, 3});
        s.skip(2);
        check(s.get() == 3, "skip(2) must advance past two bytes");
    }

    static void testSkipToEnd() {
        ImageStream s = new ImageStream(new byte[] {1, 2, 3});
        s.skip(3);
        check(!s.hasByte(), "skip(remaining) must consume all bytes");
    }

    static void testSkipNegativeThrows() {
        ImageStream s = new ImageStream(new byte[] {1, 2, 3});
        try {
            s.skip(-1);
            throw new RuntimeException("skip(-1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSkipBeyondRemainingThrows() {
        ImageStream s = new ImageStream(new byte[] {1, 2, 3});
        try {
            s.skip(4);
            throw new RuntimeException("skip beyond limit should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected: ByteBuffer.position rejects a new position above the limit
        }
    }
}
