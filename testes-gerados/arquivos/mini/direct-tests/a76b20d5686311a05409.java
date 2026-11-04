/*
 * @test
 * @summary Test DefaultSSLContext constructor behavior
 * @run main SSLContextImplWitupTest
 */

import sun.security.ssl.SSLContextImpl;

public class SSLContextImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that DefaultSSLContext constructor throws reservedException if set
        Exception reserved = new Exception("reserved");
        setReservedException(reserved);
        try {
            new DefaultSSLContext();
            throw new RuntimeException("Expected exception not thrown");
        } catch (Exception e) {
            if (e != reserved) {
                throw new RuntimeException("Unexpected exception thrown", e);
            }
        } finally {
            clearReservedException();
        }

        // Test normal construction does not throw
        DefaultSSLContext ctx = new DefaultSSLContext();
        if (ctx == null) {
            throw new RuntimeException("DefaultSSLContext instance is null");
        }
    }

    // Reflection helpers to set and clear DefaultManagersHolder.reservedException
    private static void setReservedException(Exception e) throws Exception {
        Class<?> holderClass = Class.forName("sun.security.ssl.SSLContextImpl$DefaultManagersHolder");
        java.lang.reflect.Field f = holderClass.getDeclaredField("reservedException");
        f.setAccessible(true);
        f.set(null, e);
    }

    private static void clearReservedException() throws Exception {
        Class<?> holderClass = Class.forName("sun.security.ssl.SSLContextImpl$DefaultManagersHolder");
        java.lang.reflect.Field f = holderClass.getDeclaredField("reservedException");
        f.setAccessible(true);
        f.set(null, null);
    }

    // Subclass to expose DefaultSSLContext constructor
    public static class DefaultSSLContext extends SSLContextImpl {
        public DefaultSSLContext() throws Exception {
            super.DefaultSSLContext();
        }
    }
}
