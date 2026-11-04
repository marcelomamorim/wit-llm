/*
 * @test
 * @summary Tests the 2-argument DeflaterOutputStream constructor: default 512-byte buffer, null checks and compressed round trip
 * @run main DeflaterOutputStreamWitupTest
 */
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class DeflaterOutputStreamWitupTest {
    static class Probe extends DeflaterOutputStream {
        Probe(OutputStream out, Deflater def) {
            super(out, def);
        }

        int bufferLength() {
            return buf.length;
        }
    }

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Deflater def = new Deflater();
        Probe p = new Probe(bos, def);
        if (p.bufferLength() != 512) {
            throw new RuntimeException("unexpected buffer size: " + p.bufferLength());
        }

        byte[] data = new byte[1000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i % 7);
        }
        p.write(data);
        p.close();
        def.end();

        byte[] comp = bos.toByteArray();
        if (comp.length == 0) {
            throw new RuntimeException("no compressed output");
        }

        Inflater inf = new Inflater();
        inf.setInput(comp);
        byte[] res = new byte[data.length + 100];
        int n = 0;
        while (!inf.finished()) {
            int k = inf.inflate(res, n, res.length - n);
            if (k == 0 && (inf.needsInput() || inf.needsDictionary())) {
                break;
            }
            n += k;
        }
        inf.end();

        if (n != data.length) {
            throw new RuntimeException("length mismatch: " + n);
        }
        for (int i = 0; i < data.length; i++) {
            if (res[i] != data[i]) {
                throw new RuntimeException("mismatch at index " + i);
            }
        }

        try {
            new DeflaterOutputStream(null, new Deflater());
            throw new RuntimeException("no NPE for null out");
        } catch (NullPointerException expected) {
        }

        try {
            new DeflaterOutputStream(new ByteArrayOutputStream(), null);
            throw new RuntimeException("no NPE for null def");
        } catch (NullPointerException expected) {
        }
    }
}
