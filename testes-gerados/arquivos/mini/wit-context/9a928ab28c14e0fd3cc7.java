/*
 * @test
 * @summary Test InputStreamReader constructor with Charset parameter for null and valid Charset
 * @run main InputStreamReaderWitupTest
 */

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class InputStreamReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidCharsetSucceeds();
    }

    private static void testNullCharsetThrowsNPE() {
        InputStream dummyStream = new ByteArrayInputStream(new byte[0]);
        try {
            new InputStreamReader(dummyStream, (Charset) null);
            throw new RuntimeException("Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            if (!"charset".equals(e.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testValidCharsetSucceeds() throws Exception {
        byte[] bytes = "abc".getBytes(StandardCharsets.UTF_8);
        InputStream in = new ByteArrayInputStream(bytes);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        int firstChar = reader.read();
        if (firstChar != 'a') {
            throw new RuntimeException("Expected first character 'a', got: " + (char)firstChar);
        }
        reader.close();
    }
}
