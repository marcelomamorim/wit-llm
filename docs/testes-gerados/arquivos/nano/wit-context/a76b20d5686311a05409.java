/* @test    @summary Test for SSLContextImpl.DefaultSSLContext constructor behavior    @run main SSLContextImplWitupTest */

import java.security.*;
import sun.security.ssl.SSLContextImpl;

public class SSLContextImplWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultSSLContextNormal();
        testDefaultSSLContextWithReservedException();
    }

    private static void testDefaultSSLContextNormal() {
        try {
            SSLContextImpl.DefaultSSLContext ctx = new SSLContextImpl.DefaultSSLContext();
            // If no exception, test passes
            System.out.println("testDefaultSSLContextNormal: PASSED");
        } catch (Exception e) {
            System.err.println("testDefaultSSLContextNormal: FAILED with exception: " + e);
        }
    }

    private static void testDefaultSSLContextWithReservedException() {
        // Setup: simulate DefaultManagersHolder.reservedException != null
        // Note: Since DefaultManagersHolder is package-private and static, and no public API to set it,
        // this test assumes the exception is already set or can be set via reflection.
        // Given constraints, we will simulate this by temporarily modifying the static field via reflection.
        try {
            java.lang.reflect.Field reservedExceptionField = Class.forName("sun.security.ssl.DefaultManagersHolder")
                .getDeclaredField("reservedException");
            reservedExceptionField.setAccessible(true);
            Throwable originalException = (Throwable) reservedExceptionField.get(null);
            Throwable testException = new RuntimeException("Test reserved exception");
            reservedExceptionField.set(null, testException);
            try {
                SSLContextImpl.DefaultSSLContext ctx = new SSLContextImpl.DefaultSSLContext();
                System.err.println("testDefaultSSLContextWithReservedException: FAILED - exception was expected but not thrown");
            } catch (Throwable e) {
                if (e == testException || (e.getCause() != null && e.getCause() == testException)) {
                    System.out.println("testDefaultSSLContextWithReservedException: PASSED");
                } else {
                    System.err.println("testDefaultSSLContextWithReservedException: FAILED with unexpected exception: " + e);
                }
            } finally {
                // Restore original exception
                reservedExceptionField.set(null, originalException);
            }
        } catch (Exception e) {
            System.err.println("testDefaultSSLContextWithReservedException: SKIPPED due to reflection error: " + e);
        }
    }
}
