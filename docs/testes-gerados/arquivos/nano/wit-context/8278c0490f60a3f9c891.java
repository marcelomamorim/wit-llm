/*
 * @test
 * @summary Test for java.net.URI.toURL() method, focusing on null scheme case.
 * @run main URIWitupTest
 */

import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;

public class URIWitupTest {

    public static void main(String[] args) {
        testToURLWithNullScheme();
    }

    public static void testToURLWithNullScheme() {
        URI uri = null;
        try {
            // Creating a URI with null scheme to simulate the exception path
            uri = new URI(null, "authority", "/path", "query", "fragment");
            URL url = uri.toURL();
            // If no exception, the test should fail because exception is expected
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected exception, test passes
            System.out.println("Caught expected exception: " + e.getClass().getSimpleName());
        } catch (MalformedURLException e) {
            // MalformedURLException should not occur in this context
            System.err.println("Unexpected MalformedURLException: " + e.getMessage());
            System.exit(1);
        }
    }
}
