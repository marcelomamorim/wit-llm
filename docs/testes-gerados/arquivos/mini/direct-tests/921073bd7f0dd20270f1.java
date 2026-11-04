/*
 * @test
 * @summary Test java.lang.System.getProperty(String) basic behavior and security checks
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        testGetPropertyWithNullKey();
        testGetPropertyWithValidKey();
        testGetPropertyWithNonexistentKey();
    }

    private static void testGetPropertyWithNullKey() {
        try {
            System.getProperty(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetPropertyWithValidKey() {
        String key = "java.version";
        String value = System.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Expected non-null, non-empty value for key: " + key);
        }
    }

    private static void testGetPropertyWithNonexistentKey() {
        String key = "this.property.should.not.exist.1234567890";
        String value = System.getProperty(key);
        if (value != null) {
            throw new RuntimeException("Expected null for nonexistent property key: " + key);
        }
    }
}
