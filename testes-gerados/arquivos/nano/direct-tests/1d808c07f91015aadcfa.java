/* @test    @summary Test for ArrayList.set method to ensure correct behavior and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetWithValidIndex();
        testSetWithInvalidIndex();
    }

    private static void testSetWithValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("initial");
        list.add("second");
        list.set(1, "updated");
        assert "updated" .equals(list.get(1)) : "Expected 'updated' at index 1";
    }

    private static void testSetWithInvalidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");
        boolean exceptionThrown = false;
        try {
            list.set(5, "fail");
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for invalid index";
    }
}
