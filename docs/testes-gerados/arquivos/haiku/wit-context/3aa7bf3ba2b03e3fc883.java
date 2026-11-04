/*
 * @test
 * @summary Verifies DataOutputStream.write(int) writes the low eight bits and updates the written counter only on success
 * @run main DataOutputStreamWitupTest
 */
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class DataOutputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class Probe extends DataOutputStream {
        Probe(OutputStream out) {
            super(out);
        }

        int count() {
            return written;
        }
    }

    static class FailingOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            throw new IOException("boom");
        }
    }

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Probe p = new Probe(bos);

        p.write(0x1FF);
        check(bos.size() == 1, "expected one byte written");
        check(bos.toByteArray()[0] == (byte) 0xFF, "expected low eight bits 0xFF");
        check(p.count() == 1, "expected written counter 1");

        p.write(1);
        p.write(2);
        byte[] expected = {(byte) 0xFF, 1, 2};
        check(Arrays.equals(bos.toByteArray(), expected), "unexpected byte sequence");
        check(p.count() == 3, "expected written counter 3");

        p.write(-1);
        check(bos.size() == 4, "expected four bytes written");
        check(bos.toByteArray()[3] == (byte) 0xFF, "negative value must write low eight bits");
        check(p.count() == 4, "expected written counter 4");

        Probe failing = new Probe(new FailingOutputStream());
        boolean thrown = false;
        try {
            failing.write(7);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException from underlying stream");
        check(failing.count() == 0, "counter must not change when underlying write fails");
    }
}
