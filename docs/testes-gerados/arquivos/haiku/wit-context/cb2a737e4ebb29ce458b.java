/*
 * @test
 * @summary Verifies observable chunked encoding produced by ChunkedOutputStream.write(int)
 * @modules java.base/sun.net.www.http
 * @run main ChunkedOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import sun.net.www.http.ChunkedOutputStream;

public class ChunkedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos, true, "US-ASCII");
        ChunkedOutputStream cos = new ChunkedOutputStream(ps);
        cos.write(0x141);
        cos.close();
        String out = bos.toString("US-ASCII");
        check(out.contains("1\r\nA\r\n"), "single byte chunk missing: " + out);
        check(out.endsWith("0\r\n\r\n"), "terminating chunk missing: " + out);

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        PrintStream ps2 = new PrintStream(bos2, true, "US-ASCII");
        ChunkedOutputStream cos2 = new ChunkedOutputStream(ps2);
        cos2.write('Z');
        cos2.close();
        String out2 = bos2.toString("US-ASCII");
        check(out2.contains("1\r\nZ\r\n"), "second chunk content missing: " + out2);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
