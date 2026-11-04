/*
 * @test
 * @summary Test getResource(Resource rs) method of ImageReader for valid Resource.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.Resource;

public class ImageReaderWitupTest {

    public static void main(String[] args) {
        try {
            testGetResourceWithValidResource();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testGetResourceWithValidResource() throws IOException {
        Path imagePath = Paths.get("/path/to/fake/image");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath);
            Resource resource = new Resource("testResource");
            byte[] data = reader.getResource(resource);
            // Verify that data is not null (behavior observable)
            if (data == null) {
                throw new AssertionError("Expected non-null byte array");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
