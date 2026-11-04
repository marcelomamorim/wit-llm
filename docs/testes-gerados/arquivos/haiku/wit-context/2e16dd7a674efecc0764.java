/*
 * @test
 * @summary Verifies that BasicImageReader.open rejects a null path with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            BasicImageReader.open(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }
}
