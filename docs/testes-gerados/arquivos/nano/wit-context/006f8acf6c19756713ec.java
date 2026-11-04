/*
 * @test
 * @summary Testa o método forEachRemaining de Collections com entrada nula.
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
        Iterator<Integer> iterator = new Iterator<Integer>() {
            public boolean hasNext() { return false; }
            public Integer next() { return null; }
            public void remove() { }
        };
        try {
            iterator.forEachRemaining(null);
            // Espera-se NullPointerException
            System.out.println("Erro: NullPointerException não lançada");
        } catch (NullPointerException e) {
            System.out.println("Passou: NullPointerException lançada como esperado");
        } catch (Exception e) {
            System.out.println("Erro: Exceção inesperada: " + e);
        }
    }
}
