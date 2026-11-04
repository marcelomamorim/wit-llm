/* @test    @summary Test the write(int c) method of java.io.Writer    @run main WriterWitupTest */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) {
        testWriteMethod()
        ;
    }

    public static void testWriteMethod() {
        Writer writer = new Writer() {
            private boolean open = true;

            private void ensureOpen() throws IOException {
                if (!open) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public void write(int c) throws IOException {
                ensureOpen();
            }

            @Override
            public void close() throws IOException {
                open = false;
            }

            @Override
            public void flush() throws IOException {
                ensureOpen();
            }
        };
        try {
            writer.write(65); // ASCII for 'A'
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        try {
            writer.close();
            writer.write(66); // Should throw IOException
            throw new AssertionError("Expected IOException after close() not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
        try {
            writer.flush(); // Should throw IOException after close
            throw new AssertionError("Expected IOException on flush() after close() not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message on flush: " + e.getMessage(), e);
            }
        }
    }
}
