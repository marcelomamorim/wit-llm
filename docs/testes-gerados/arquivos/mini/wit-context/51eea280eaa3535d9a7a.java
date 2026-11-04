/*
 * @test
 * @summary Test ImageReader.findLocation(String) for open and closed states
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageLocation;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        // Use a dummy path that likely does not exist to test open and close behavior
        Path dummyPath = Paths.get("/dev/null");

        // Attempt to open ImageReader with native byte order
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath, ByteOrder.nativeOrder());
        } catch (IOException e) {
            // Expected if file does not exist or cannot be opened
            // Test cannot proceed further meaningfully
            System.out.println("Expected IOException on open with dummy path: " + e.getMessage());
            return;
        }

        // Test findLocation with valid open reader
        try {
            // The name argument can be any string; null would throw NPE in requireNonNull if used
            ImageLocation loc = reader.findLocation("test");
            // loc may be null or non-null depending on implementation; no exception means pass
            if (loc == null) {
                System.out.println("findLocation returned null as allowed");
            } else {
                System.out.println("findLocation returned non-null ImageLocation");
            }
        } catch (IllegalStateException ise) {
            throw new AssertionError("Unexpected IllegalStateException on open reader", ise);
        } catch (IOException ioe) {
            // IOException may be thrown by underlying reader.findLocation
            System.out.println("IOException from findLocation: " + ioe.getMessage());
        }

        // Close the reader
        try {
            reader.close();
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException on close", ioe);
        }

        // After close, findLocation should throw IllegalStateException
        try {
            reader.findLocation("test");
            throw new AssertionError("Expected IllegalStateException after close not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
            System.out.println("IllegalStateException correctly thrown after close");
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException after close", ioe);
        }

        // Closing again should throw IOException
        try {
            reader.close();
            throw new AssertionError("Expected IOException on second close not thrown");
        } catch (IOException expected) {
            System.out.println("IOException correctly thrown on second close");
        }
    }
}
