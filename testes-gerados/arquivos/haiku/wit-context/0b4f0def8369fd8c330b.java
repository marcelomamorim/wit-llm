/*
 * @test
 * @summary Verify ImageReader.getModuleNames returns module names from the runtime image and throws IllegalStateException after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(image);
        String[] names;
        try {
            names = reader.getModuleNames();
            if (names == null || names.length == 0) {
                throw new AssertionError("expected non-empty module names");
            }
            if (!Arrays.asList(names).contains("java.base")) {
                throw new AssertionError("expected java.base in module names: " + Arrays.toString(names));
            }
        } finally {
            reader.close();
        }
        boolean thrown = false;
        try {
            reader.getModuleNames();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalStateException after close");
        }
    }
}
