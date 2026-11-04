/* @test    @summary Test for TreeMap.containsKey method to verify behavior with null keys.    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithNullKey();
        testContainsKeyWithNonExistingKey();
        testContainsKeyWithExistingKey();
    }

    private static void testContainsKeyWithNullKey() {
        TreeMap<String, String> map = new TreeMap<>();
        try {
            boolean result = map.containsKey(null);
            System.out.println("containsKey(null) = " + result);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException thrown as expected for null key");
        }
    }

    private static void testContainsKeyWithNonExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "alpha");
        boolean result = map.containsKey("b");
        System.out.println("containsKey("b") = " + result);
    }

    private static void testContainsKeyWithExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("x", "ex" );
        boolean result = map.containsKey("x");
        System.out.println("containsKey("x") = " + result);
    }
}
