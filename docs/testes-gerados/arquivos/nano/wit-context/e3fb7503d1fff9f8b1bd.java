/* @test    @summary Test for ArrayList.replaceAll with null operator to trigger NullPointerException    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullOperator();
        testReplaceAllWithValidOperator();
    }

    private static void testReplaceAllWithNullOperator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        try {
            list.replaceAll(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testReplaceAllWithValidOperator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.replaceAll(x -> x * 2);
        if (list.get(0) == 2 && list.get(1) == 4) {
            System.out.println("PASS: replaceAll with valid operator works as expected");
        } else {
            System.out.println("FAIL: replaceAll with valid operator did not produce expected result");
        }
    }
}
