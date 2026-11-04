/*
 * @test
 * @summary Verify ChunkedInputStream.available() reports buffered chunk data and is stable across calls
 * @modules java.base/sun.net.www.http java.base/sun.net.www java.base/sun.net
 * @run main ChunkedInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.net.www.MessageHeader;
import sun.net.www.http.ChunkedInputStream;

public class ChunkedInputStreamWitupTest {

    private static ChunkedInputStream newStream(String body) {
        InputStream in = new ByteArrayInputStream(body.getBytes());
        return new ChunkedInputStream(in, null, null);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAvailableReportsChunkDataOnFirstCall() throws IOException {
        ChunkedInputStream cis = newStream("5\r\nhello\r\n0\r\n\r\n");
        int avail = cis.available();
        check(avail == 5, "expected available()==5 but was " + avail);
    }

    static void testAvailableIsStableOnRepeatedCalls() throws IOException {
        ChunkedInputStream cis = newStream("5\r\nhello\r\n0\r\n\r\n");
        int first = cis.available();
        int second = cis.available();
        check(first == second, "available() changed between calls: " + first + " vs " + second);
        check(second == 5, "expected 5 but was " + second);
    }

    static void testAvailableDecreasesAfterRead() throws IOException {
        ChunkedInputStream cis = newStream("5\r\nhello\r\n0\r\n\r\n");
        check(cis.available() == 5, "initial available() should be 5");
        int b = cis.read();
        check(b == 'h', "expected 'h' but was " + b);
        int avail = cis.available();
        check(avail == 4, "expected available()==4 after one read but was " + avail);
    }

    public static void main(String[] args) throws Exception {
        testAvailableReportsChunkDataOnFirstCall();
        testAvailableIsStableOnRepeatedCalls();
        testAvailableDecreasesAfterRead();
    }
}
