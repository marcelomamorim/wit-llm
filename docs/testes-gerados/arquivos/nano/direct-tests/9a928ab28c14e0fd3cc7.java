/*
 * @test
 * @summary Test InputStreamReader constructor with Charset parameter
 * @run main InputStreamReaderWitupTest
 */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithCharset();
    }

    static void testConstructorWithCharset() throws Exception {
        byte[] data = "hello".getBytes();
        InputStream inputStream = new java.io.ByteArrayInputStream(data);
        Charset charset = Charset.forName("UTF-8");
        InputStreamReader reader = new InputStreamReader(inputStream, charset);
        int ch = reader.read();
        if (ch != 'h') {
            throw new AssertionError("Expected 'h', but got: " + (char) ch);
        }
        reader.close();
    }
}
