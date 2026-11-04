/*
 * @test
 * @summary Test for setSSLSocketFactory method in HttpsURLConnection
 * @run main HttpsURLConnectionWitupTest
 */

import java.net.URL;
import javax.net.ssl.SSLSocketFactory;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetSSLSocketFactoryWithNull();
        testSetSSLSocketFactoryWithValid();
    }

    private static void testSetSSLSocketFactoryWithNull() {
        HttpsURLConnection conn = createConnection();
        try {
            conn.setSSLSocketFactory(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null SSLSocketFactory");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null SSLSocketFactory");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testSetSSLSocketFactoryWithValid() throws Exception {
        HttpsURLConnection conn = createConnection();
        SSLSocketFactory factory = javax.net.ssl.SSLSocketFactory.getDefault();
        try {
            conn.setSSLSocketFactory(factory);
            System.out.println("PASS: setSSLSocketFactory with valid factory succeeded");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static HttpsURLConnection createConnection() {
        try {
            URL url = new URL("https://example.com");
            return (HttpsURLConnection) url.openConnection();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create connection", e);
        }
    }
}
