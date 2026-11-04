/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.write(byte[] b) method
 * @run main HttpURLConnectionWitupTest
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteWithEmptyArray();
        testWriteWithNonEmptyArray();
    }

    private static void testWriteWithEmptyArray() throws Exception {
        HttpURLConnection conn = createHttpURLConnection();
        byte[] empty = new byte[0];
        conn.write(empty); // should not throw
    }

    private static void testWriteWithNonEmptyArray() throws Exception {
        HttpURLConnection conn = createHttpURLConnection();
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        conn.write(data); // should call write(b,0,b.length) without error
    }

    private static HttpURLConnection createHttpURLConnection() throws MalformedURLException {
        // Use public constructor with URL and Proxy
        URL url = new URL("http://example.com");
        Proxy proxy = Proxy.NO_PROXY;
        return new HttpURLConnection(url, proxy) {
            @Override
            public void connect() throws IOException {}
            @Override
            public void disconnect() {}
            @Override
            public boolean usingProxy() { return false; }
            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                // Minimal override to allow testing write(byte[] b)
                if (b == null) {
                    throw new NullPointerException();
                }
                if (off < 0 || len < 0 || off + len > b.length) {
                    throw new IndexOutOfBoundsException();
                }
                // no-op
            }
        };
    }
}
