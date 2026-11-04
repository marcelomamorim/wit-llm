/*
 * @test
 * @summary Verify that setFixedLengthStreamingMode delegates without error on an unconnected HTTPS connection
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetFixedLengthStreamingModeZero();
        testSetFixedLengthStreamingModePositive();
        testSetFixedLengthStreamingModeLargeValue();
        testSetFixedLengthStreamingModeCalledTwice();
        System.out.println("PASSED");
    }

    private static HttpsURLConnection newConnection() throws Exception {
        URL url = new URL("https://localhost/");
        Object o = url.openConnection();
        if (!(o instanceof HttpsURLConnection)) {
            throw new RuntimeException("Unexpected connection type: " + o.getClass());
        }
        return (HttpsURLConnection) o;
    }

    private static void testSetFixedLengthStreamingModeZero() throws Exception {
        HttpsURLConnection conn = newConnection();
        conn.setFixedLengthStreamingMode(0L);
    }

    private static void testSetFixedLengthStreamingModePositive() throws Exception {
        HttpsURLConnection conn = newConnection();
        conn.setFixedLengthStreamingMode(1024L);
    }

    private static void testSetFixedLengthStreamingModeLargeValue() throws Exception {
        HttpsURLConnection conn = newConnection();
        conn.setFixedLengthStreamingMode((long) Integer.MAX_VALUE);
    }

    private static void testSetFixedLengthStreamingModeCalledTwice() throws Exception {
        HttpsURLConnection conn = newConnection();
        conn.setFixedLengthStreamingMode(10L);
        conn.setFixedLengthStreamingMode(20L);
    }
}
