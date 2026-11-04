/*
 * @test
 * @summary Verify that HttpsURLConnectionImpl.setDoOutput delegates correctly and is observable through getDoOutput
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/witup");
        URLConnection uc = url.openConnection();
        if (!(uc instanceof HttpsURLConnection)) {
            throw new RuntimeException("Expected HttpsURLConnection but got " + uc.getClass().getName());
        }
        HttpsURLConnection c = (HttpsURLConnection) uc;

        if (c.getDoOutput()) {
            throw new RuntimeException("doOutput should default to false");
        }

        c.setDoOutput(true);
        if (!c.getDoOutput()) {
            throw new RuntimeException("setDoOutput(true) not reflected by getDoOutput()");
        }

        c.setDoOutput(false);
        if (c.getDoOutput()) {
            throw new RuntimeException("setDoOutput(false) not reflected by getDoOutput()");
        }

        c.setDoOutput(true);
        c.setDoOutput(true);
        if (!c.getDoOutput()) {
            throw new RuntimeException("Repeated setDoOutput(true) changed state");
        }
    }
}
