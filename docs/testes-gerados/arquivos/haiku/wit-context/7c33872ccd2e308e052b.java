/*
 * @test
 * @summary Checks ImageReader.findLocation on an open image and its IllegalStateException after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageLocation;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    private static Path modulesFile() {
        return Paths.get(System.getProperty("java.home"), "lib", "modules");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void testFindLocationOnOpenReader() throws IOException {
        try (ImageReader reader = ImageReader.open(modulesFile())) {
            ImageLocation loc = reader.findLocation("java.base", "java/lang/Object.class");
            check(loc != null, "findLocation returned null for java.base java/lang/Object.class");
        }
    }

    private static void testFindLocationAfterCloseThrows() throws IOException {
        ImageReader reader = ImageReader.open(modulesFile());
        reader.close();
        boolean thrown = false;
        try {
            reader.findLocation("java.base", "java/lang/Object.class");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "findLocation after close did not throw IllegalStateException");
    }

    private static void testDoubleCloseThrows() throws IOException {
        ImageReader reader = ImageReader.open(modulesFile());
        reader.close();
        boolean thrown = false;
        try {
            reader.close();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "second close did not throw IOException");
    }

    public static void main(String[] args) throws IOException {
        testFindLocationOnOpenReader();
        testFindLocationAfterCloseThrows();
        testDoubleCloseThrows();
    }
}
