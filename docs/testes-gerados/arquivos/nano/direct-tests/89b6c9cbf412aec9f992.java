/* @test    @summary Test setSSLSocketFactory method of HttpsURLConnection    @run main HttpsURLConnectionWitupTest */

import java.net.URL;
import javax.net.ssl.SSLSocketFactory;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetSSLSocketFactoryWithValidFactory();
        testSetSSLSocketFactoryWithNull();
    }

    private static void testSetSSLSocketFactoryWithValidFactory() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        conn.setSSLSocketFactory(factory);
        System.out.println("setSSLSocketFactory with valid factory passed.");
    }

    private static void testSetSSLSocketFactoryWithNull() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        try {
            conn.setSSLSocketFactory(null);
            System.out.println("ERROR: Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("setSSLSocketFactory with null factory correctly threw IllegalArgumentException.");
        }
    }
}
