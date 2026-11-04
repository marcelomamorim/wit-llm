/*
 * @test
 * @summary Test for Collections.putAll to verify behavior with null and non-null maps.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testPutAllWithNullMap();
        testPutAllWithValidMap();
    }

    private static void testPutAllWithNullMap() {
        Map<String, String> map = new HashMap<>();
        try {
            Collections.putAll(map, null);
            // Expecting NullPointerException as per Map interface contract
            System.out.println("FAIL: Expected NullPointerException for null map");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testPutAllWithValidMap() {
        Map<String, String> map = new HashMap<>();
        Map<String, String> source = new HashMap<>();
        source.put("key1", "value1");
        source.put("key2", "value2");
        try {
            Collections.putAll(map, source);
            // Since the method throws UnsupportedOperationException, check for it
            System.out.println("FAIL: Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
        // Verify that map remains empty after attempt
        if (map.isEmpty()) {
            System.out.println("PASS: Map remains empty after putAll attempt");
        } else {
            System.out.println("FAIL: Map was modified unexpectedly");
        }
    }
}
