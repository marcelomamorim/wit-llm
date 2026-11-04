/*
 * @test
 * @summary Test java.util.Vector.get(int) method behavior
 * @run main java.util.VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndex();
    }

    private static void testGetValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        v.add("two");

        String s0 = v.get(0);
        if (!"zero".equals(s0)) {
            throw new Error("Expected 'zero' at index 0, got: " + s0);
        }

        String s1 = v.get(1);
        if (!"one".equals(s1)) {
            throw new Error("Expected 'one' at index 1, got: " + s1);
        }

        String s2 = v.get(2);
        if (!"two".equals(s2)) {
            throw new Error("Expected 'two' at index 2, got: " + s2);
        }
    }

    private static void testGetInvalidIndex() {
        Vector<Integer> v = new Vector<>();
        v.add(10);
        v.add(20);

        try {
            v.get(2); // index == size, should throw
            throw new Error("Expected ArrayIndexOutOfBoundsException for index 2");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        try {
            v.get(-1); // negative index, should throw
            throw new Error("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
