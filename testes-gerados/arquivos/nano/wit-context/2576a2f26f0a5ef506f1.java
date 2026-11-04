/*
 * @test    @summary Test subMap method for null keys to verify NullPointerException
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testSubMapNullFromKey();
        testSubMapNullToKey();
    }

    private static void testSubMapNullFromKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.subMap(null, 10);
            System.out.println("FAIL: Expected NullPointerException for null fromKey");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null fromKey");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null fromKey: " + e);
        }
    }

    private static void testSubMapNullToKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.subMap(0, null);
            System.out.println("FAIL: Expected NullPointerException for null toKey");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null toKey");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null toKey: " + e);
        }
    }
}
