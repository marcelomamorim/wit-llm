/*
 * @test
 * @summary ImageStream.put(ImageStream) copies the readable prefix of the source stream
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;
import java.nio.ByteBuffer;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testCopiesPositionPrefix();
        testEmptySourceWritesNothing();
        testReturnsSameInstance();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesPositionPrefix() {
        ImageStream source = new ImageStream(new byte[]{1, 2, 3});
        source.skip(2);
        ByteBuffer target = ByteBuffer.allocate(4);
        ImageStream dest = new ImageStream(target);
        dest.put(source);
        byte[] b = target.array();
        check(b[0] == 1 && b[1] == 2 && b[2] == 0 && b[3] == 0,
              "Expected only the source prefix [1,2] to be copied");
    }

    static void testEmptySourceWritesNothing() {
        ImageStream source = new ImageStream(ByteBuffer.wrap(new byte[]{5}));
        ByteBuffer target = ByteBuffer.allocate(2);
        ImageStream dest = new ImageStream(target);
        dest.put(source);
        check(target.position() == 0, "Empty source must not advance target position");
        check(target.array()[0] == 0 && target.array()[1] == 0,
              "Empty source must not modify target content");
    }

    static void testReturnsSameInstance() {
        ImageStream source = new ImageStream(new byte[]{7});
        ImageStream dest = new ImageStream(ByteBuffer.allocate(2));
        check(dest.put(source) == dest, "put(ImageStream) must return this");
    }
}
