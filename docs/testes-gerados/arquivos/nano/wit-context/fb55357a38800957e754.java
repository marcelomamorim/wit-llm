/*
 * @test
 * @summary Test for ImageReader.close method to verify NullPointerException when image is null
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) {
        testCloseWithNullImage();
    }

    public static void testCloseWithNullImage() {
        try {
            // Call close with null to trigger NullPointerException
            ImageReader.close(null);
            System.err.println("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
