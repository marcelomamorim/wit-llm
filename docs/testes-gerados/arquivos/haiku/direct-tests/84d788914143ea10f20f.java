/*
 * @test
 * @summary Verifies PipedInputStream(int pipeSize) sizes the internal buffer and yields data written through a connected PipedOutputStream
 * @run main PipedInputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedInputStreamWitupTest {

    static class Probe extends PipedInputStream {
        Probe(int pipeSize) {
            super(pipeSize);
        }

        int capacity() {
            return buffer.length;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        Probe minimal = new Probe(1);
        check(minimal.capacity() == 1, "pipe size 1 must allocate a 1-byte buffer");

        Probe sixteen = new Probe(16);
        check(sixteen.capacity() == 16, "pipe size 16 must allocate a 16-byte buffer");

        Probe pipe = new Probe(16);
        PipedOutputStream out = new PipedOutputStream(pipe);
        out.write(new byte[] {1, 2, 3, 4, 5});
        check(pipe.available() == 5, "available() must report 5 written bytes");
        for (int i = 0; i < 5; i++) {
            int b = pipe.read();
            check(b == i + 1, "byte " + i + " read back incorrectly: " + b);
        }
        check(pipe.available() == 0, "available() must be 0 after reading all bytes");
        out.close();
        pipe.close();

        Probe single = new Probe(1);
        PipedOutputStream singleOut = new PipedOutputStream(single);
        singleOut.write(42);
        check(single.read() == 42, "single-byte pipe must return the written byte");
        singleOut.close();
        single.close();
    }
}
