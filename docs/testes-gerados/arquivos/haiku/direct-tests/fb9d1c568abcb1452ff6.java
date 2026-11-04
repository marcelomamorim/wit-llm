/*
 * @test
 * @summary Checks null-argument rejection and observable wrapping behavior of Collections.checkedCollection, which reaches the CheckedCollection constructor
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        testNullCollectionRejected();
        testNullTypeRejected();
        testWrapsBackingCollection();
        testWrongElementTypeRejected();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void testNullCollectionRejected() {
        try {
            Collections.checkedCollection(null, String.class);
            fail("expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullTypeRejected() {
        Collection<String> backing = new ArrayList<>();
        try {
            Collections.checkedCollection(backing, null);
            fail("expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWrapsBackingCollection() {
        Collection<String> backing = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> checked = Collections.checkedCollection(backing, String.class);
        check(checked.size() == 2, "size should reflect backing collection");
        check(checked.contains("a"), "checked view should contain backing element");
        checked.add("c");
        check(backing.contains("c"), "add through view should affect backing collection");
        check(backing.size() == 3, "backing size should be 3 after add");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void testWrongElementTypeRejected() {
        Collection<String> backing = new ArrayList<>();
        Collection<String> checked = Collections.checkedCollection(backing, String.class);
        Collection raw = checked;
        try {
            raw.add(Integer.valueOf(1));
            fail("expected ClassCastException for wrong element type");
        } catch (ClassCastException expected) {
            // expected
        }
        check(backing.isEmpty(), "rejected element must not be added");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            System.err.println("FAILED: " + message);
            failures++;
        }
    }

    private static void fail(String message) {
        System.err.println("FAILED: " + message);
        failures++;
    }
}
