/*
 * @test
 * @summary Witup generated checks for ImageStream.put(ImageStream)
 * @modules java.base/jdk.internal.jimage
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testReturnsSameInstanceAndKeepsSourcePosition();
        testEmptySourceReturnsThis();
        testNullSourceThrowsNullPointerException();
    }

    static void testReturnsSameInstanceAndKeepsSourcePosition() {
        ImageStream source = new ImageStream(new byte[] {1, 2, 3, 4});
        source.skip(2);
        ImageStream dest = new ImageStream();
        if (dest.put(source) != dest) {
            throw new AssertionError("put(ImageStream) must return this");
        }
        if (source.get() != 3) {
            throw new AssertionError("source position must not be altered");
        }
        if (source.get() != 4) {
            throw new AssertionError("source position must not be altered");
        }
    }

    static void testEmptySourceReturnsThis() {
        ImageStream empty = new ImageStream(new byte[] {9});
        ImageStream dest = new ImageStream(16);
        if (dest.put(empty) != dest) {
            throw new AssertionError("put of empty source must return this");
        }
        if (empty.get() != 9) {
            throw new AssertionError("empty source content must remain readable");
        }
    }

    static void testNullSourceThrowsNullPointerException() {
        ImageStream dest = new ImageStream();
        try {
            dest.put((ImageStream) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("put(null) must throw NullPointerException");
    }
}
