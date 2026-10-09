/*
 * @test
 * @summary Test java.io.File.toURL() method for valid and invalid paths
 * @run main FileWitupTest_P815fccdd45c3
 */

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

public class FileWitupTest_P815fccdd45c3 {

    public static void main(String[] args) {
        testValidFilePath();
        testInvalidFilePath();
    }

    static void testValidFilePath() {
        try {
            File file = new File(System.getProperty("user.dir"));
            URL url = file.toURL();
            if (url == null || !url.toString().startsWith("file:")) {
                throw new AssertionError("Expected URL starting with 'file:', but got: " + url);
            }
        } catch (MalformedURLException e) {
            throw new AssertionError("Unexpected MalformedURLException for valid path", e);
        }
    }

    static void testInvalidFilePath() {
        try {
            File file = new File("\0");
            file.toURL();
            throw new AssertionError("Expected MalformedURLException for invalid path");
        } catch (MalformedURLException e) {
            // Expected exception
        }
    }
}
