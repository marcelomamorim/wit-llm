/*
 * @test
 * @summary Verify URLConnection.getRequestProperties() returns request headers before connection and rejects the call once connected
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class URLConnectionWitupTest {

    static class StubConnection extends URLConnection {
        StubConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
            connected = true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<String> findValues(Map<String, List<String>> map, String key) {
        for (Map.Entry<String, List<String>> e : map.entrySet()) {
            if (e.getKey() != null && e.getKey().equalsIgnoreCase(key)) {
                return e.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://example.com/resource");

        StubConnection fresh = new StubConnection(url);
        Map<String, List<String>> empty = fresh.getRequestProperties();
        check(empty != null, "getRequestProperties returned null on fresh connection");
        check(empty.isEmpty(), "fresh connection should have no request properties");

        fresh.setRequestProperty("X-Witup", "one");
        Map<String, List<String>> withHeader = fresh.getRequestProperties();
        List<String> values = findValues(withHeader, "X-Witup");
        check(values != null, "request property X-Witup missing after setRequestProperty");
        check(values.contains("one"), "request property value 'one' missing");

        StubConnection connected = new StubConnection(url);
        connected.connect();
        boolean thrown = false;
        try {
            connected.getRequestProperties();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "getRequestProperties should throw IllegalStateException when connected");
    }
}
