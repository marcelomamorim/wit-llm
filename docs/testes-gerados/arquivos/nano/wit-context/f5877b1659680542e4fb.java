/*
 * @test
 * @summary Testa o método replaceAll de Collections com entrada nula.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllWithNullOperator();
    }

    public static void testReplaceAllWithNullOperator() {
        try {
            Collections.replaceAll(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
