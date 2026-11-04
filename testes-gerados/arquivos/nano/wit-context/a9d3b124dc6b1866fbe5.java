/*
 * @test
 * @summary Test for java.util.Collections.forEachRemaining with null Consumer
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
    }

    public static void testForEachRemainingWithNullAction() {
        Iterator<Integer> iterator = Collections.emptyIterator();
        try {
            iterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
