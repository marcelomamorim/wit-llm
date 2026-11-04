/*
 * @test
 * @summary Test BufferedReader(Reader) constructor with default buffer size
 * @run main BufferedReaderWitupTest
 */

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

public class BufferedReaderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a BufferedReader with a simple StringReader
        StringReader sr = new StringReader("test");
        BufferedReader br = new BufferedReader(sr);

        // Read characters and verify they match the input
        int ch = br.read();
        if (ch != 't') {
            throw new RuntimeException("Expected 't' but got " + (char) ch);
        }

        ch = br.read();
        if (ch != 'e') {
            throw new RuntimeException("Expected 'e' but got " + (char) ch);
        }

        ch = br.read();
        if (ch != 's') {
            throw new RuntimeException("Expected 's' but got " + (char) ch);
        }

        ch = br.read();
        if (ch != 't') {
            throw new RuntimeException("Expected 't' but got " + (char) ch);
        }

        ch = br.read();
        if (ch != -1) {
            throw new RuntimeException("Expected end of stream (-1) but got " + ch);
        }

        br.close();
    }
}
