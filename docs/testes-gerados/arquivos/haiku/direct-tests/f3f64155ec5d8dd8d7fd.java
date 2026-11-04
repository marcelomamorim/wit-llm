/*
 * @test
 * @summary read(byte[]) must delegate to read(byte[], int, int) with offset 0 and the full array length
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.URL;

public class HttpURLConnectionWitupTest {

    static class Recorder extends sun.net.www.protocol.http.HttpURLConnection {
        int calls;
        int lastOff = -1;
        int lastLen = -1;

        Recorder(URL u) {
            super(u, "localhost", 8080);
        }

        @Override
        public int read(byte[] b, int off, int len) {
            calls++;
            lastOff = off;
            lastLen = len;
            return 42;
        }
    }

    public static void main(String[] args) throws Exception {
        URL u = new URL("http://localhost:8080/witup");
        Recorder r = new Recorder(u);

        byte[] buf = new byte[16];
        int n = r.read(buf);
        if (n != 42) {
            throw new RuntimeException("unexpected return value: " + n);
        }
        if (r.calls != 1) {
            throw new RuntimeException("expected one delegation, got " + r.calls);
        }
        if (r.lastOff != 0) {
            throw new RuntimeException("expected offset 0, got " + r.lastOff);
        }
        if (r.lastLen != 16) {
            throw new RuntimeException("expected length 16, got " + r.lastLen);
        }

        byte[] empty = new byte[0];
        int m = r.read(empty);
        if (m != 42) {
            throw new RuntimeException("unexpected return value for empty array: " + m);
        }
        if (r.calls != 2 || r.lastOff != 0 || r.lastLen != 0) {
            throw new RuntimeException("empty array delegation mismatch: calls=" + r.calls
                    + " off=" + r.lastOff + " len=" + r.lastLen);
        }

        boolean npe = false;
        try {
            r.read((byte[]) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null buffer");
        }
        if (r.calls != 2) {
            throw new RuntimeException("null buffer must not reach read(byte[],int,int), calls=" + r.calls);
        }
    }
}
