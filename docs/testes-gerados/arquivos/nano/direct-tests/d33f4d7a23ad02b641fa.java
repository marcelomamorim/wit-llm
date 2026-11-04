/* @test    @summary Test setDefaultSSLSocketFactory method of HttpsURLConnection    @run main HttpsURLConnectionWitupTest */

import java.net.URL;
import javax.net.ssl.SSLSocketFactory;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetDefaultSSLSocketFactory();
    }

    public static void testSetDefaultSSLSocketFactory() {
        SSLSocketFactory factory = SSLSocketFactory.getDefault();
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(factory);
            System.out.println("setDefaultSSLSocketFactory executed successfully.");
        } catch (Exception e) {
            throw new RuntimeException("setDefaultSSLSocketFactory threw an exception", e);
        }
    }
}
