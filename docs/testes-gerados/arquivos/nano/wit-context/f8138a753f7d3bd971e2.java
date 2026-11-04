/*
 * @test
 * @summary Test for ImageReaderFactory.get(Path jimage) method, focusing on null input handling.
 * @run main ImageReaderFactoryWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReaderFactory;

public class ImageReaderFactoryWitupTest {

    public static void main(String[] args) {
        testGetWithNullPath();
        testGetWithValidPath();
    }

    private static void testGetWithNullPath() {
        try {
            ImageReaderFactory.get(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            System.out.println("Passed: NullPointerException thrown as expected for null input");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testGetWithValidPath() {
        Path validPath = Paths.get("/path/to/valid/image");
        try {
            ImageReader reader = ImageReaderFactory.get(validPath);
            if (reader != null) {
                System.out.println("Passed: get() returned non-null ImageReader for valid path");
            } else {
                System.err.println("Failed: get() returned null for valid path");
            }
        } catch (IOException e) {
            System.err.println("Failed: IOException thrown during get() with valid path: " + e);
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
