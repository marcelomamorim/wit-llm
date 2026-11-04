/*
 * @test
 * @summary Test for java.io.Writer.write(char[], int, int) method, covering exception paths.
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    public static void main(String[] args) {
        testWriteWithInvalidIndices();
        testWriteWhenClosed();
    }

    private static void testWriteWithInvalidIndices() {
        char[] buffer = {'a', 'b', 'c'};
        Writer writer = Writer.nullWriter();
        try {
            writer.write(buffer, -1, 2);
            System.err.println("Failed: Expected IndexOutOfBoundsException for negative offset.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException.");
        }
        try {
            writer.write(buffer, 1, 5);
            System.err.println("Failed: Expected IndexOutOfBoundsException for length exceeding array bounds.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException.");
        }
    }

    private static void testWriteWhenClosed() {
        class TestWriter extends Writer {
            private boolean closed = false;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public void flush() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        }
        TestWriter tw = new TestWriter();
        try {
            tw.close();
            tw.write(new char[]{'a'}, 0, 1);
            System.err.println("Failed: Expected IOException after close.");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
