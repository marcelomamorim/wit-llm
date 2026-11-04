/*
 * @test
 * @summary Verifies InflaterInputStream.available() before reading, after EOF, after close, and for an empty payload
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = "hello inflater".getBytes(StandardCharsets.UTF_8);
        byte[] comp = deflate(data);

        testAvailableBeforeRead(comp);
        testAvailableAfterEof(comp);
        testAvailableAfterClose(comp);
        testAvailableEmptyPayload();
    }

    static byte[] deflate(byte[] input) {
        Deflater d = new Deflater();
        d.setInput(input);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[64];
        while (!d.finished()) {
            int n = d.deflate(buf);
            bos.write(buf, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testAvailableBeforeRead(byte[] comp) throws IOException {
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        check(in.available() == 1, "available() before read should be 1");
        check(in.available() == 1, "available() should be stable before read");
        in.close();
    }

    static void testAvailableAfterEof(byte[] comp) throws IOException {
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        byte[] out = new byte[128];
        int total = 0;
        int n;
        int guard = 0;
        while ((n = in.read(out, 0, out.length)) != -1) {
            total += n;
            if (++guard > 10000) {
                throw new RuntimeException("read loop did not terminate");
            }
        }
        check(total == "hello inflater".length(), "unexpected decompressed length");
        check(in.available() == 0, "available() after EOF should be 0");
        check(in.available() == 0, "available() after EOF should remain 0");
        in.close();
    }

    static void testAvailableAfterClose(byte[] comp) throws IOException {
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        in.close();
        boolean threw = false;
        try {
            in.available();
        } catch (IOException e) {
            threw = true;
            check("Stream closed".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
        check(threw, "available() after close should throw IOException");
    }

    static void testAvailableEmptyPayload() throws IOException {
        byte[] comp = deflate(new byte[0]);
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        check(in.available() == 1, "available() before reading empty payload should be 1");
        check(in.read() == -1, "empty payload should produce EOF");
        check(in.available() == 0, "available() after EOF of empty payload should be 0");
        in.close();
    }
}
