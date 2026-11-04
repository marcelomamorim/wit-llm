/* @test    @summary Test for WeakHashMap.tryAdvance with null Consumer    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        try {
            map.tryAdvance(null);
            System.out.println("testTryAdvanceWithNullConsumer: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testTryAdvanceWithNullConsumer: Passed");
        } catch (Throwable t) {
            System.out.println("testTryAdvanceWithNullConsumer: Failed - unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        Consumer<String> consumer = (k) -> {
            if (k.equals("key1")) {
                System.out.println("Consumer accepted key: " + k);
            } else {
                System.out.println("Consumer received unexpected key: " + k);
            }
        };
        boolean result = false;
        try {
            result = map.tryAdvance(consumer);
        } catch (Throwable t) {
            System.out.println("testTryAdvanceWithValidConsumer: Failed - exception: " + t);
            return;
        }
        if (result) {
            System.out.println("testTryAdvanceWithValidConsumer: Passed - returned true");
        } else {
            System.out.println("testTryAdvanceWithValidConsumer: Passed - returned false");
        }
    }
}
