/*
 * @test
 * @summary Verifies Writer.write(String) behavior for the stream returned by Writer.nullWriter()
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteNullStringThrowsNPE();
        testWriteStringOnOpenNullWriterDoesNotThrow();
        testWriteStringAfterCloseThrowsIOException();
        testWriteStringReturnsNormallyForEmptyString();
    }

    static void testWriteNullStringThrowsNPE() {
        Writer w = Writer.nullWriter();
        try {
            w.write((String) null);
        } catch (NullPointerException expected) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null string");
    }

    static void testWriteStringOnOpenNullWriterDoesNotThrow() throws IOException {
        Writer w = Writer.nullWriter();
        w.write("hello");
        w.write("");
        w.close();
    }

    static void testWriteStringAfterCloseThrowsIOException() throws IOException {
        Writer w = Writer.nullWriter();
        w.close();
        try {
            w.write("abc");
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException when writing to closed nullWriter");
    }

    static void testWriteStringReturnsNormallyForEmptyString() throws IOException {
        Writer w = Writer.nullWriter();
        w.write("");
        w.close();
    }
}
