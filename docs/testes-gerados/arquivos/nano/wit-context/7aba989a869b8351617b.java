/*
 * @test
 * @summary Tests for java.util.Collections synchronizedMap method
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testSynchronizedMapWithNull();
        testSynchronizedMapWithValidMap();
    }

    private static void testSynchronizedMapWithNull() {
        try {
            Map<Object, Object> result = Collections.synchronizedMap(null);
            // Espera-se NullPointerException
            System.out.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null input");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testSynchronizedMapWithValidMap() {
        Map<String, String> originalMap = new HashMap<>();
        originalMap.put("key1", "value1");
        Map<String, String> syncMap = Collections.synchronizedMap(originalMap);
        if (syncMap != null && syncMap.get("key1").equals("value1")) {
            System.out.println("PASS: synchronizedMap returns a map with expected content");
        } else {
            System.out.println("FAIL: synchronizedMap did not return expected map");
        }
    }
}
