/* @test    @summary Test for TreeMap.remove() method to observe behavior and exceptions    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutEntries();
        testRemoveWithEntries();
        testRemoveAfterClear();
    }

    static void testRemoveWithoutEntries() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.remove();
            System.out.println("testRemoveWithoutEntries: No exception");
        } catch (Exception e) {
            System.out.println("testRemoveWithoutEntries: Exception - " + e.getClass().getName());
        }
    }

    static void testRemoveWithEntries() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.remove();
            System.out.println("testRemoveWithEntries: No exception");
        } catch (Exception e) {
            System.out.println("testRemoveWithEntries: Exception - " + e.getClass().getName());
        }
    }

    static void testRemoveAfterClear() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.clear();
        try {
            map.remove();
            System.out.println("testRemoveAfterClear: No exception");
        } catch (Exception e) {
            System.out.println("testRemoveAfterClear: Exception - " + e.getClass().getName());
        }
    }
}
