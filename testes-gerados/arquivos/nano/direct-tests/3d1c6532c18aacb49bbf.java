/* @test    @summary Test Writer.write(String, int, int) method for correct behavior and exception handling.    @run main WriterWitupTest */

import java.io.Writer;
import java.io.IOException;
import java.util.Objects;

public class WriterWitupTest {

    public static void main(String[] args) {
        testWriteValidRange();
        testWriteInvalidRange();
        testWriteNullString();
    }

    static void testWriteValidRange() {
        String testStr = "Hello, World!";
        StringBuilder sb = new StringBuilder();
        Writer writer = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                for (int i = off; i < off + len; i++) {
                    sb.append(cbuf[i]);
                }
            }
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
            public void write(String str, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, str.length());
                for (int i = off; i < off + len; i++) {
                    sb.append(str.charAt(i));
                }
            }
        };
        try {
            writer.write(testStr, 0, testStr.length());
            assert sb.toString().equals(testStr) : "Valid range write failed";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid range test", e);
        }
    }

    static void testWriteInvalidRange() {
        String testStr = "Test";
        Writer writer = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                // no-op
            }
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
            public void write(String str, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, str.length());
            }
        };
        boolean exceptionThrown = false;
        try {
            writer.write(testStr, -1, 2);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in invalid range test", e);
        }
        assert exceptionThrown : "Expected exception not thrown for invalid range";
    }

    static void testWriteNullString() {
        String testStr = null;
        Writer writer = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                // no-op
            }
            @Override
            public void flush() throws IOException {}
            @Override
            public void close() throws IOException {}
            public void write(String str, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, str.length());
            }
        };
        boolean exceptionThrown = false;
        try {
            writer.write(testStr, 0, 1);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException when writing null string", e);
        }
        assert exceptionThrown : "Expected NullPointerException not thrown when writing null string";
    }
}
