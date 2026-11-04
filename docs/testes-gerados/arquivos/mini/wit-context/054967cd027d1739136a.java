/*
 * @test
 * @summary Test KeepAliveStream.reset() throws IOException as expected
 * @run main KeepAliveStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import sun.net.ProgressSource;
import sun.net.www.http.KeepAliveStream;
import sun.net.www.http.HttpClient;

public class KeepAliveStreamWitupTest {

    static class DummyInputStream extends InputStream {
        public int read() {
            return -1; // EOF
        }
    }

    static class DummyHttpClient extends HttpClient {
        DummyHttpClient() {
            super(null, null, null, null, null, null, null, null, null, null, null, null, null);
        }
        public void closeServer() {}
        public void finished() {}
    }

    public static void main(String[] args) throws Exception {
        InputStream is = new DummyInputStream();
        ProgressSource pi = null;
        long expected = 0L;
        HttpClient hc = new DummyHttpClient();

        KeepAliveStream kas = new KeepAliveStream(is, pi, expected, hc);

        try {
            kas.reset();
            throw new RuntimeException("Expected IOException not thrown by reset()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("mark/reset not supported")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
