/*
 * @test    @summary Test getOrDefault with null key to verify NullPointerException
 * @run main ConcurrentSkipListMapGetOrDefaultNullKeyTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapGetOrDefaultNullKeyTest {

    public static void main(String[] args) {
        testGetOrDefaultWithNullKey();
    }

    public static void testGetOrDefaultWithNullKey() {
        ConcurrentSkipListMap<Object, String> map = new ConcurrentSkipListMap<>();
        try {
            map.getOrDefault(null, "default");
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("NullPointerException caught as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
