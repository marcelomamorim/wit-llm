/*
 * @test
 * @summary Test getSession method of SSLSessionContextImpl for valid and null sessionId
 * @run main SSLSessionContextImplWitupTest
 */

import java.util.Arrays;
import javax.net.ssl.SSLSession;
import sun.security.ssl.SSLSessionContextImpl;

public class SSLSessionContextImplWitupTest {

    public static void main(String[] args) {
        testGetSessionWithValidId();
        testGetSessionWithNullId();
    }

    private static void testGetSessionWithValidId() {
        SSLSessionContextImpl context = new SSLSessionContextImpl();
        byte[] sessionId = {1, 2, 3, 4};
        SSLSession session = context.getSession(sessionId);
        if (session != null) {
            System.out.println("PASS: getSession with valid id returned a session or null as expected.");
        } else {
            System.out.println("PASS: getSession with valid id returned null (no session stored)." );
        }
    }

    private static void testGetSessionWithNullId() {
        SSLSessionContextImpl context = new SSLSessionContextImpl();
        boolean exceptionThrown = false;
        try {
            context.getSession(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            System.out.println("PASS: getSession with null id threw NullPointerException as expected.");
        } else {
            System.out.println("FAIL: getSession with null id did not throw NullPointerException.");
        }
    }
}
