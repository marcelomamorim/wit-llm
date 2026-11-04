/*
 * @test
 * @summary Test OutputStreamWriter(OutputStream out) constructor basic functionality
 * @run main OutputStreamWriterWitupTest
 */

import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OutputStreamWriterWitupTest {
    public static void main(String[] args) throws IOException {
        // Create a ByteArrayOutputStream to capture output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(baos);

        // Write a simple string
        String testString = "Hello, world!";
        osw.write(testString);
        osw.flush();

        // Verify the bytes written correspond to the string in the platform default charset
        byte[] bytes = baos.toByteArray();
        String result = new String(bytes, java.nio.charset.Charset.defaultCharset());

        if (!testString.equals(result)) {
            throw new AssertionError("OutputStreamWriter did not encode string correctly. Expected: '" + testString + "' but got: '" + result + "'");
        }

        // Test writing empty string
        baos.reset();
        osw.write("");
        osw.flush();
        if (baos.size() != 0) {
            throw new AssertionError("Writing empty string should produce no output bytes");
        }

        // Test writing single character
        baos.reset();
        osw.write('A');
        osw.flush();
        bytes = baos.toByteArray();
        result = new String(bytes, java.nio.charset.Charset.defaultCharset());
        if (!"A".equals(result)) {
            throw new AssertionError("OutputStreamWriter did not encode single char correctly. Expected: 'A' but got: '" + result + "'");
        }

        osw.close();
    }
}
