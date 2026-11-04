/*
 * @test
 * @summary Test addAll(Collection<? extends E> c) method of ArrayList
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddAllWithNonEmptyCollection();
        testAddAllWithEmptyCollection();
        testAddAllWithNullCollection();
    }

    static void testAddAllWithNonEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Collection<String> c = new java.util.ArrayList<>();
        c.add("c");
        c.add("d");
        boolean result = list.addAll(c);
        assert result : "addAll should return true when collection is added";
        assert list.size() == 4 : "List size should be 4 after addAll";
        assert list.get(2).equals("c") : "Third element should be 'c'";
        assert list.get(3).equals("d") : "Fourth element should be 'd'";
    }

    static void testAddAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        Collection<String> c = new java.util.ArrayList<>();
        boolean result = list.addAll(c);
        assert !result : "addAll should return false when collection is empty";
        assert list.size() == 2 : "List size should remain 2";
        assert list.get(0).equals("x") : "First element should be 'x'";
        assert list.get(1).equals("y") : "Second element should be 'y'";
    }

    static void testAddAllWithNullCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("m");
        list.add("n");
        try {
            list.addAll(null);
            assert false : "addAll with null should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        assert list.size() == 2 : "List size should remain 2 after null addAll";
        assert list.get(0).equals("m") : "First element should be 'm'";
        assert list.get(1).equals("n") : "Second element should be 'n'";
    }
}
