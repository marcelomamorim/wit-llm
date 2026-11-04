/*
 * @test
 * @summary Verify ImageReader.getResource(Resource) rejects calls after the reader is closed
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.Resource;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(image);
        reader.close();

        try {
            reader.getResource((Resource) null);
            throw new AssertionError("expected IOException for closed reader");
        } catch (IOException expected) {
            if (!"image file closed".equals(expected.getMessage())) {
                throw new AssertionError("unexpected message: " + expected.getMessage());
            }
        }

        try {
            reader.close();
            throw new AssertionError("expected IOException on second close");
        } catch (IOException expected) {
            if (!"image file already closed".equals(expected.getMessage())) {
                throw new AssertionError("unexpected message: " + expected.getMessage());
            }
        }
    }
}
