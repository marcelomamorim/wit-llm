/* @test    @summary Test retainAll method of ArrayList for correct behavior and regressions    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRetainAllWithExistingElements();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNoMatchingElements();
        testRetainAllWithNullCollection();
    }

    static void testRetainAllWithExistingElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> c = Arrays.asList("a", "b");
        boolean changed = list.retainAll(c);
        assert changed : "Expected list to be changed";
        assert list.size() == 2 : "Expected size 2";
        assert list.contains("a") : "List should contain 'a'";
        assert list.contains("b") : "List should contain 'b'";
    }

    static void testRetainAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> c = Arrays.asList();
        boolean changed = list.retainAll(c);
        assert changed : "Expected list to be changed";
        assert list.isEmpty() : "List should be empty";
    }

    static void testRetainAllWithNoMatchingElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> c = Arrays.asList("x", "y");
        boolean changed = list.retainAll(c);
        assert changed : "Expected list to be changed";
        assert list.isEmpty() : "List should be empty";
    }

    static void testRetainAllWithNullCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        try {
            list.retainAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
