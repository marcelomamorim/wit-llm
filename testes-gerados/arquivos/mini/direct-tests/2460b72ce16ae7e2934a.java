/*
 * @test
 * @summary Test jdk.internal.jimage.ImageStream.skip(int) method
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {
    public static void main(String[] args) {
        testSkipPositive();
        testSkipZero();
        testSkipNegativeThrows();
    }

    private static void testSkipPositive() {
        ImageStream stream = new ImageStream(new byte[10]);
        int initialPos = stream.buffer.position();
        stream.skip(5);
        int newPos = stream.buffer.position();
        if (newPos != initialPos + 5) {
            throw new AssertionError("skip(5) did not advance position correctly: " + newPos);
        }
    }

    private static void testSkipZero() {
        ImageStream stream = new ImageStream(new byte[10]);
        int initialPos = stream.buffer.position();
        stream.skip(0);
        int newPos = stream.buffer.position();
        if (newPos != initialPos) {
            throw new AssertionError("skip(0) should not change position: " + newPos);
        }
    }

    private static void testSkipNegativeThrows() {
        ImageStream stream = new ImageStream(new byte[10]);
        try {
            stream.skip(-1);
            throw new AssertionError("skip(-1) did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("skip value = -1")) {
                throw new AssertionError("Exception message incorrect: " + msg);
            }
        }
    }
}
