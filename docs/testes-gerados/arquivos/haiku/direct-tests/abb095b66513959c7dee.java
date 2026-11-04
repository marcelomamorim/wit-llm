/*
 * @test
 * @summary Verify PipedOutputStream.write(int) behavior when unconnected and when connected to a PipedInputStream
 * @run main PipedOutputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteUnconnectedThrowsIOException();
        testWriteConnectedViaConstructorDeliversByte();
        testWriteConnectedViaConnectDeliversByte();
        testWriteOnlyLowEightBitsAreDelivered();
    }

    static void testWriteUnconnectedThrowsIOException() {
        PipedOutputStream pos = new PipedOutputStream();
        boolean thrown = false;
        try {
            pos.write(65);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException when writing to unconnected PipedOutputStream");
        }
    }

    static void testWriteConnectedViaConstructorDeliversByte() throws IOException {
        PipedInputStream snk = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(snk);
        pos.write(0x41);
        int value = snk.read();
        if (value != 0x41) {
            throw new RuntimeException("Expected 65 but was " + value);
        }
        pos.close();
    }

    static void testWriteConnectedViaConnectDeliversByte() throws IOException {
        PipedInputStream snk = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream();
        pos.connect(snk);
        pos.write(0x5A);
        int value = snk.read();
        if (value != 0x5A) {
            throw new RuntimeException("Expected 90 but was " + value);
        }
        pos.close();
    }

    static void testWriteOnlyLowEightBitsAreDelivered() throws IOException {
        PipedInputStream snk = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(snk);
        pos.write(0x141);
        int value = snk.read();
        if (value != 0x41) {
            throw new RuntimeException("Expected low 8 bits 0x41 but was " + value);
        }
        pos.close();
    }
}
