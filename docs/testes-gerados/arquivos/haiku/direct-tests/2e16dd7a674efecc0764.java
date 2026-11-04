/*
 * @test
 * @summary Verifies BasicImageReader.open rejects a null path and a missing image file
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullPathThrowsNPE();
        testMissingFileThrowsIOException();
    }

    static void testNullPathThrowsNPE() {
        try {
            BasicImageReader.open(null);
            throw new RuntimeException("expected NullPointerException for null path");
        } catch (NullPointerException expected) {
            // expected: constructor calls Objects.requireNonNull(path)
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException for null path", e);
        }
    }

    static void testMissingFileThrowsIOException() {
        Path missing = Paths.get("witup-missing-" + System.nanoTime() + ".jimage");
        try {
            BasicImageReader reader = BasicImageReader.open(missing);
            reader.close();
            throw new RuntimeException("expected IOException for missing image file");
        } catch (IOException expected) {
            // expected: the image file cannot be opened
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception for missing file", e);
        }
    }
}
