/*
 * @test
 * @summary Verify ImageReader.getEntryNames returns entries for an open image and throws IllegalStateException after close
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(modules, ByteOrder.nativeOrder());
        try {
            String[] names = reader.getEntryNames();
            if (names == null || names.length == 0) {
                throw new RuntimeException("getEntryNames returned no entries for an open image");
            }
            for (String name : names) {
                if (name == null) {
                    throw new RuntimeException("getEntryNames returned a null entry");
                }
            }
            String[] again = reader.getEntryNames();
            if (again.length != names.length) {
                throw new RuntimeException("getEntryNames is not stable across calls");
            }
        } finally {
            reader.close();
        }

        try {
            reader.getEntryNames();
            throw new RuntimeException("Expected IllegalStateException after close");
        } catch (IllegalStateException expected) {
            // expected: requireOpen() rejects closed reader
        }
    }
}
