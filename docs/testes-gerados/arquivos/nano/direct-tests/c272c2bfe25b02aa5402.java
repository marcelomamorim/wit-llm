/* @test    @summary Test for setDefaultHostnameVerifier method in HttpsURLConnection    @run main HttpsURLConnectionWitupTest */

import java.net.URL;
import com.sun.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetDefaultHostnameVerifier();
    }

    public static void testSetDefaultHostnameVerifier() {
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String hostname, String sessionHostname) {
                return true;
            }
        };
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(verifier);
            System.out.println("setDefaultHostnameVerifier executed successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println("Failed: Unexpected IllegalArgumentException.");
        } catch (SecurityException e) {
            System.err.println("Failed: Unexpected SecurityException.");
        }
    }
}
