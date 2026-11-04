/*
 * @test
 * @summary Test Collections.SetFromMap.forEach method for null action and normal behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormal();
    }

    static void testForEachNullAction() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("key", Boolean.TRUE);
        Collections.SetFromMap<String> setFromMap = new Collections.SetFromMap<>(map);

        try {
            setFromMap.forEach(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachNormal() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("a", Boolean.TRUE);
        map.put("b", Boolean.TRUE);
        Collections.SetFromMap<String> setFromMap = new Collections.SetFromMap<>(map);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        setFromMap.forEach(action);

        String result = sb.toString();
        if (!(result.contains("a") && result.contains("b"))) {
            throw new Error("forEach did not process all elements: " + result);
        }
    }
}
