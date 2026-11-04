/*
 * @test
 * @summary Tests the removeValue method of SSLSessionImpl for null key input.
 * @run main SSLSessionImplWitupTest
 */

import sun.security.ssl.SSLSessionImpl;

public class SSLSessionImplWitupTest {

    public static void main(String[] args) {
        testRemoveValueWithNullKey();
    }

    public static void testRemoveValueWithNullKey() {
        SSLSessionImpl session = new SSLSessionImpl();
        try {
            session.removeValue(null);
            System.out.println("Test failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed: Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }
}
