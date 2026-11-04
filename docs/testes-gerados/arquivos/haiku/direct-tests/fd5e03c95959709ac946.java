/*
 * @test
 * @summary Verify that the no-arg PipedInputStream constructor uses the default 1024-byte pipe buffer
 * @run main PipedInputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedInputStreamWitupTest {

    static class Probe extends PipedInputStream {
        Probe() {
            super();
        }

        int bufferLength() {
            return buffer.length;
        }

        static int defaultPipeSize() {
            return PIPE_SIZE;
        }
    }

    public static void main(String[] args) throws Exception {
        testNewStreamHasNoAvailableBytes();
        testBufferSizeMatchesDefault();
        testFullDefaultBufferIsReadableInOrder();
    }

    static void testNewStreamHasNoAvailableBytes() throws IOException {
        PipedInputStream in = new PipedInputStream();
        int avail = in.available();
        if (avail != 0) {
            throw new AssertionError("expected 0 available bytes, got " + avail);
        }
    }

    static void testBufferSizeMatchesDefault() {
        Probe probe = new Probe();
        if (Probe.defaultPipeSize() != 1024) {
            throw new AssertionError("unexpected PIPE_SIZE: " + Probe.defaultPipeSize());
        }
        if (probe.bufferLength() != 1024) {
            throw new AssertionError("expected buffer length 1024, got " + probe.bufferLength());
        }
    }

    static void testFullDefaultBufferIsReadableInOrder() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        byte[] data = new byte[1024];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        out.write(data);

        int avail = in.available();
        if (avail != 1024) {
            throw new AssertionError("expected 1024 available bytes, got " + avail);
        }

        int first = in.read();
        if (first != 0) {
            throw new AssertionError("expected first byte 0, got " + first);
        }

        byte[] rest = new byte[1023];
        int n = in.read(rest, 0, rest.length);
        if (n != 1023) {
            throw new AssertionError("expected 1023 bytes read, got " + n);
        }
        if (rest[0] != 1 || rest[1022] != (byte) 1023) {
            throw new AssertionError("data order mismatch");
        }

        out.close();
        in.close();
    }
}
