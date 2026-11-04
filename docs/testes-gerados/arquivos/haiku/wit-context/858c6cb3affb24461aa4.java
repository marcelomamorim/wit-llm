/*
 * @test
 * @summary Witup generated checks for JarURLConnection.getContent failure paths without a backing jar
 * @modules java.base/sun.net.www.protocol.jar
 * @run main JarURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import sun.net.www.protocol.jar.JarURLConnection;

public class JarURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testMissingSeparatorIsRejected();
        testGetContentWithEntryFailsWhenJarMissing();
        testGetContentWithoutEntryFailsWhenJarMissing();
    }

    static void testMissingSeparatorIsRejected() throws Exception {
        URL url = new URL("file:/tmp/witup-notajar.txt");
        try {
            new JarURLConnection(url, null);
        } catch (MalformedURLException expected) {
            return;
        }
        throw new RuntimeException("expected MalformedURLException for URL without '!/'");
    }

    static void testGetContentWithEntryFailsWhenJarMissing() throws Exception {
        URL url = new URL("jar:file:/nonexistent/witup-missing.jar!/entry.txt");
        JarURLConnection conn = new JarURLConnection(url, null);
        try {
            conn.getContent();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException from getContent with missing jar");
    }

    static void testGetContentWithoutEntryFailsWhenJarMissing() throws Exception {
        URL url = new URL("jar:file:/nonexistent/witup-missing.jar!/");
        JarURLConnection conn = new JarURLConnection(url, null);
        try {
            conn.getContent();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException from getContent on jar root with missing jar");
    }
}
