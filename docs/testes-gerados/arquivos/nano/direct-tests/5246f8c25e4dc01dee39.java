/* @test    @summary Test the set method of Vector for correct behavior and exception handling    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testSetWithValidIndex();
        testSetWithoutLastRet();
    }

    private static void testSetWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        int indexToSet = 1;
        String newValue = "z";
        vector.set(indexToSet, newValue);
        assert vector.get(indexToSet).equals(newValue) : "Failed to set value at valid index";
    }

    private static void testSetWithoutLastRet() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        try {
            // Directly call set without prior setLastRet simulation
            // Expecting IllegalStateException as lastRet is -1
            vector.getClass().getDeclaredMethod("set", Object.class).invoke(vector, "test");
            assert false : "Expected IllegalStateException was not thrown";
        } catch (ReflectiveOperationException e) {
            Throwable cause = e.getCause();
            assert cause instanceof IllegalStateException : "Unexpected exception type: " + cause;
        }
    }
}
