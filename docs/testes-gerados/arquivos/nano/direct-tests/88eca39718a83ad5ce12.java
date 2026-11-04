/* @test    @summary Test getResource method of ImageReader for valid resource name    @run main ImageReaderWitupTest */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        Path imagePath = Paths.get("/path/to/test/image");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
            byte[] resourceBytes = reader.getResource("testResource");
            if (resourceBytes == null || resourceBytes.length == 0) {
                throw new AssertionError("Resource content should not be null or empty");
            }
            System.out.println("Resource retrieved successfully, size: " + resourceBytes.length);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
