/* @test    @summary Test HashSet(Collection<? extends E> c) constructor with various collections    @run main HashSetWitupTest */

import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testEmptyCollection();
        testCollectionWithElements();
        testCollectionWithNull();
        testCollectionWithDuplicates();
    }

    static void testEmptyCollection() {
        HashSet<String> set = new HashSet<>(Collections.emptyList());
        assert set.size() == 0 : "Expected size 0 for empty collection";
    }

    static void testCollectionWithElements() {
        HashSet<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
        assert set.contains(1) : "Set should contain 1";
        assert set.contains(2) : "Set should contain 2";
        assert set.contains(3) : "Set should contain 3";
        assert set.size() == 3 : "Expected size 3";
    }

    static void testCollectionWithNull() {
        HashSet<String> set = new HashSet<>(Collections.singletonList(null));
        assert set.contains(null) : "Set should contain null";
        assert set.size() == 1 : "Expected size 1";
    }

    static void testCollectionWithDuplicates() {
        HashSet<Integer> set = new HashSet<>(Arrays.asList(1, 2, 2, 3, 3, 3));
        assert set.contains(1) : "Set should contain 1";
        assert set.contains(2) : "Set should contain 2";
        assert set.contains(3) : "Set should contain 3";
        assert set.size() == 3 : "Expected size 3 after duplicates";
    }
}
