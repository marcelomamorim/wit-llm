/*
 * @test
 * @summary Basic observable behavior of ChunkedInputStream.available() on a chunk-framed stream
 * @modules java.base/sun.net.www.http
 * @run main ChunkedInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.net.www.http.ChunkedInputStream;

public class ChunkedInputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        byte[] data = "5\r\nhello\r\n".getBytes("US-ASCII");
        ChunkedInputStream cis = new ChunkedInputStream(
                new ByteArrayInputStream(data), null, null);
        int avail = cis.available();
        check(avail >= 0, "available() must not be negative: " + avail);
        check(avail <= 5, "available() must not exceed chunk size 5: " + avail);
    }
}
