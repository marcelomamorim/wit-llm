/* @test    @summary Test for ImageReader.getName() method to ensure it returns a non-null string when the image is open.    @run main ImageReaderWitupTest */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        Path dummyPath = Paths.get("/dev/null");
        // Open ImageReader with native byte order
        try (ImageReader reader = ImageReader.open(dummyPath)) {
            String name = reader.getName();
            if (name == null) {
                throw new AssertionError("getName() returned null");
            }
            System.out.println("getName() returned: " + name);
        }
    }
}
