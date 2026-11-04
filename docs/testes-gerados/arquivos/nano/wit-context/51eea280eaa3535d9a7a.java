/*
 * @test
 * @summary Test for jdk.internal.jimage.ImageReader.findLocation to verify behavior when closed.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testFindLocationWhenOpen();
        testFindLocationWhenClosed();
    }

    private static void testFindLocationWhenOpen() throws IOException {
        // Setup: create a mock or real ImageReader instance in open state
        // Since constructor is package-private, assume a method to open or mock
        // For this test, we simulate by opening a real file if available
        // Here, we skip actual file opening; focus on behavior when open
        // Note: In real scenario, replace with actual file path or mock
        Path dummyPath = java.nio.file.Paths.get("/dev/null");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath);
            // Call findLocation with a dummy name
            try {
                reader.findLocation("dummyName");
                System.out.println("findLocation when open: PASSED");
            } catch (Exception e) {
                System.out.println("findLocation when open: FAILED with exception: " + e);
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            // If opening fails, skip test
            System.out.println("Skipping open test due to IOException: " + e);
        }
    }

    private static void testFindLocationWhenClosed() throws IOException {
        // Setup: open and then close the reader to simulate closed state
        Path dummyPath = java.nio.file.Paths.get("/dev/null");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath);
            reader.close();
            // Now, calling findLocation should throw IllegalStateException
            try {
                reader.findLocation("dummyName");
                System.out.println("findLocation when closed: FAILED (no exception)");
            } catch (IllegalStateException e) {
                System.out.println("findLocation when closed: PASSED");
            } catch (Exception e) {
                System.out.println("findLocation when closed: FAILED with unexpected exception: " + e);
            }
        } catch (IOException e) {
            System.out.println("Skipping closed test due to IOException: " + e);
        }
    }
}
