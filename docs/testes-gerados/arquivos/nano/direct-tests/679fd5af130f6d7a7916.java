/*
 * @test
 * @summary Test getRequestProperties() method of URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testGetRequestPropertiesNullRequests();
        testGetRequestPropertiesWithHeaders();
    }

    private static void testGetRequestPropertiesNullRequests() {
        URLConnection connection = new DummyURLConnection();
        Map<String, List<String>> result = connection.getRequestProperties();
        if (result != null && result.isEmpty()) {
            System.out.println("PASS: getRequestProperties() returns empty map when requests is null");
        } else {
            System.out.println("FAIL: getRequestProperties() did not return empty map when requests is null");
        }
    }

    private static void testGetRequestPropertiesWithHeaders() {
        DummyURLConnection connection = new DummyURLConnection();
        connection.setHeaders(Collections.singletonMap("Header1", List.of("Value1")));
        Map<String, List<String>> result = connection.getRequestProperties();
        if (result != null && result.containsKey("Header1") && result.get("Header1").contains("Value1")) {
            System.out.println("PASS: getRequestProperties() returns correct headers");
        } else {
            System.out.println("FAIL: getRequestProperties() did not return correct headers");
        }
    }

    private static class DummyURLConnection extends URLConnection {
        private Map<String, List<String>> headers;

        protected DummyURLConnection() {
            super(null);
        }

        public void setHeaders(Map<String, List<String>> headers) {
            this.headers = headers;
        }

        @Override
        public void connect() {
            // no-op
        }

        @Override
        public Map<String, List<String>> getRequestProperties() {
            if (headers == null) {
                return null;
            }
            return headers;
        }
    }
}
