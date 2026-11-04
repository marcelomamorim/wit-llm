/* @test    @summary Test for getHeaderValue method in NegotiateAuthentication
 * @run main NegotiateAuthenticationWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.NegotiateAuthentication;

public class NegotiateAuthenticationWitupTest {

    public static void main(String[] args) {
        testGetHeaderValueThrowsRuntimeException();
    }

    public static void testGetHeaderValueThrowsRuntimeException() {
        try {
            URL url = new URL("http://example.com");
            String method = "GET";
            String result = NegotiateAuthentication.getHeaderValue(url, method);
            System.out.println("Unexpected success: " + result);
            System.exit(1);
        } catch (RuntimeException e) {
            System.out.println("Caught expected RuntimeException: " + e.getMessage());
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
            System.exit(1);
        }
    }
}
