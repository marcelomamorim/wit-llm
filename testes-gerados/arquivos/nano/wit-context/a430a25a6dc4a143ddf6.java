/*
 * @test
 * @summary Test for tryAdvance method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithSingleElement();
    }

    private static void testTryAdvanceWithNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.tryAdvance(null);
            System.out.println("testTryAdvanceWithNullAction: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testTryAdvanceWithNullAction: Passed");
        } catch (Exception e) {
            System.out.println("testTryAdvanceWithNullAction: Failed - wrong exception: " + e);
        }
    }

    private static void testTryAdvanceWithEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean result = map.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                System.out.println("Accepted: " + s);
            }
        });
        if (result == false) {
            System.out.println("testTryAdvanceWithEmptyMap: Passed");
        } else {
            System.out.println("testTryAdvanceWithEmptyMap: Failed - expected false");
        }
    }

    private static void testTryAdvanceWithSingleElement() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        final boolean[] accepted = {false};
        boolean result = map.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                if (s.equals("value")) {
                    accepted[0] = true;
                }
            }
        });
        if (result == true && accepted[0]) {
            System.out.println("testTryAdvanceWithSingleElement: Passed");
        } else {
            System.out.println("testTryAdvanceWithSingleElement: Failed");
        }
    }
}
