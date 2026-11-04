/* @test    @summary Test setAuthenticator method of HttpURLConnection for UnsupportedOperationException    @run main HttpURLConnectionWitupTest */

import java.net.HttpURLConnection;
import java.net.Authenticator;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetAuthenticatorThrows();
    }

    public static void testSetAuthenticatorThrows() {
        HttpURLConnection connection = new HttpURLConnection() {
            @Override
            public void connect() throws IOException {
                // no-op
            }
        };
        try {
            connection.setAuthenticator(null);
            // If no exception, test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            assert e.getMessage() != null : "Exception message should not be null";
        }
    }
}
