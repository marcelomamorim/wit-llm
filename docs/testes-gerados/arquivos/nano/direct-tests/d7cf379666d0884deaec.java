/* @test    @summary Test retainAll method of ArrayList for various collection inputs    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithSubset();
        testRetainAllWithDisjoint();
        testRetainAllWithSuperset();
        testRetainAllWithIdentical();
    }

    static void testRetainAllWithNullCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        try {
            list.retainAll(null);
        } catch (NullPointerException e) {
            // Expected exception
        }
        assert list.equals(Arrays.asList("a", "b", "c"));
    }

    static void testRetainAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        boolean changed = list.retainAll(Arrays.asList());
        assert changed;
        assert list.isEmpty();
    }

    static void testRetainAllWithSubset() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        boolean changed = list.retainAll(Arrays.asList("a", "b"));
        assert changed;
        assert list.equals(Arrays.asList("a", "b"));
    }

    static void testRetainAllWithDisjoint() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        boolean changed = list.retainAll(Arrays.asList("x", "y"));
        assert changed;
        assert list.isEmpty();
    }

    static void testRetainAllWithSuperset() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        boolean changed = list.retainAll(Arrays.asList("a", "b", "c"));
        assert !changed;
        assert list.equals(Arrays.asList("a", "b"));
    }

    static void testRetainAllWithIdentical() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        boolean changed = list.retainAll(Arrays.asList("a", "b", "c"));
        assert !changed;
        assert list.equals(Arrays.asList("a", "b", "c"));
    }
}
