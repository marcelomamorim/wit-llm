/*
 * @test
 * @summary DataOutputStream.write(int) writes the low eight bits and updates the written counter
 * @run main DataOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DataOutputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class CountingDOS extends DataOutputStream {
        CountingDOS(OutputStream out) {
            super(out);
        }

        void setWritten(int value) {
            written = value;
        }

        int getWritten() {
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
        // Writes only the low eight bits of the argument
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CountingDOS d = new CountingDOS(bos);
        d.write(0x1FF);
        check(bos.size() == 1, "expected one byte written");
        check(bos.toByteArray()[0] == (byte) 0xFF, "expected low eight bits 0xFF");
        check(d.getWritten() == 1, "expected written counter 1 after one write");

        // Counter accumulates across calls
        d.write(65);
        d.write(66);
        check(bos.size() == 3, "expected three bytes written");
        check(d.getWritten() == 3, "expected written counter 3");

        // Exception from underlying stream propagates and counter is not incremented
        CountingDOS failing = new CountingDOS(new FailingOutputStream());
        boolean thrown = false;
        try {
            failing.write(1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException from underlying stream");
        check(failing.getWritten() == 0, "counter must not change on failure");

        // Counter saturates at Integer.MAX_VALUE on overflow
        CountingDOS overflow = new CountingDOS(new ByteArrayOutputStream());
        overflow.setWritten(Integer.MAX_VALUE - 1);
        overflow.write(1);
        check(overflow.getWritten() == Integer.MAX_VALUE, "expected counter to reach Integer.MAX_VALUE");
        overflow.write(1);
        check(overflow.getWritten() == Integer.MAX_VALUE, "expected counter to stay at Integer.MAX_VALUE");
    }
}
