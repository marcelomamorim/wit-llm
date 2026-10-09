/* @test
   @summary Verifies the forEach method of HashMap with a simple BiConsumer.
   @run main HashMapWitupTest_P7d3f6a672d75
 */
import java.util.HashMap;
import java.util.function.BiConsumer;
public class HashMapWitupTest_P7d3f6a672d75 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        final StringBuilder result = new StringBuilder();
        BiConsumer<String, Integer> consumer = (k, v) -> result.append(k).append(v);
        map.forEach(consumer);
        String output = result.toString();
        boolean condition1 = output.contains("a1") && output.contains("b2");
        if (!condition1) {
            throw new AssertionError("forEach did not process all entries correctly: " + output);
        }
        // Test with empty map
        HashMap<String, Integer> emptyMap = new HashMap<>();
        final StringBuilder emptyResult = new StringBuilder();
        emptyMap.forEach((k, v) -> emptyResult.append(k).append(v));
        if (emptyResult.length() != 0) {
            throw new AssertionError("Empty map should not produce output: " + emptyResult.toString());
        }
        // Test with null action (should throw NullPointerException)
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null BiConsumer");
        }
    }
}
