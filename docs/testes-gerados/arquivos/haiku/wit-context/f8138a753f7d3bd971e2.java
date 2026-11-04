/*
 * @test
 * @summary Verifies that ImageReaderFactory.get rejects a null path with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderFactoryWitupTest
 */

import jdk.internal.jimage.ImageReaderFactory;

public class ImageReaderFactoryWitupTest {
    public static void main(String[] args) throws Exception {
        boolean thrown = false;
        try {
            ImageReaderFactory.get(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null jimage path");
        }
    }
}
