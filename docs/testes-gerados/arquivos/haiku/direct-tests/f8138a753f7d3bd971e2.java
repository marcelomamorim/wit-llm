/*
 * @test
 * @summary Tests ImageReaderFactory.get for null input, missing image files and cached reader reuse
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderFactoryWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageReaderFactory;

public class ImageReaderFactoryWitupTest {

    public static void main(String[] args) throws Exception {
        testNullArgumentThrowsNullPointerException();
        testMissingImageFileThrowsIOException();
        testSameImagePathReturnsSameReader();
    }

    static void testNullArgumentThrowsNullPointerException() {
        try {
            ImageReaderFactory.get(null);
            throw new AssertionError("expected NullPointerException for null path");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(jimage)
        } catch (IOException e) {
            throw new AssertionError("unexpected IOException for null path", e);
        }
    }

    static void testMissingImageFileThrowsIOException() {
        Path missing = Paths.get("nonexistent-witup-image-file.jimage");
        try {
            ImageReaderFactory.get(missing);
            throw new AssertionError("expected IOException for missing image file");
        } catch (IOException expected) {
            // expected: OPENER failure is wrapped and unwrapped as IOException
        }
    }

    static void testSameImagePathReturnsSameReader() throws IOException {
        Path boot = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader first = ImageReaderFactory.get(boot);
        ImageReader second = ImageReaderFactory.get(boot);
        if (first == null || second == null) {
            throw new AssertionError("get returned null reader");
        }
        if (first != second) {
            throw new AssertionError("expected cached ImageReader for the same path");
        }
    }
}
