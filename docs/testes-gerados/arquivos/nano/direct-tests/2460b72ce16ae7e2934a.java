/* @test    @summary Test skip method of ImageStream for positive, zero, and negative values    @run main ImageStreamWitupTest */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testSkipPositive();
        testSkipZero();
        testSkipNegative();
    }

    private static void testSkipPositive() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.position(2);
        ImageStream stream = new ImageStream(buffer);
        stream.skip(3);
        assert stream.buffer.position() == 5 : "Expected position 5, but got " + stream.buffer.position();
    }

    private static void testSkipZero() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.position(4);
        ImageStream stream = new ImageStream(buffer);
        stream.skip(0);
        assert stream.buffer.position() == 4 : "Expected position 4, but got " + stream.buffer.position();
    }

    private static void testSkipNegative() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.position(5);
        ImageStream stream = new ImageStream(buffer);
        boolean exceptionThrown = false;
        try {
            stream.skip(-1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for negative skip";
    }
}
