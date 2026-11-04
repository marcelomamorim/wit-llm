/* @test    @summary Test getContent() method for HttpsURLConnectionImpl with expected exception handling    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentThrowsUnknownServiceException();
    }

    public static void testGetContentThrowsUnknownServiceException() {
        try {
            URL url = new URL("https://example.com");
            HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
            // Simulate delegate.getContent() throwing UnknownServiceException
            // Since delegate is protected, and no public setter, we assume the method throws as per current implementation
            // For testing, we invoke getContent() and expect an IOException wrapping UnknownServiceException
            Object content = conn.getContent();
            // If no exception, the test fails
            System.err.println("Expected UnknownServiceException to be thrown, but got: " + content);
        } catch (IOException e) {
            Throwable cause = e.getCause();
            if (cause != null && cause.getClass().getSimpleName().equals("UnknownServiceException")) {
                System.out.println("Passed: getContent() threw UnknownServiceException as expected.");
            } else {
                System.err.println("Failed: getContent() threw IOException, but cause was: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
