/* @test    @summary Test replaceAll method of ArrayList for correct element replacement    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNonNullOperator();
        testReplaceAllWithNullOperator();
    }

    private static void testReplaceAllWithNonNullOperator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        UnaryOperator<String> toUpperCase = String::toUpperCase;
        list.replaceAll(toUpperCase);
        assert list.get(0).equals("A") : "Expected 'A' at index 0";
        assert list.get(1).equals("B") : "Expected 'B' at index 1";
        assert list.get(2).equals("C") : "Expected 'C' at index 2";
    }

    private static void testReplaceAllWithNullOperator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        UnaryOperator<String> nullOperator = null;
        try {
            list.replaceAll(nullOperator);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        // Verify list remains unchanged
        assert list.get(0).equals("x") : "Expected 'x' at index 0";
        assert list.get(1).equals("y") : "Expected 'y' at index 1";
        assert list.get(2).equals("z") : "Expected 'z' at index 2";
    }
}
