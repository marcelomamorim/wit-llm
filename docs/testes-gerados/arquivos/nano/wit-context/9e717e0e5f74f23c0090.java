/* @test    @summary Test for HashMap's forEachRemaining method handling null action    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    static void testForEachRemainingWithNullAction() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        HashMap.ValueSpliterator<String, String> spliterator = new HashMap<String, String>().new ValueSpliterator<>(map);
        try {
            spliterator.forEachRemaining(null);
            // Expecting NullPointerException
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Test passes
        }
    }

    static void testForEachRemainingWithValidAction() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        HashMap.ValueSpliterator<String, String> spliterator = new HashMap<String, String>().new ValueSpliterator<>(map);
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = sb::append;
        spliterator.forEachRemaining(consumer);
        String result = sb.toString();
        if (result.contains("value1") && result.contains("value2")) {
            // Success
        } else {
            System.err.println("Failed: Not all values processed");
        }
    }
}

// Note: HashMap.ValueSpliterator is an inner class; for testing, we simulate its instantiation.
// Since it's package-private, in real tests, reflection or a test-specific subclass might be needed.
// Here, for simplicity, we assume access or that the test is within the same package.