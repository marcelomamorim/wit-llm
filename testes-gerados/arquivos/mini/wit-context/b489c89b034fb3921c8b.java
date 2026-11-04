/*
 * @test
 * @summary Test Writer.append(char) method including exception on closed stream
 * @run main WriterWitupTest
 */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) throws Exception {
        testAppendOnOpenStream();
        testAppendOnClosedNullWriter();
    }

    static void testAppendOnOpenStream() throws IOException {
        Writer w = Writer.nullWriter();
        Writer result = w.append('a');
        if (result != w) {
            throw new AssertionError("append(char) should return this Writer instance");
        }
    }

    static void testAppendOnClosedNullWriter() {
        Writer w = Writer.nullWriter();
        try {
            w.close();
            w.append('a');
            throw new AssertionError("Expected IOException when appending to closed Writer");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("IOException message mismatch: " + msg);
            }
        }
    }

}
