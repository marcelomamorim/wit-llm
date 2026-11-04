/* @test    @summary Test for TreeMap.tryAdvance with null Consumer    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.function.Consumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithEmptyMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Consumer<Integer> consumer = (k) -> {
            System.out.println("Consumed key: " + k);
        };
        boolean result = false;
        try {
            result = map.tryAdvance(consumer);
            if (result == false) {
                System.out.println("PASS: tryAdvance returned false on empty map");
            } else {
                System.out.println("FAIL: tryAdvance returned true on empty map");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Consumer<Integer> consumer = (k) -> {
            System.out.println("Consumed key: " + k);
        };
        boolean result;
        try {
            result = map.tryAdvance(consumer);
            if (result) {
                System.out.println("PASS: tryAdvance returned true with non-empty map");
            } else {
                System.out.println("FAIL: tryAdvance returned false with non-empty map");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
