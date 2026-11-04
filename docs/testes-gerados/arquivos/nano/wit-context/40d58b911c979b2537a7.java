/*
 * @test
 * @summary Test for WeakHashMap.forEach with null BiConsumer
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.function.BiConsumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
    }

    public static void testForEachWithNullAction() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            map.forEach(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null action.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
