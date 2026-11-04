/*
 * @test
 * @summary Verify that HttpsURLConnectionImpl.setDoInput delegates the input flag observable through getDoInput
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        if (!conn.getDoInput()) {
            throw new RuntimeException("doInput should default to true");
        }

        conn.setDoInput(false);
        if (conn.getDoInput()) {
            throw new RuntimeException("getDoInput should be false after setDoInput(false)");
        }

        conn.setDoInput(true);
        if (!conn.getDoInput()) {
            throw new RuntimeException("getDoInput should be true after setDoInput(true)");
        }

        conn.setDoInput(false);
        conn.setDoInput(false);
        if (conn.getDoInput()) {
            throw new RuntimeException("repeated setDoInput(false) should keep flag false");
        }
    }
}
