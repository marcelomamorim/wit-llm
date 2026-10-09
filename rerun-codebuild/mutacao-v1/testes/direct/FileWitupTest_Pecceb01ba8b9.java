/*
 * @test
 * @summary Test java.io.File.toURL() method for valid and invalid paths
 * @run main FileWitupTest_Pecceb01ba8b9
 */

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

public class FileWitupTest_Pecceb01ba8b9 {
    public static void main(String[] args) throws Exception {
        testValidFile();
        testInvalidFile();
    }

    static void testValidFile() throws Exception {
        File file = new File(System.getProperty("user.dir"));
        URL url = file.toURL();
        if (url == null) {
            throw new AssertionError("Expected non-null URL for valid file");
        }
        String protocol = url.getProtocol();
        if (!"file".equals(protocol)) {
            throw new AssertionError("Expected protocol 'file', but got: " + protocol);
        }
        String path = url.getPath();
        if (path == null || !path.contains(System.getProperty("user.dir"))) {
            throw new AssertionError("URL path does not contain expected directory: " + path);
        }
        System.out.println("PASS: testValidFile");
    }

    static void testInvalidFile() {
        try {
            File invalidFile = new File("/invalid/path/\0");
            invalidFile.toURL();
            throw new AssertionError("Expected MalformedURLException for invalid file path");
        } catch (MalformedURLException e) {
            System.out.println("PASS: testInvalidFile");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
