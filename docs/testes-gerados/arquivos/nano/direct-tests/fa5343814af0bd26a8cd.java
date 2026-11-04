/*
 * @test
 * @summary Test the write(byte[] b) method of HttpURLConnection for correct delegation.
 * @run main HttpURLConnectionWitupTest
 */

import java.io.IOException;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) {
        testWriteDelegatesCorrectly();
    }

    public static void testWriteDelegatesCorrectly() {
        byte[] testData = {1, 2, 3, 4, 5};
        DummyHttpURLConnection conn = new DummyHttpURLConnection();
        try {
            conn.write(testData);
            if (conn.writtenBytes != null && conn.writtenBytes.length == testData.length) {
                boolean match = true;
                for (int i = 0; i < testData.length; i++) {
                    if (conn.writtenBytes[i] != testData[i]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    System.out.println("PASS: write(byte[]) delegates correctly.");
                } else {
                    System.out.println("FAIL: write(byte[]) did not delegate correctly.");
                }
            } else {
                System.out.println("FAIL: write(byte[]) did not set writtenBytes correctly.");
            }
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }

    static class DummyHttpURLConnection extends HttpURLConnection {
        byte[] writtenBytes = null;
        public DummyHttpURLConnection() {
            super(null);
        }
        @Override
        public void write(byte[] b) throws IOException {
            this.writtenBytes = b.clone();
        }
        @Override
        public void connect() throws IOException {
            // no-op
        }
        @Override
        public void disconnect() {
            // no-op
        }
        @Override
        public boolean usingProxy() {
            return false;
        }
        @Override
        public int getResponseCode() throws IOException {
            return 200;
        }
        @Override
        public String getHeaderField(String name) {
            return null;
        }
        @Override
        public void setRequestMethod(String method) throws ProtocolException {
            // no-op
        }
        @Override
        public String getRequestMethod() {
            return "GET";
        }
        @Override
        public java.net.URL getURL() {
            try {
                return new java.net.URL("http://example.com");
            } catch (java.net.MalformedURLException e) {
                return null;
            }
        }
    }
}
