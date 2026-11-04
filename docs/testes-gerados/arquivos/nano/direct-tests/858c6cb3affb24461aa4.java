/*
 * @test
 * @summary Test getContent() method of JarURLConnection for both jar file and entry cases
 * @run main JarURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import sun.net.www.protocol.jar.JarURLConnection;

public class JarURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentWithJarFile();
        testGetContentWithEntry();
    }

    private static void testGetContentWithJarFile() throws IOException {
        URL url = new URL("jar:file:/dummy/path/test.jar!/");
        JarURLConnection conn = (JarURLConnection) url.openConnection();
        Object result = conn.getContent();
        if (result != null && result instanceof java.util.jar.JarFile) {
            System.out.println("testGetContentWithJarFile: PASSED");
        } else {
            System.out.println("testGetContentWithJarFile: FAILED");
        }
    }

    private static void testGetContentWithEntry() throws IOException {
        URL url = new URL("jar:file:/dummy/path/test.jar!/com/example/Entry.class");
        JarURLConnection conn = (JarURLConnection) url.openConnection();
        Object result = conn.getContent();
        if (result != null && !(result instanceof java.util.jar.JarFile)) {
            System.out.println("testGetContentWithEntry: PASSED");
        } else {
            System.out.println("testGetContentWithEntry: FAILED");
        }
    }
}
