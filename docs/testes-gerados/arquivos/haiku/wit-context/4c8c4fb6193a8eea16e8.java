/*
 * @test
 * @summary Verify setDoOutput delegates to the public getDoOutput behavior of HttpsURLConnectionImpl
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost:1/");
        URLConnection uc = url.openConnection();
        if (!(uc instanceof HttpsURLConnection)) {
            throw new RuntimeException("Expected HttpsURLConnection but got " + uc.getClass().getName());
        }
        HttpsURLConnection c = (HttpsURLConnection) uc;

        c.setDoOutput(true);
        if (!c.getDoOutput()) {
            throw new RuntimeException("getDoOutput() should be true after setDoOutput(true)");
        }

        c.setDoOutput(false);
        if (c.getDoOutput()) {
            throw new RuntimeException("getDoOutput() should be false after setDoOutput(false)");
        }

        c.setDoOutput(true);
        if (!c.getDoOutput()) {
            throw new RuntimeException("getDoOutput() should be true after re-enabling output");
        }
    }
}
