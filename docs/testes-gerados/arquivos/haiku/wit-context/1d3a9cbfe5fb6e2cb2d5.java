/*
 * @test
 * @summary Verify ImageReader.getImagePath returns the image path while open and throws IllegalStateException after close
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
        Path p = reader.getImagePath();
        if (p == null) {
            throw new AssertionError("getImagePath returned null while open");
        }
        if (!"modules".equals(p.getFileName().toString())) {
            throw new AssertionError("unexpected image file name: " + p);
        }
        reader.close();
        try {
            reader.getImagePath();
            throw new AssertionError("expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
        }
    }
}
