/*
 * @test
 * @summary Test the write(String str) method of java.io.Writer
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) {
        testWriteStringWithNull();
        testWriteStringWithEmpty();
        testWriteStringWithContent();
    }

    static void testWriteStringWithNull() {
        Writer writer = new Writer() {
            private boolean open = true;
            private void ensureOpen() throws IOException {
                if (!open) throw new IOException("Stream closed");
            }
            @Override
            public void write(String str) throws IOException {
                Objects.requireNonNull(str);
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
            writer.write(null);
            throw new AssertionError("Expected NullPointerException for null string");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testWriteStringWithEmpty() {
        Writer writer = new Writer() {
            private boolean open = true;
            private void ensureOpen() throws IOException {
                if (!open) throw new IOException("Stream closed");
            }
            @Override
            public void write(String str) throws IOException {
                Objects.requireNonNull(str);
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
            writer.write("");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testWriteStringWithContent() {
        Writer writer = new Writer() {
            private boolean open = true;
            private void ensureOpen() throws IOException {
                if (!open) throw new IOException("Stream closed");
            }
            @Override
            public void write(String str) throws IOException {
                Objects.requireNonNull(str);
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
            writer.write("Hello, World!");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
