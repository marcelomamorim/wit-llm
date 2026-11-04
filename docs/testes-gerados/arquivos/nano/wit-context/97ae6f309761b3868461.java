/*
 * @test
 * @summary Testa o método Collections.forEach com entrada nula.
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
            System.out.println("FAIL: Esperado NullPointerException ao passar action nulo.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException capturada como esperado.");
        } catch (Throwable t) {
            System.out.println("FAIL: Exceção inesperada: " + t);
        }
    }
}
