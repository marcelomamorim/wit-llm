/*
 * @test
 * @summary Verify setDefaultSSLSocketFactory rejects null and enforces the SecurityManager setFactory check
 * @modules java.base/com.sun.net.ssl
 * @run main HttpsURLConnectionWitupTest
 */

import java.security.Permission;
import javax.net.ssl.SSLSocketFactory;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFactoryThrowsIllegalArgumentException();
        testValidFactoryAccepted();
        testSecurityManagerDenialThrowsSecurityException();
    }

    static void testNullFactoryThrowsIllegalArgumentException() {
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for null factory");
    }

    static void testValidFactoryAccepted() {
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        HttpsURLConnection.setDefaultSSLSocketFactory(sf);
    }

    static void testSecurityManagerDenialThrowsSecurityException() {
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if ("setFactory".equals(perm.getName())) {
                    throw new SecurityException("denied");
                }
            }
        });
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(sf);
        } catch (SecurityException expected) {
            return;
        } finally {
            System.setSecurityManager(original);
        }
        throw new AssertionError("expected SecurityException from SecurityManager");
    }
}
