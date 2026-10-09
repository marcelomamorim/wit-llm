/*
 * @test
 * @summary Test java.io.File.toURL() method behavior
 * @run main FileWitupTest_Pa1fd218df76a
 */

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

public class FileWitupTest_Pa1fd218df76a {
    public static void main(String[] args) throws Exception {
        testValidFileToURL();
        testInvalidFileToURL();
    }

    private static void testValidFileToURL() throws Exception {
        // Create a File object with a valid path
        File file = new File(".");
        URL url = file.toURL();
        // The URL protocol must be "file"
        if (!"file".equals(url.getProtocol())) {
            throw new AssertionError("Expected protocol 'file' but got: " + url.getProtocol());
        }
        // The URL path must end with the file's absolute path (slashified)
        String absPath = file.getAbsolutePath().replace(java.io.File.separatorChar, '/');
        String urlPath = url.getPath();
        if (!urlPath.endsWith(absPath)) {
            throw new AssertionError("URL path does not end with absolute path. URL path: " + urlPath + ", abs path: " + absPath);
        }
    }

    private static void testInvalidFileToURL() {
        // Create a subclass of File that returns true for isInvalid() to trigger exception
        File invalidFile = new File("invalid") {
            @Override
            public boolean isInvalid() {
                return true;
            }
        };
        try {
            invalidFile.toURL();
            throw new AssertionError("Expected MalformedURLException for invalid file path");
        } catch (MalformedURLException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Invalid file path")) {
                throw new AssertionError("MalformedURLException message unexpected: " + msg);
            }
        }
    }
}
