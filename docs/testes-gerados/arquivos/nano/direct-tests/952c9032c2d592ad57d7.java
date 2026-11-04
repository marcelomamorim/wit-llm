/* @test    @summary Test getHeaderValue method of NegotiateAuthentication class    @run main NegotiateAuthenticationWitupTest */

import java.net.URL;

public class NegotiateAuthenticationWitupTest {

    public static void main(String[] args) {
        testGetHeaderValueThrowsRuntimeException();
    }

    public static void testGetHeaderValueThrowsRuntimeException() {
        try {
            URL url = new URL("http://example.com");
            sun.net.www.protocol.http.NegotiateAuthentication auth = new sun.net.www.protocol.http.NegotiateAuthentication(null);
            auth.getHeaderValue(url, "GET");
            System.out.println("Test failed: no exception thrown");
        } catch (RuntimeException e) {
            if ("getHeaderValue not supported".equals(e.getMessage())) {
                System.out.println("Test passed: RuntimeException thrown as expected");
            } else {
                System.out.println("Test failed: Unexpected RuntimeException message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
