/* @test    @summary Test for SSLContext.setDefault(SSLContext) handling null argument and normal behavior    @run main SSLContextWitupTest */

import javax.net.ssl.SSLContext;
import java.security.Security;

public class SSLContextWitupTest {

    public static void main(String[] args) throws Exception {
        testSetDefaultWithNull();
        testSetDefaultWithValidContext();
    }

    private static void testSetDefaultWithNull() {
        boolean exceptionThrown = false;
        try {
            SSLContext.setDefault(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when setting default SSLContext with null");
        }
    }

    private static void testSetDefaultWithValidContext() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        SSLContext.setDefault(context);
        SSLContext defaultContext = SSLContext.getDefault();
        if (defaultContext != context) {
            throw new AssertionError("Default SSLContext was not set correctly");
        }
    }
}
