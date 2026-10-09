/* @test    @summary Tests for java.io.File.toURL() method    @run main FileWitupTest_P10ef0363c8b0 */

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

public class FileWitupTest_P10ef0363c8b0 {

    public static void main(String[] args) throws Exception {
        testToURLWithValidFile();
        testToURLWithInvalidFile();
        testToURLWithNull();
    }

    private static void testToURLWithValidFile() throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir"));
        URL url = file.toURL();
        if (url == null) {
            throw new AssertionError("Expected non-null URL for valid file");
        }
        String urlStr = url.toString();
        if (!urlStr.startsWith("file:")) {
            throw new AssertionError("URL does not start with 'file:'");
        }
    }

    private static void testToURLWithInvalidFile() {
        File file = new File("invalid");
        // Manipulate the file object to simulate an invalid path if needed
        // Here, we assume that getAbsolutePath() returns an invalid URL string
        try {
            // Force isInvalid() to be true by overriding if necessary
            // Since isInvalid() is not public, we test normally; assume invalid path triggers exception
            // For the test, simulate an invalid path
            // Note: in real scenario, we'd need a subclass or reflection; here, we simulate by passing a path that causes MalformedURLException
            File invalidFile = new File("\\invalid\path");
            invalidFile.toURL();
            throw new AssertionError("Expected MalformedURLException for invalid file") ;
        } catch (MalformedURLException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testToURLWithNull() {
        try {
            // Cannot create a File with null, so simulate by reflection or skip
            // As per constructor, null is not accepted, so skipping this test
            // Alternatively, test a null reference, expecting NullPointerException
            File file = null;
            try {
                file.toURL();
                throw new AssertionError("Expected NullPointerException");
            } catch (NullPointerException e) {
                // Expected
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
