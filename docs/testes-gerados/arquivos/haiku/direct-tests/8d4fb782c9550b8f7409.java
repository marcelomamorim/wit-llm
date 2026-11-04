/*
 * @test
 * @summary Verifies Map.replaceAll behavior through Collections.synchronizedMap, including null function handling
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllAppliesFunction();
        testReplaceAllNullFunctionOnNonEmptyMapThrowsNPE();
    }

    static void testReplaceAllAppliesFunction() {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        base.put("b", 2);
        Map<String, Integer> sync = Collections.synchronizedMap(base);
        sync.replaceAll((k, v) -> v * 10);
        if (sync.get("a") != 10 || sync.get("b") != 20) {
            throw new RuntimeException("replaceAll did not apply function: " + sync);
        }
    }

    static void testReplaceAllNullFunctionOnNonEmptyMapThrowsNPE() {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> sync = Collections.synchronizedMap(base);
        try {
            sync.replaceAll(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null function");
    }
}
