/*
 * @test
 * @summary Verifies that the public ImageLocation constructor rejects null arguments
 * @modules java.base/jdk.internal.jimage
 * @run main ImageLocationWitupTest
 */

import jdk.internal.jimage.ImageLocation;

public class ImageLocationWitupTest {
    public static void main(String[] args) {
        expectNpe(null, null);
        expectNpe(new long[ImageLocation.ATTRIBUTE_COUNT], null);
    }

    private static void expectNpe(long[] attributes, Object strings) {
        try {
            new ImageLocation(attributes, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("NullPointerException expected for null arguments");
    }
}
