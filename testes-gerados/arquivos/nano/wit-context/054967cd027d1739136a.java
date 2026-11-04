/* @test    @summary Test for sun.net.www.http.KeepAliveStream.reset() method
 * @run main KeepAliveStreamWitupTest
 */

import java.io.*;

public class KeepAliveStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testResetThrowsIOException();
    }

    private static void testResetThrowsIOException() {
        InputStream dummyInputStream = new ByteArrayInputStream(new byte[0]);
        ProgressSource ps = null; // Not used in this test, can be null
        long expected = 0L;
        HttpClient hc = null; // Not used in this test, can be null
        KeepAliveStream kas = new KeepAliveStream(dummyInputStream, ps, expected, hc);
        try {
            kas.reset();
            // If no exception is thrown, the test fails
            System.err.println("Expected IOException was not thrown.");
            System.exit(1);
        } catch (IOException e) {
            // Verify that the exception message matches the expected
            if ("mark/reset not supported".equals(e.getMessage())) {
                System.out.println("Test passed: IOException thrown as expected.");
            } else {
                System.err.println("Test failed: Unexpected IOException message: " + e.getMessage());
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Test failed: Unexpected exception type: " + e);
            System.exit(1);
        }
    }
}
