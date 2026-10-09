/* @test    @summary Test HashMap forEach method with null action to trigger NullPointerException    @run main HashMapWitupTest_P4ccb607dc5c5 */

import java.util.HashMap;
import java.util.function.BiConsumer;

public class HashMapWitupTest_P4ccb607dc5c5 {

    public static void main(String[] args) {
        testForEachWithNullAction();
    }

    public static void testForEachWithNullAction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Test passes, exception was thrown as expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
