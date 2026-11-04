/* @test    @summary Test ArrayList.toArray() method for correct array output and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testToArrayEmpty();
        testToArrayWithElements();
        testToArrayAfterModification();
    }

    static void testToArrayEmpty() {
        ArrayList<Object> list = new ArrayList<>();
        Object[] array = list.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 0 : "Array length should be 0";
    }

    static void testToArrayWithElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Object[] array = list.toArray();
        assert array.length == 3 : "Array length should be 3";
        assert array[0].equals("a") : "First element should be 'a'";
        assert array[1].equals("b") : "Second element should be 'b'";
        assert array[2].equals("c") : "Third element should be 'c'";
    }

    static void testToArrayAfterModification() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Object[] arrayBefore = list.toArray();
        list.add(4);
        Object[] arrayAfter = list.toArray();
        assert arrayBefore.length == 3 : "Array before modification should have length 3";
        assert arrayAfter.length == 4 : "Array after modification should have length 4";
        assert arrayBefore[0].equals(1) : "First element before modification should be 1";
        assert arrayAfter[3].equals(4) : "Fourth element after modification should be 4";
    }
}
