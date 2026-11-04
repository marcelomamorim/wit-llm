/* @test    @summary Test for Hashtable constructor with Map parameter, focusing on exception paths and normal behavior    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidMap();
        testConstructorWithNullMap();
        testConstructorWithEmptyMap();
        testConstructorWithLargeMap();
        testConstructorWithNullPointer();
    }

    static void testConstructorWithValidMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        try {
            Hashtable<String, Integer> ht = new Hashtable<>(map);
            assert ht.size() == 2 : "Size should be 2";
            assert ht.get("one") == 1 : "Value for 'one' should be 1";
            assert ht.get("two") == 2 : "Value for 'two'";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid map constructor", e);
        }
    }

    static void testConstructorWithNullMap() {
        try {
            new Hashtable<>(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testConstructorWithEmptyMap() {
        Map<String, String> emptyMap = new HashMap<>();
        try {
            Hashtable<String, String> ht = new Hashtable<>(emptyMap);
            assert ht.size() == 0 : "Size should be 0";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in empty map constructor", e);
        }
    }

    static void testConstructorWithLargeMap() {
        Map<Integer, String> largeMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            largeMap.put(i, "value" + i);
        }
        try {
            Hashtable<Integer, String> ht = new Hashtable<>(largeMap);
            assert ht.size() == 100 : "Size should be 100";
            for (int i = 0; i < 100; i++) {
                assert "value" + i.equals(ht.get(i)) : "Value mismatch for key " + i;
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in large map constructor", e);
        }
    }

    static void testConstructorWithNullPointer() {
        try {
            Map<String, String> nullMap = null;
            new Hashtable<>(nullMap);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
