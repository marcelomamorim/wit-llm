/* @test    @summary Test PipedOutputStream.write(int b) behavior when connected and not connected    @run main PipedOutputStreamWitupTest */

import java.io.*;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteWithoutConnect();
        testWriteWithConnect();
    }

    private static void testWriteWithoutConnect() {
        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.write(65);
            fail("Expected IOException not thrown");
        } catch (IOException e) {
            assert e.getMessage().equals("Pipe not connected") : "Unexpected message: " + e.getMessage();
        }
    }

    private static void testWriteWithConnect() throws Exception {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thread readerThread = new Thread(() -> {
            try {
                int data = pis.read();
                baos.write(data);
            } catch (IOException e) {
                // ignore
            }
        });
        readerThread.start();
        pos.write(66); // ASCII 'B'
        pos.flush();
        readerThread.join(1000);
        assert baos.size() == 1 : "No data read from PipedInputStream";
        assert baos.toByteArray()[0] == 66 : "Data mismatch: expected 66, got " + baos.toByteArray()[0];
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
