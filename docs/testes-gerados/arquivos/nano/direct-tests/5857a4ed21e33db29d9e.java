/*
 * @test
 * @summary Test getResource method of BasicImageReader for various resource names.
 * @run main BasicImageReaderWitupTest
 */
import java.io.IOException;
import java.nio.file.Paths;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws IOException {
        // Initialize BasicImageReader with a dummy path and ByteOrder.LITTLE_ENDIAN
        BasicImageReader reader = new BasicImageReader(Paths.get("dummy/path"), java.nio.ByteOrder.LITTLE_ENDIAN);
        // Test with a resource name that is expected to exist
        byte[] resourceBytes = reader.getResource("existingResourceName");
        System.out.println("Resource bytes length: " + (resourceBytes != null ? resourceBytes.length : "null"));
        // Test with a resource name that is unlikely to exist
        byte[] nonExistentResource = reader.getResource("nonExistentResource");
        System.out.println("Non-existent resource: " + (nonExistentResource != null ? "found" : "null"));
        // Test with null resource name to verify NullPointerException
        try {
            reader.getResource(null);
            System.out.println("Null resource name did not throw exception");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught as expected");
        }
    }
}
