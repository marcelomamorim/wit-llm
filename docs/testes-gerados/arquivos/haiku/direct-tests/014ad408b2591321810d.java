/*
 * @test
 * @summary Checks that Collections.checkedMap rejects null arguments and delegates to the backing map
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    static int failures = 0;

    static void fail(String msg) {
        failures++;
        System.err.println("FAIL: " + msg);
    }

    static void testNullMapThrowsNPE() {
        try {
            Collections.checkedMap(null, String.class, Integer.class);
            fail("expected NullPointerException for null map");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullKeyTypeThrowsNPE() {
        try {
            Collections.checkedMap(new HashMap<String, Integer>(), null, Integer.class);
            fail("expected NullPointerException for null keyType");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullValueTypeThrowsNPE() {
        try {
            Collections.checkedMap(new HashMap<String, Integer>(), String.class, null);
            fail("expected NullPointerException for null valueType");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testWrapperDelegatesToBackingMap() {
        HashMap<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> checked = Collections.checkedMap(backing, String.class, Integer.class);

        if (checked.get("a") == null || checked.get("a").intValue() != 1) {
            fail("wrapper get did not return backing value");
        }

        checked.put("b", 2);
        if (backing.get("b") == null || backing.get("b").intValue() != 2) {
            fail("wrapper put did not reach backing map");
        }
        if (checked.size() != 2 || backing.size() != 2) {
            fail("size mismatch between wrapper and backing map");
        }
    }

    public static void main(String[] args) {
        testNullMapThrowsNPE();
        testNullKeyTypeThrowsNPE();
        testNullValueTypeThrowsNPE();
        testWrapperDelegatesToBackingMap();
        if (failures > 0) {
            throw new RuntimeException(failures + " test(s) failed");
        }
    }
}
