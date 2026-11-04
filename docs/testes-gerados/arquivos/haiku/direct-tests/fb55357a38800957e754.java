/*
 * @test
 * @summary ImageReader.close() must track open ImageReader instances sharing one jimage and reject a second close on the same instance
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    static Path imagePath() {
        return Paths.get(System.getProperty("java.home"), "lib", "modules");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSecondCloseOnSameInstanceThrows() throws IOException {
        ImageReader reader = ImageReader.open(imagePath());
        reader.close();
        boolean thrown = false;
        try {
            reader.close();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "second close() on same ImageReader must throw IOException");
    }

    static void testSharedImageRemainsUsableUntilLastClose() throws IOException {
        ImageReader first = ImageReader.open(imagePath());
        ImageReader second = ImageReader.open(imagePath());

        first.close();

        boolean firstClosedRejects = false;
        try {
            first.getRootDirectory();
        } catch (IOException e) {
            firstClosedRejects = true;
        }
        check(firstClosedRejects, "closed ImageReader must reject getRootDirectory()");

        var root = second.getRootDirectory();
        check(root != null, "remaining ImageReader must still provide a root directory");

        second.close();
    }

    public static void main(String[] args) throws Exception {
        testSecondCloseOnSameInstanceThrows();
        testSharedImageRemainsUsableUntilLastClose();
        System.out.println("PASS");
    }
}
