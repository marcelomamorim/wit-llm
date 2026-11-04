/*
 * @test
 * @summary Test InputStreamReader constructor with Charset argument
 * @run main InputStreamReaderWitupTest
 */

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

public class InputStreamReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrows();
        testValidCharsetCreatesReader();
    }

    private static void testNullCharsetThrows() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new InputStreamReader(in, (Charset) null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            if (!"charset".equals(e.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testValidCharsetCreatesReader() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        Charset cs = Charset.forName("UTF-8");
        InputStreamReader reader = new InputStreamReader(in, cs);
        if (reader == null) {
            throw new RuntimeException("InputStreamReader constructor returned null");
        }
        // Test that read() returns -1 on empty stream
        int result = reader.read();
        if (result != -1) {
            throw new RuntimeException("Expected -1 from read() on empty stream, got " + result);
        }
        reader.close();
    }
}
