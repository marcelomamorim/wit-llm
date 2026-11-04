/*
 * @test
 * @summary Test Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntrySetSpliterator.forEachRemaining with null and valid Consumer
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.Objects;
import java.util.Iterator;
import java.util.Map.Entry;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingValidAction();
    }

    static void testForEachRemainingNullAction() {
        // Setup a simple unmodifiable map and get its entry set spliterator
        Map<String, String> map = Collections.singletonMap("key", "value");
        Set<Map.Entry<String, String>> entrySet = Collections.unmodifiableMap(map).entrySet();
        java.util.Spliterator<Map.Entry<String, String>> spliterator = entrySet.spliterator();

        // The forEachRemaining method is on the spliterator, but the tested method is from
        // UnmodifiableEntrySetSpliterator inner class, which is the spliterator returned by entrySet.spliterator()

        // Test that passing null to forEachRemaining throws NullPointerException
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testForEachRemainingValidAction() {
        Map<String, String> map = Collections.singletonMap("key", "value");
        Set<Map.Entry<String, String>> entrySet = Collections.unmodifiableMap(map).entrySet();
        java.util.Spliterator<Map.Entry<String, String>> spliterator = entrySet.spliterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                sb.append(e.getKey()).append("=").append(e.getValue());
            }
        };

        spliterator.forEachRemaining(action);

        if (!"key=value".equals(sb.toString())) {
            throw new AssertionError("forEachRemaining did not process entries correctly: " + sb.toString());
        }
    }
}
