/*
 * @test
 * @summary Test the append(CharSequence) method of java.io.Writer.
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) throws IOException {
        testAppendWithNull();
        testAppendWithEmptyString();
        testAppendWithNonEmptyString();
    }

    static void testAppendWithNull() throws IOException {
        Writer writer = Writer.nullWriter();
        Writer result = writer.append((CharSequence) null);
        assert result == writer : "append(null) should return the same writer";
    }

    static void testAppendWithEmptyString() throws IOException {
        Writer writer = Writer.nullWriter();
        CharSequence empty = "";
        Writer result = writer.append(empty);
        assert result == writer : "append("") should return the same writer";
    }

    static void testAppendWithNonEmptyString() throws IOException {
        Writer writer = Writer.nullWriter();
        CharSequence seq = "test";
        Writer result = writer.append(seq);
        assert result == writer : "append("test") should return the same writer";
    }
}
