/* @test    @summary Test for TreeMap.lastKey() method to observe behavior and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testLastKeyThrowsInternalError();
        testLastKeyEmptyMap();
        testLastKeyWithEntries();
    }

    private static void testLastKeyThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.lastKey();
            System.out.println("FAIL: Expected InternalError not thrown");
        } catch (InternalError e) {
            System.out.println("PASS: InternalError thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testLastKeyEmptyMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            Integer key = map.lastKey();
            if (key == null) {
                System.out.println("FAIL: lastKey() returned null on empty map");
            } else {
                System.out.println("PASS: lastKey() returned: " + key);
            }
        } catch (InternalError e) {
            System.out.println("PASS: InternalError thrown on empty map as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testLastKeyWithEntries() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            Integer key = map.lastKey();
            if (key != null && key == 2) {
                System.out.println("PASS: lastKey() returned the highest key: " + key);
            } else {
                System.out.println("FAIL: lastKey() returned: " + key);
            }
        } catch (InternalError e) {
            System.out.println("FAIL: Unexpected InternalError with entries");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
