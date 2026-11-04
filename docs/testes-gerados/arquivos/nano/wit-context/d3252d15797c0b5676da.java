/*
 * @test
 * @summary Test OutputStreamWriter constructor with Charset, focusing on null argument handling.
 * @run main OutputStreamWriterWitupTest
 */

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithNullCharset();
        testConstructorWithValidCharset();
    }

    private static void testConstructorWithNullCharset() {
        OutputStream dummyStream = new OutputStream() {
            @Override
            public void write(int b) {}
        };
        try {
            new OutputStreamWriter(dummyStream, (Charset) null);
            System.err.println("Failed: Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected for null Charset");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithValidCharset() throws Exception {
        OutputStream dummyStream = new OutputStream() {
            @Override
            public void write(int b) {}
        };
        Charset utf8 = Charset.forName("UTF-8");
        OutputStreamWriter writer = new OutputStreamWriter(dummyStream, utf8);
        if (writer != null) {
            System.out.println("Passed: OutputStreamWriter instantiated with valid Charset");
        } else {
            System.err.println("Failed: OutputStreamWriter is null with valid Charset");
        }
    }
}
