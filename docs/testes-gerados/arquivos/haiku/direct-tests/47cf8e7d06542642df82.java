/*
 * @test
 * @summary Verifies observable behavior of System.setProperty: previous value return, read-back via getProperty, and key validation.
 * @run main SystemWitupTest
 */

public class SystemWitupTest {

    public static void main(String[] args) {
        String key = "witup.test.setProperty.key";
        System.clearProperty(key);

        String prev = System.setProperty(key, "v1");
        if (prev != null) {
            throw new RuntimeException("Expected null previous value, got: " + prev);
        }
        if (!"v1".equals(System.getProperty(key))) {
            throw new RuntimeException("Expected v1 after setProperty, got: " + System.getProperty(key));
        }

        prev = System.setProperty(key, "v2");
        if (!"v1".equals(prev)) {
            throw new RuntimeException("Expected previous value v1, got: " + prev);
        }
        if (!"v2".equals(System.getProperty(key))) {
            throw new RuntimeException("Expected v2 after second setProperty, got: " + System.getProperty(key));
        }

        System.clearProperty(key);

        boolean iae = false;
        try {
            System.setProperty("", "x");
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        if (!iae) {
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        }

        boolean npe = false;
        try {
            System.setProperty(null, "x");
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null key");
        }
    }
}
