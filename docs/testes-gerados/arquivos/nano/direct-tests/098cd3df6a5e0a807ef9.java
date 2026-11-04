/* @test    @summary Test for ArrayList.set method to ensure it updates element at lastRet index and throws exception if lastRet < 0    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetWithValidLastRet();
        testSetWithInvalidLastRet();
    }

    private static void testSetWithValidLastRet() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        // Simulate lastRet as index 1
        int lastRet = 1;
        // Save original value
        String original = list.get(lastRet);
        String newValue = "z";
        // Set new value at lastRet
        list.set(lastRet, newValue);
        // Verify the value was updated
        assert list.get(lastRet).equals(newValue) : "Element at lastRet should be updated to 'z'";
        // Verify other elements remain unchanged
        assert list.get(0).equals("a") : "First element should remain 'a'";
        assert list.get(2).equals("c") : "Third element should remain 'c'";
    }

    private static void testSetWithInvalidLastRet() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        // Set lastRet to invalid value (-1)
        int lastRet = -1;
        boolean exceptionThrown = false;
        try {
            list.set(lastRet, "new");
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Setting with invalid lastRet should throw IndexOutOfBoundsException";
    }
}
