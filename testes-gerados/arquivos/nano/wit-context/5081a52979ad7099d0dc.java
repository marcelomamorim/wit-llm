/*
 * @test
 * @summary Test for java.io.File.toURL() method.
 * @run main FileWitupTest
 */

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

public class FileWitupTest {

    public static void main(String[] args) {
        testToURLWithValidFile();
        testToURLWithInvalidFile();
    }

    static void testToURLWithValidFile() {
        try {
            File file = new File(System.getProperty("java.io.tmpdir"));
            URL url = file.toURL();
            if (url == null) {
                throw new AssertionError("Expected non-null URL");
            }
        } catch (MalformedURLException e) {
            throw new AssertionError("Unexpected MalformedURLException", e);
        }
    }

    static void testToURLWithInvalidFile() {
        try {
            // Create a File object with a path that triggers MalformedURLException
            File invalidFile = new File("\0");
            invalidFile.toURL();
            throw new AssertionError("Expected MalformedURLException was not thrown");
        } catch (MalformedURLException e) {
            // Expected exception
        }
    }
}
