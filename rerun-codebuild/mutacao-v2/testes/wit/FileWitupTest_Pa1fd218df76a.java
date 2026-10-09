/*
 * @test
 * @summary Test java.io.File.toURL() for valid and invalid paths
 * @run main FileWitupTest_Pa1fd218df76a
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class FileWitupTest_Pa1fd218df76a {
    public static void main(String[] args) throws Exception {
        testToURLValidPath();
        testToURLInvalidPath();
    }

    private static void testToURLValidPath() throws Exception {
        // Create a File with a valid path (current directory)
        File f = new File(".");
        URL url = f.toURL();
        if (url == null) {
            throw new RuntimeException("toURL() returned null for valid path");
        }
        String protocol = url.getProtocol();
        if (!"file".equals(protocol)) {
            throw new RuntimeException("Expected protocol 'file' but got: " + protocol);
        }
        String path = url.getPath();
        if (path == null || path.isEmpty()) {
            throw new RuntimeException("URL path is null or empty");
        }
    }

    private static void testToURLInvalidPath() {
        // Since we cannot directly create an invalid File path that triggers isInvalid(),
        // we test that no MalformedURLException is thrown for a normal path.
        // The WIT expaths for MalformedURLException are not reproducible with public API,
        // so this test only asserts that no exception is thrown for a normal path.
        try {
            File f = new File(""); // empty string path is valid but points to current dir
            URL url = f.toURL();
            if (url == null) {
                throw new RuntimeException("toURL() returned null for empty path");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unexpected MalformedURLException for empty path", e);
        }
    }
}
