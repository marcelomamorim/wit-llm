/* @test
   @summary Tests for java.io.File.toURL() method.
   @run main FileWitupTest_P2baaedb328e6
 */
import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;
public class FileWitupTest_P2baaedb328e6 {
    public static void main(String[] args) throws Exception {
        testToURLWithValidFile();
        testToURLWithInvalidFile();
    }
    private static void testToURLWithValidFile() throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir"), "testfile.txt");
        URL url = null;
        try {
            url = file.toURL();
        } catch (MalformedURLException e) {
            throw new AssertionError("Unexpected MalformedURLException for valid file", e);
        }
        if (url == null) {
            throw new AssertionError("toURL() returned null for valid file");
        }
        String urlString = url.toString();
        if (!urlString.startsWith("file:")) {
            throw new AssertionError("URL does not start with 'file:': " + urlString);
        }
    }
    private static void testToURLWithInvalidFile() {
        File invalidFile = new File("/invalid/path/\0");
        try {
            invalidFile.toURL();
            throw new AssertionError("Expected MalformedURLException was not thrown");
        } catch (MalformedURLException e) {
            // expected
        }
    }
}
