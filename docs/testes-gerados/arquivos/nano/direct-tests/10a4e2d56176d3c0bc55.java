/* @test    @summary Test for CopyOnWriteArrayList.toArray() method    @run main CopyOnWriteArrayListWitupTest */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testToArrayEmpty();
        testToArrayWithElements();
        testToArrayNullElements();
    }

    static void testToArrayEmpty() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        Object[] array = list.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 0 : "Array length should be 0";
    }

    static void testToArrayWithElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Object[] array = list.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 3 : "Array length should be 3";
        assert array[0].equals("a") : "First element should be 'a'";
        assert array[1].equals("b") : "Second element should be 'b'";
        assert array[2].equals("c") : "Third element should be 'c'";
    }

    static void testToArrayNullElements() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        list.add(null);
        list.add("test");
        Object[] array = list.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 2 : "Array length should be 2";
        assert array[0] == null : "First element should be null";
        assert array[1].equals("test") : "Second element should be 'test'";
    }
}
