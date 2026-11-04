/*
 * @test
 * @summary Test for Collections.replaceAll method to observe behavior and exceptions.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllThrowsException();
    }

    public static void testReplaceAllThrowsException() {
        try {
            // Invoca o método replaceAll com um operador qualquer
            Collections.replaceAll(new java.util.ArrayList<>(), (x) -> x);
            // Como o método lança UnsupportedOperationException, a execução deve chegar aqui se não lançar
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
