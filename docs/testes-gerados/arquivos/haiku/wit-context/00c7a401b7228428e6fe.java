/*
 * @test
 * @summary Checks that a protected BasicImageReader constructor rejects a missing image file with IOException
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    static class Reader extends BasicImageReader {
        Reader(Path p) throws IOException {
            super(p, ByteOrder.nativeOrder());
        }
    }

    public static void main(String[] args) throws Exception {
        Path missing = Paths.get("witup-missing-" + System.nanoTime() + ".jimage");
        try {
            new Reader(missing);
            throw new RuntimeException("expected IOException for missing image file");
        } catch (IOException expected) {
            // expected
        }
    }
}
