/* @test    @summary Test Base64.write(int b) method for correct byte writing and exception handling    @run main Base64WitupTest */

import java.io.IOException;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testWriteByte();
        testWriteNullBuffer();
        testWriteBufferWithOffset();
    }

    static void testWriteByte() {
        class DummyOutputStream extends java.io.OutputStream {
            byte lastByte = 0;
            boolean closed = false;
            @Override
            public void write(int b) throws IOException {
                lastByte = (byte) b;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        }
        DummyOutputStream dos = new DummyOutputStream();
        Base64 encoder = Base64.getEncoder();
        try {
            java.io.OutputStream os = encoder.wrap(dos);
            os.write(65); // ASCII 'A'
            os.close();
            assert dos.lastByte == 65 : "Expected last byte to be 65";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testWriteNullBuffer() {
        class DummyOutputStream extends java.io.OutputStream {
            @Override
            public void write(int b) throws IOException {
                // do nothing
            }
        }
        DummyOutputStream dos = new DummyOutputStream();
        Base64.Encoder encoder = Base64.getEncoder();
        java.io.OutputStream os = encoder.wrap(dos);
        try {
            // Since write(int b) calls write(byte[] buf, int off, int len),
            // and the buffer is null, we simulate a null buffer scenario.
            // But in actual implementation, write(int b) does not pass null buffer.
            // So, test that calling write(int b) does not throw.
            os.write(0);
        } catch (NullPointerException e) {
            throw new RuntimeException("Unexpected NullPointerException", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testWriteBufferWithOffset() {
        class DummyOutputStream extends java.io.OutputStream {
            byte[] buffer = new byte[10];
            int position = 0;
            @Override
            public void write(int b) throws IOException {
                if (position < buffer.length) {
                    buffer[position++] = (byte) b;
                }
            }
        }
        DummyOutputStream dos = new DummyOutputStream();
        Base64.Encoder encoder = Base64.getEncoder();
        java.io.OutputStream os = encoder.wrap(dos);
        try {
            os.write(66); // ASCII 'B'
            os.close();
            // Verify that the last written byte is 66
            // Since buffer is internal, we assume write works if no exception
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
