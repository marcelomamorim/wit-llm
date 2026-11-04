/* @test    @summary Test getByteOrder() method of ImageReader class    @run main ImageReaderWitupTest */

import java.io.IOException;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetByteOrderReturnsCorrectOrder();
        testGetByteOrderAfterCloseThrows();
    }

    private static void testGetByteOrderReturnsCorrectOrder() throws IOException {
        // Setup: create a dummy SharedImageReader with a specific ByteOrder
        // Since SharedImageReader is not accessible, we simulate the behavior by mocking or assuming the method works as intended.
        // For the purpose of this test, we assume open() returns an ImageReader with native order.
        // Note: In real scenario, a mock or a test double would be used.
        Path dummyPath = java.nio.file.Paths.get("/dev/null");
        ImageReader reader = ImageReader.open(dummyPath);
        ByteOrder order = reader.getByteOrder();
        // Expect the order to be the native order of the platform
        ByteOrder expectedOrder = ByteOrder.nativeOrder();
        if (order != expectedOrder) {
            throw new AssertionError("Expected ByteOrder: " + expectedOrder + ", but got: " + order);
        }
        reader.close();
    }

    private static void testGetByteOrderAfterCloseThrows() throws IOException {
        Path dummyPath = java.nio.file.Paths.get("/dev/null");
        ImageReader reader = ImageReader.open(dummyPath);
        reader.close();
        boolean exceptionThrown = false;
        try {
            reader.getByteOrder();
        } catch (IOException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException when calling getByteOrder() after close");
        }
    }
}
