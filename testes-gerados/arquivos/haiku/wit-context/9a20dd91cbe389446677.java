/*
 * @test
 * @summary Verifies that HttpsURLConnection.getContent() reports an IOException through the public API when the connection cannot be established
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {
    public static void main(String[] args) throws Exception {
        URL u = new URL("https://localhost:1/");
        HttpsURLConnection c = (HttpsURLConnection) u.openConnection();
        try {
            c.getContent();
            throw new RuntimeException("expected IOException from getContent()");
        } catch (IOException expected) {
        }
    }
}
