/*
 * @test
 * @summary Test java.util.ArrayList.replaceAll with null and valid UnaryOperator
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testReplaceAllWithNullOperator();
        testReplaceAllWithValidOperator();
    }

    private static void testReplaceAllWithNullOperator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testReplaceAllWithValidOperator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.replaceAll(new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                return s.toUpperCase();
            }
        });
        if (!"A".equals(list.get(0)) || !"B".equals(list.get(1))) {
            throw new RuntimeException("replaceAll did not correctly replace elements");
        }
    }
}
