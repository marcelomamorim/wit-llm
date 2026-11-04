/* @test    @summary Test BufferedReader constructor with default buffer size    @run main BufferedReaderWitupTest */

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testBufferedReaderConstructorWithStringReader();
    }

    static void testBufferedReaderConstructorWithStringReader() throws IOException {
        StringReader sr = new StringReader("Test string");
        BufferedReader br = new BufferedReader(sr);
        String line = br.readLine();
        if (!"Test string".equals(line)) {
            throw new AssertionError("Expected 'Test string', but got: " + line);
        }
        br.close();
    }
}
