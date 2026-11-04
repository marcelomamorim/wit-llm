/* @test    @summary Test ArrayList.add(E e) method for basic functionality and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddWithValidElement();
        testAddWithNullElement();
        testAddAtSpecificIndex();
        testAddAtInvalidIndex();
    }

    static void testAddWithValidElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element");
        assert list.size() == 1 : "Size should be 1 after adding one element";
        assert "element".equals(list.get(0)) : "First element should be 'element'";
    }

    static void testAddWithNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        assert list.size() == 1 : "Size should be 1 after adding null";
        assert list.get(0) == null : "First element should be null";
    }

    static void testAddAtSpecificIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add(1, "c");
        assert list.size() == 3 : "Size should be 3 after insertion";
        assert "a".equals(list.get(0)) : "First element should be 'a'";
        assert "c".equals(list.get(1)) : "Second element should be 'c'";
        assert "b".equals(list.get(2)) : "Third element should be 'b'";
    }

    static void testAddAtInvalidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean exceptionThrown = false;
        try {
            list.add(5, "b");
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Adding at invalid index should throw IndexOutOfBoundsException";
    }
}
