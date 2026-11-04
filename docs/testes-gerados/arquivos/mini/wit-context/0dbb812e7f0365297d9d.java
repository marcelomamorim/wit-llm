/*
 * @test
 * @summary Test java.util.Arrays.asList(T... a) for null input and normal usage
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testAsListNullArgument();
        testAsListNormalUsage();
    }

    private static void testAsListNullArgument() {
        try {
            Arrays.asList((Object[]) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testAsListNormalUsage() {
        String[] data = {"a", "b", "c"};
        List<String> list = Arrays.asList(data);
        if (list.size() != data.length) {
            throw new RuntimeException("List size does not match array length");
        }
        for (int i = 0; i < data.length; i++) {
            if (!list.get(i).equals(data[i])) {
                throw new RuntimeException("List element does not match array element at index " + i);
            }
        }
    }
}
