/*
 * @test
 * @summary Tests InflaterInputStream.skip argument validation, EOF handling, closed-stream behavior and partial skips
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        byte[] plain = "hello world".getBytes("US-ASCII");
        byte[] comp = deflate(plain);
        testNegative(comp);
        testZero(comp);
        testPartialSkip(comp);
        testSkipPastEof(comp);
        testClosed(comp);
        testLarge();
    }

    static byte[] deflate(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] tmp = new byte[1024];
        while (!d.finished()) {
            int n = d.deflate(tmp);
            bos.write(tmp, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    static InflaterInputStream stream(byte[] comp) {
        return new InflaterInputStream(new ByteArrayInputStream(comp));
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNegative(byte[] comp) throws IOException {
        InflaterInputStream in = stream(comp);
        try {
            in.skip(-1);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            in.close();
        }
    }

    static void testZero(byte[] comp) throws IOException {
        InflaterInputStream in = stream(comp);
        try {
            check(in.skip(0) == 0, "skip(0) should return 0");
            check(in.read() == 'h', "skip(0) should not consume data");
        } finally {
            in.close();
        }
    }

    static void testPartialSkip(byte[] comp) throws IOException {
        InflaterInputStream in = stream(comp);
        try {
            check(in.skip(5) == 5, "skip(5) should return 5");
            check(in.read() == ' ', "byte after skipped prefix should be space");
        } finally {
            in.close();
        }
    }

    static void testSkipPastEof(byte[] comp) throws IOException {
        InflaterInputStream in = stream(comp);
        try {
            check(in.skip(100) == 11, "skip past EOF should return available count 11");
            check(in.read() == -1, "read after EOF should return -1");
        } finally {
            in.close();
        }
    }

    static void testClosed(byte[] comp) throws IOException {
        InflaterInputStream in = stream(comp);
        in.close();
        try {
            in.skip(1);
            throw new RuntimeException("expected IOException on closed stream");
        } catch (IOException e) {
            // expected
        }
    }

    static void testLarge() throws IOException {
        byte[] big = new byte[20000];
        for (int i = 0; i < big.length; i++) {
            big[i] = (byte) (i % 251);
        }
        byte[] comp = deflate(big);
        InflaterInputStream in = stream(comp);
        try {
            check(in.skip(20000) == 20000, "skip of full large content should return 20000");
            check(in.read() == -1, "stream should be at EOF after full skip");
        } finally {
            in.close();
        }
    }
}
