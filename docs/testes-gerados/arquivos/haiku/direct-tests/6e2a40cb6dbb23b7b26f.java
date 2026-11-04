/*
 * @test
 * @summary Witup-style tests for ImageStream.ensure behavior
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testNegativeNeedsThrows();
        testNoGrowthWhenEnoughRemaining();
        testGrowthDoublesWhenNeedsFitsArray();
        testGrowthToPositionPlusNeedsWhenNeedsExceedsArray();
        testZeroNeedsIsNoop();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeNeedsThrows() {
        ImageStream stream = new ImageStream(4);
        boolean thrown = false;
        try {
            stream.ensure(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "ensure(-1) must throw IndexOutOfBoundsException");
    }

    static void testNoGrowthWhenEnoughRemaining() {
        ImageStream stream = new ImageStream(4);
        stream.ensure(2);
        check(stream.hasBytes(4), "remaining must stay 4 after ensure(2)");
        check(!stream.hasBytes(5), "no growth expected when needs <= remaining");
    }

    static void testGrowthDoublesWhenNeedsFitsArray() {
        ImageStream stream = new ImageStream(4);
        stream.skip(3);
        stream.ensure(3);
        check(stream.hasBytes(5), "after doubling to 8 with position 3, 5 bytes must be available");
        check(!stream.hasBytes(6), "after doubling to 8 with position 3, only 5 bytes must be available");
    }

    static void testGrowthToPositionPlusNeedsWhenNeedsExceedsArray() {
        ImageStream stream = new ImageStream(4);
        stream.skip(2);
        stream.ensure(5);
        check(stream.hasBytes(5), "after growth to position+needs, needs bytes must be available");
        check(!stream.hasBytes(6), "after growth to position+needs, exactly needs bytes must be available");
    }

    static void testZeroNeedsIsNoop() {
        ImageStream stream = new ImageStream(new byte[] {1, 2, 3});
        stream.ensure(0);
        check(stream.hasBytes(3), "ensure(0) must keep remaining bytes");
        check(!stream.hasBytes(4), "ensure(0) must not grow the stream");
    }
}
