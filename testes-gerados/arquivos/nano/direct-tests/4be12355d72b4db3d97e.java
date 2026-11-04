/* @test    @summary Test ArrayList.set method for correct replacement and return of old value    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetReplacesValueAndReturnsOld();
        testSetWithInvalidIndexThrows();
    }

    static void testSetReplacesValueAndReturnsOld() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String old = list.set(1, "c");
        assert "b".equals(old) : "Expected old value 'b', got " + old;
        assert "c".equals(list.get(1)) : "Expected new value 'c', got " + list.get(1);
        assert list.size() == 2 : "List size should remain 2";
    }

    static void testSetWithInvalidIndexThrows() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        boolean thrown = false;
        try {
            list.set(5, "y");
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assert thrown : "Expected IndexOutOfBoundsException for invalid index";
    }
}
