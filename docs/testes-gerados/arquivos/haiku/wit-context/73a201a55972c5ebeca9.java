/*
 * @test
 * @summary Smoke tests for public ImageHeader API observable behavior
 * @modules java.base/jdk.internal.jimage
 * @run main ImageHeaderWitupTest
 */

import jdk.internal.jimage.ImageHeader;

public class ImageHeaderWitupTest {

    static void check(boolean condition, String message) throws Exception {
        if (!condition) {
            throw new Exception(message);
        }
    }

    public static void main(String[] args) throws Exception {
        check(ImageHeader.getHeaderSize() == 28,
              "getHeaderSize expected 28 but was " + ImageHeader.getHeaderSize());
        check(ImageHeader.MAGIC == 0xCAFEDADA, "unexpected MAGIC");
        check(ImageHeader.MAJOR_VERSION == 1, "unexpected MAJOR_VERSION");
        check(ImageHeader.MINOR_VERSION == 0, "unexpected MINOR_VERSION");

        ImageHeader h1 = new ImageHeader(1, 2, 3, 4);
        check(h1 != null, "4-arg constructor returned null");

        ImageHeader h2 = new ImageHeader(ImageHeader.MAGIC, ImageHeader.MAJOR_VERSION,
                                         ImageHeader.MINOR_VERSION, 0, 5, 6, 7, 8);
        check(h2 != null, "8-arg constructor returned null");
    }
}
