/*
 * @test
 * @summary Test for ImageReader.getHeader() method, focusing on exception path when closed.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetHeaderWhenClosed();
        testGetHeaderWhenOpen();
    }

    private static void testGetHeaderWhenClosed() {
        try {
            // Setup: open and then close the ImageReader to simulate closed state
            Path dummyPath = java.nio.file.Paths.get("/dev/null");
            ImageReader reader = ImageReader.open(dummyPath);
            reader.close();
            // Action: call getHeader() after close
            reader.getHeader();
            System.out.println("FAIL: Expected IOException when calling getHeader() after close.");
        } catch (IOException e) {
            if (e.getMessage().equals("image file closed")) {
                System.out.println("PASS: Correct exception thrown when calling getHeader() after close.");
            } else {
                System.out.println("FAIL: Unexpected IOException message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testGetHeaderWhenOpen() {
        try {
            Path dummyPath = java.nio.file.Paths.get("/dev/null");
            ImageReader reader = ImageReader.open(dummyPath);
            // Action: call getHeader() when open
            reader.getHeader();
            System.out.println("PASS: getHeader() executed successfully when open.");
            reader.close();
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception when calling getHeader() when open: " + e);
        }
    }
}
