/*
 * @test
 * @summary Test for java.util.Collections.forEach with null action
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachWithNullAction();
    }

    public static void testForEachWithNullAction() {
        try {
            Collections.forEach(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
