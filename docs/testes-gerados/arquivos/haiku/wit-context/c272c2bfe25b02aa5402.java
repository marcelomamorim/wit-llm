/*
 * @test
 * @summary Verifies that HttpsURLConnection.setDefaultHostnameVerifier rejects null and accepts a non-null verifier
 * @modules java.base/com.sun.net.ssl
 * @run main HttpsURLConnectionWitupTest
 */

import com.sun.net.ssl.HostnameVerifier;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
            throw new AssertionError("expected IllegalArgumentException for null verifier");
        } catch (IllegalArgumentException expected) {
        }

        HostnameVerifier denyAll = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return false;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(denyAll);

        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
            throw new AssertionError("expected IllegalArgumentException for null verifier after non-null set");
        } catch (IllegalArgumentException expected) {
        }
    }
}
