/*
 * @test
 * @summary Test for InputStreamReader constructor with Charset parameter
 * @run main InputStreamReaderWitupTest
 */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithNullCharset();
        testConstructorWithValidCharset();
    }

    private static void testConstructorWithNullCharset() {
        InputStream in = System.in;
        try {
            new InputStreamReader(in, (Charset) null);
            System.err.println("Failed: Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected for null Charset");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithValidCharset() throws Exception {
        InputStream in = System.in;
        Charset charset = Charset.forName("UTF-8");
        InputStreamReader reader = new InputStreamReader(in, charset);
        if (reader != null) {
            System.out.println("Passed: InputStreamReader created with valid Charset");
        } else {
            System.err.println("Failed: InputStreamReader is null");
        }
    }
}
