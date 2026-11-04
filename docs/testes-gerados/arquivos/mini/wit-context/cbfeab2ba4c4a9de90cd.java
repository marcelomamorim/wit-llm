/*
 * @test
 * @summary Test java.io.Reader.transferTo(Writer) for null and closed stream exceptions
 * @run main ReaderWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.StringWriter;
import java.util.Objects;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testTransferToNullWriter();
        testTransferToClosedReader();
        testTransferToOpenReaderReturnsZero();
    }

    static void testTransferToNullWriter() {
        Reader r = Reader.nullReader();
        try {
            r.transferTo(null);
            throw new AssertionError("Expected NullPointerException for null Writer");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException instead of NullPointerException", ioe);
        }
    }

    static void testTransferToClosedReader() throws IOException {
        Reader r = Reader.nullReader();
        r.close();
        Writer w = new StringWriter();
        try {
            r.transferTo(w);
            throw new AssertionError("Expected IOException for closed Reader");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testTransferToOpenReaderReturnsZero() throws IOException {
        Reader r = Reader.nullReader();
        Writer w = new StringWriter();
        long transferred = r.transferTo(w);
        if (transferred != 0L) {
            throw new AssertionError("Expected transferTo to return 0 for open nullReader, got " + transferred);
        }
    }
}
