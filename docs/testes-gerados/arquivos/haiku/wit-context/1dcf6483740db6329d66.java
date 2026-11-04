/*
 * @test
 * @summary Verifies that ImageReader.close() throws IOException when the reader is already closed and that the reader is unusable afterwards
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!Files.exists(image)) {
            System.out.println("jimage file not found, skipping: " + image);
            return;
        }

        ImageReader reader = ImageReader.open(image);
        reader.close();

        boolean closeFailed = false;
        try {
            reader.close();
        } catch (IOException expected) {
            closeFailed = true;
        }
        if (!closeFailed) {
            throw new RuntimeException("second close() did not throw IOException");
        }

        boolean accessFailed = false;
        try {
            reader.getRootDirectory();
        } catch (IOException expected) {
            accessFailed = true;
        }
        if (!accessFailed) {
            throw new RuntimeException("getRootDirectory() after close() did not throw IOException");
        }
    }
}
