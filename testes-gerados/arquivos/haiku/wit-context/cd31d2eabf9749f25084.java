/*
 * @test
 * @summary Witup-derived tests for Collections.emptySet().forEach null handling
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Set;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNonNullActionIsNotInvoked();
    }

    static void testNullActionThrowsNPE() {
        Set<String> empty = Collections.emptySet();
        boolean threw = false;
        try {
            empty.forEach(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testNonNullActionIsNotInvoked() {
        Set<String> empty = Collections.emptySet();
        int[] count = new int[1];
        empty.forEach(s -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("action invoked " + count[0] + " times on empty set");
        }
    }
}
