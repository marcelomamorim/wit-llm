/* @test    @summary Test for Vector.replaceAll method to verify behavior with null operator and normal operation    @run main VectorWitupTest */

import java.util.Vector;
import java.util.function.UnaryOperator;

public class VectorWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullOperator();
        testReplaceAllWithValidOperator();
    }

    private static void testReplaceAllWithNullOperator() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.replaceAll(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testReplaceAllWithValidOperator() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        UnaryOperator<Integer> squareOperator = new UnaryOperator<Integer>() {
            public Integer apply(Integer x) {
                return x * x;
            }
        };
        vector.replaceAll(squareOperator);
        if (vector.get(0) == 1 && vector.get(1) == 4 && vector.get(2) == 9) {
            System.out.println("PASS: replaceAll applied correctly");
        } else {
            System.out.println("FAIL: replaceAll did not apply correctly");
        }
    }
}
