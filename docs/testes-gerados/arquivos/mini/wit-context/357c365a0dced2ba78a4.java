/*
 * @test
 * @summary Test java.util.Collections.UnmodifiableMap.UnmodifiableEntrySet.forEach method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachInvocation();
    }

    private static void testForEachNullAction() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        Set<Map.Entry<String, String>> unmodifiableEntrySet = Collections.unmodifiableMap(map).entrySet();

        try {
            unmodifiableEntrySet.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachInvocation() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        Set<Map.Entry<String, String>> unmodifiableEntrySet = Collections.unmodifiableMap(map).entrySet();

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append(",");
            }
        };

        unmodifiableEntrySet.forEach(action);

        String result = sb.toString();
        if (!(result.contains("a=1") && result.contains("b=2"))) {
            throw new RuntimeException("forEach did not process all entries correctly: " + result);
        }
    }
}
