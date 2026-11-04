/*
 * @test
 * @summary Test for getResourceBuffer method in ImageReader class.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageLocation;

public class ImageReaderWitupTest {

    public static void main(String[] args) {
        testGetResourceBufferWhenClosed();
        testGetResourceBufferWhenOpen();
    }

    private static void testGetResourceBufferWhenClosed() {
        try {
            // Setup: open and then close the ImageReader
            Path dummyPath = java.nio.file.Paths.get("/dev/null");
            ImageReader reader = ImageReader.open(dummyPath);
            reader.close();
            // Attempt to invoke getResourceBuffer after close, expecting IllegalStateException
            ImageLocation loc = null; // null, as actual location is not used in this test
            try {
                reader.getResourceBuffer(loc);
                System.err.println("Expected IllegalStateException was not thrown.");
            } catch (IllegalStateException e) {
                System.out.println("Caught expected IllegalStateException when calling getResourceBuffer after close.");
            } catch (Exception e) {
                System.err.println("Unexpected exception type: " + e);
            }
        } catch (IOException e) {
            System.err.println("IOException during setup: " + e);
        }
    }

    private static void testGetResourceBufferWhenOpen() {
        try {
            // Setup: open the ImageReader
            Path dummyPath = java.nio.file.Paths.get("/dev/null");
            ImageReader reader = ImageReader.open(dummyPath);
            // Create a dummy ImageLocation (null for simplicity, as method does not use it in this test)
            ImageLocation loc = null;
            // Call getResourceBuffer, expecting no exception
            ByteBuffer buffer = reader.getResourceBuffer(loc);
            if (buffer != null) {
                System.out.println("getResourceBuffer returned a ByteBuffer as expected.");
            } else {
                System.err.println("getResourceBuffer returned null.");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("IOException during setup or execution: " + e);
        } catch (IllegalStateException e) {
            System.err.println("Unexpected IllegalStateException: " + e);
        }
    }
}
