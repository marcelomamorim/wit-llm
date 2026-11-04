/* @test    @summary Test for Collections.replaceAll to observe behavior and exception handling    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullOperator();
        testReplaceAllWithDummyOperator();
    }

    private static void testReplaceAllWithNullOperator() {
        try {
            Collections.replaceAll(null);
            System.out.println("Failed: Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null collection");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception for null collection: " + t);
        }
    }

    private static void testReplaceAllWithDummyOperator() {
        java.util.List<String> list = java.util.Arrays.asList("a", "b", "c");
        UnaryOperator<String> operator = s -> s.toUpperCase();
        try {
            Collections.replaceAll(list, operator);
            System.out.println("Note: replaceAll method throws UnsupportedOperationException as expected");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: UnsupportedOperationException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        }
    }
}
