/*
 * @test
 * @summary ImageReader.getRootDirectory must fail with IOException once the reader is closed
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(modules);
        Object root = reader.getRootDirectory();
        if (root == null) {
            throw new AssertionError("root directory must not be null while open");
        }
        reader.close();
        try {
            reader.getRootDirectory();
            throw new AssertionError("expected IOException after close");
        } catch (IOException expected) {
        }
    }
}
