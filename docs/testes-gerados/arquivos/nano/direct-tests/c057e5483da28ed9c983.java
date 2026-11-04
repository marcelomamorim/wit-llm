/* @test    @summary Test for ArrayList.size() method to ensure correct size reporting.    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyListSize();
        testListWithElementsSize();
    }

    public static void testEmptyListSize() {
        ArrayList<Object> list = new ArrayList<>();
        int size = list.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0 for empty list, but got " + size);
        }
    }

    public static void testListWithElementsSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int size = list.size();
        if (size != 3) {
            throw new AssertionError("Expected size 3, but got " + size);
        }
    }
}
