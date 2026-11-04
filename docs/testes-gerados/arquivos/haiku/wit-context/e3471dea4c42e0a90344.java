/*
 * @test
 * @summary Verifies that ImageReader.getAttributes throws IllegalStateException after the reader is closed
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(image);
        reader.close();
        try {
            reader.getAttributes(0);
            throw new AssertionError("expected IllegalStateException for closed reader");
        } catch (IllegalStateException expected) {
            if (!"image file closed".equals(expected.getMessage())) {
                throw new AssertionError("unexpected message: " + expected.getMessage());
            }
        }
    }
}
