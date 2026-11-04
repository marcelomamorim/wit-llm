/*
 * @test
 * @summary Witup-generated tests for ImageStream.ensure(int) observable behavior
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeNeedsThrowsIndexOutOfBounds();
        testZeroNeedsIsNoOp();
        testNeedsEqualToRemainingIsNoOp();
        testNeedsGreaterThanRemainingAndArrayLengthGrowsToPositionPlusNeeds();
        testNeedsGreaterThanRemainingWithinArrayLengthGrowsDoubled();
    }

    static void testNegativeNeedsThrowsIndexOutOfBounds() {
        ImageStream stream = new ImageStream(new byte[] {1, 2, 3, 4});
        boolean thrown = false;
        try {
            stream.ensure(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
            check(e.getMessage() != null && e.getMessage().contains("Bad value: -1"),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "ensure(-1) must throw IndexOutOfBoundsException");
    }

    static void testZeroNeedsIsNoOp() {
        ImageStream stream = new ImageStream(new byte[] {1, 2, 3, 4});
        stream.ensure(0);
        check(stream.hasBytes(4), "remaining must stay 4 after ensure(0)");
        check(!stream.hasBytes(5), "remaining must not grow after ensure(0)");
    }

    static void testNeedsEqualToRemainingIsNoOp() {
        ImageStream stream = new ImageStream(new byte[] {1, 2});
        stream.ensure(2);
        check(stream.hasBytes(2), "ensure(remaining) must keep 2 bytes available");
        check(!stream.hasBytes(3), "ensure(remaining) must not grow the buffer");
    }

    static void testNeedsGreaterThanRemainingAndArrayLengthGrowsToPositionPlusNeeds() {
        ImageStream stream = new ImageStream(new byte[] {1, 2, 3, 4});
        stream.ensure(5);
        check(stream.hasBytes(5), "ensure(5) on 4-byte stream must make 5 bytes available");
    }

    static void testNeedsGreaterThanRemainingWithinArrayLengthGrowsDoubled() {
        ImageStream stream = new ImageStream(new byte[] {1, 2, 3, 4});
        stream.get();
        stream.get();
        stream.ensure(3);
        check(stream.hasBytes(6), "ensure(3) after consuming 2 of 4 bytes must make 6 bytes available");
        check(!stream.hasBytes(7), "buffer must not exceed doubled size of 8 minus position 2");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
