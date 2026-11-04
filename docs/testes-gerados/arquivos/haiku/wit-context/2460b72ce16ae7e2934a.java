/*
 * @test
 * @summary Witup generated tests for ImageStream.skip(int) negative and bounds behavior
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {
    public static void main(String[] args) {
        testSkipZeroKeepsPosition();
        testSkipNegativeThrowsAndKeepsState();
        testSkipMinValueThrows();
        testSkipWithinBounds();
        testSkipToEnd();
        testSkipBeyondRemainingThrows();
    }

    static void testSkipZeroKeepsPosition() {
        ImageStream s = new ImageStream(new byte[]{1, 2, 3});
        s.skip(0);
        if (s.get() != 1) {
            throw new RuntimeException("skip(0) must not move position");
        }
    }

    static void testSkipNegativeThrowsAndKeepsState() {
        ImageStream s = new ImageStream(new byte[]{1, 2, 3});
        try {
            s.skip(-1);
            throw new RuntimeException("skip(-1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
        }
        if (s.get() != 1) {
            throw new RuntimeException("failed skip must not change position");
        }
    }

    static void testSkipMinValueThrows() {
        ImageStream s = new ImageStream(new byte[]{1, 2, 3});
        try {
            s.skip(Integer.MIN_VALUE);
            throw new RuntimeException("skip(MIN_VALUE) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    static void testSkipWithinBounds() {
        ImageStream s = new ImageStream(new byte[]{1, 2, 3});
        s.skip(2);
        if (s.get() != 3) {
            throw new RuntimeException("skip(2) should position at third byte");
        }
    }

    static void testSkipToEnd() {
        ImageStream s = new ImageStream(new byte[]{1, 2, 3});
        s.skip(3);
        if (s.hasByte()) {
            throw new RuntimeException("skip to end should leave no remaining bytes");
        }
    }

    static void testSkipBeyondRemainingThrows() {
        ImageStream s = new ImageStream(new byte[]{1, 2, 3});
        try {
            s.skip(4);
            throw new RuntimeException("skip beyond remaining should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }
}
