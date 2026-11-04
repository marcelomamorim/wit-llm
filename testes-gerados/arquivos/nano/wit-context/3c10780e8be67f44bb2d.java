/* @test    @summary Test for PushbackReader.reset() method to verify IOException is thrown as expected.    @run main PushbackReaderWitupTest */

import java.io.PushbackReader;
import java.io.StringReader;
import java.io.IOException;

public class PushbackReaderWitupTest {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        StringReader sr = new StringReader("");
        PushbackReader pbr = new PushbackReader(sr, 1);
        try {
            pbr.reset();
            System.err.println("FAIL: Expected IOException was not thrown.");
        } catch (IOException e) {
            if ("mark/reset not supported".equals(e.getMessage())) {
                System.out.println("PASS: IOException thrown with expected message.");
            } else {
                System.err.println("FAIL: IOException thrown, but message was unexpected: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
