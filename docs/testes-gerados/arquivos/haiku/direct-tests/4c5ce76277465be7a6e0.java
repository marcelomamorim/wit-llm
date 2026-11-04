/*
 * @test
 * @summary Verify DeflaterInputStream(InputStream, Deflater) delegates to the 3-arg constructor with a 512-byte buffer and validates its arguments
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;

public class DeflaterInputStreamWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static class Probe extends DeflaterInputStream {
        Probe(InputStream in, Deflater d) {
            super(in, d);
        }

        int bufLen() {
            return buf.length;
        }

        Deflater deflater() {
            return def;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectNPE(Action a, String msg) {
        try {
            a.run();
        } catch (NullPointerException expected) {
            return;
        } catch (Exception e) {
            throw new RuntimeException(msg + ": unexpected " + e, e);
        }
        throw new RuntimeException(msg + ": no NullPointerException");
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] tmp = new byte[64];
        int n;
        while ((n = in.read(tmp, 0, tmp.length)) != -1) {
            bos.write(tmp, 0, n);
        }
        return bos.toByteArray();
    }

    static byte[] inflate(byte[] comp, int originalLen) throws Exception {
        Inflater inf = new Inflater();
        inf.setInput(comp);
        byte[] res = new byte[originalLen];
        int n = inf.inflate(res);
        inf.end();
        return Arrays.copyOf(res, n);
    }

    public static void main(String[] args) throws Exception {
        byte[] data = "hello hello hello deflate stream".getBytes(StandardCharsets.UTF_8);

        Deflater d = new Deflater();
        Probe p = new Probe(new ByteArrayInputStream(data), d);
        check(p.bufLen() == 512, "default buffer length must be 512, was " + p.bufLen());
        check(p.deflater() == d, "deflater must be the supplied instance");

        byte[] comp = readAll(p);
        p.close();
        byte[] restored = inflate(comp, data.length);
        check(Arrays.equals(restored, data), "round-trip data mismatch");

        expectNPE(() -> new DeflaterInputStream(null, new Deflater()),
                  "null input must throw NPE");
        expectNPE(() -> new DeflaterInputStream(new ByteArrayInputStream(data), null),
                  "null deflater must throw NPE");
    }
}
