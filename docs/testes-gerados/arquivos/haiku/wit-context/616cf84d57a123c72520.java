/*
 * @test
 * @summary ImageReader.getStrings must succeed while open and throw IllegalStateException after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!modules.toFile().exists()) {
            throw new RuntimeException("runtime image not found: " + modules);
        }
        ImageReader reader = ImageReader.open(modules);
        Object strings = reader.getStrings();
        if (strings == null) {
            throw new AssertionError("getStrings returned null while open");
        }
        reader.close();
        try {
            reader.getStrings();
            throw new AssertionError("expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            // expected path: requireOpen() rejects use after close
        }
    }
}
