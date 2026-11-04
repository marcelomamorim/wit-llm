/*
 * @test
 * @summary Test the transferTo method of java.io.Reader for correct behavior.
 * @run main ReaderWitupTest
 */

import java.io.*;

public class ReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testTransferToWithNullWriter();
        testTransferToWithOpenReader();
        testTransferToAfterClose();
    }

    static void testTransferToWithNullWriter() {
        Reader reader = new StringReader("test");
        try {
            reader.transferTo(null);
            throw new AssertionError("Expected NullPointerException for null Writer");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    static void testTransferToWithOpenReader() throws Exception {
        String data = "hello world";
        StringReader reader = new StringReader(data);
        StringWriter writer = new StringWriter();
        long transferred = reader.transferTo(writer);
        if (transferred != 0L) {
            throw new AssertionError("Expected transferTo to return 0L, got " + transferred);
        }
        // Since transferTo implementation returns 0, no data is transferred.
        // Verify that writer is still empty.
        if (!writer.toString().isEmpty()) {
            throw new AssertionError("Writer should be empty, but has data: " + writer.toString());
        }
        reader.close();
    }

    static void testTransferToAfterClose() throws Exception {
        StringReader reader = new StringReader("data");
        reader.close();
        StringWriter writer = new StringWriter();
        try {
            reader.transferTo(writer);
            throw new AssertionError("Expected IOException after close");
        } catch (IOException expected) {
            // expected
        }
    }
}
