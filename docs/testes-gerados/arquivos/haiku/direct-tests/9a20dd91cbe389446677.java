/*
 * @test
 * @summary Verify that HttpsURLConnectionImpl.getContent() propagates IOException when the delegate cannot connect
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://127.0.0.1:1/");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        if (!url.equals(conn.getURL())) {
            throw new RuntimeException("Unexpected URL: " + conn.getURL());
        }

        boolean ioExceptionSeen = false;
        try {
            conn.getContent();
        } catch (IOException expected) {
            ioExceptionSeen = true;
        }

        if (!ioExceptionSeen) {
            throw new RuntimeException("Expected IOException from getContent()");
        }
    }
}
