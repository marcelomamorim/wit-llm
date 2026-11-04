/*
 * @test
 * @summary Verify that ImageReader.open(Path, ByteOrder) rejects null arguments with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path somePath = Paths.get("modules");
        expectNpe(null, ByteOrder.nativeOrder(), "null imagePath with non-null byteOrder");
        expectNpe(null, null, "null imagePath and null byteOrder");
        expectNpe(somePath, null, "non-null imagePath with null byteOrder");
    }

    static void expectNpe(Path imagePath, ByteOrder byteOrder, String msg) {
        try {
            ImageReader.open(imagePath, byteOrder);
        } catch (NullPointerException e) {
            return;
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception for " + msg + ": " + t, t);
        }
        throw new RuntimeException("Expected NullPointerException for " + msg);
    }
}
