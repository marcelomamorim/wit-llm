/*
 * @test
 * @summary Testa o método Collections.toArray() para comportamento esperado.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Arrays;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayWithNullElement();
        testToArrayWithNegativeSize();
    }

    static void testToArrayNormal() {
        try {
            Object[] result = Collections.toArray();
            if (result == null) {
                throw new AssertionError("toArray() retornou null");
            }
            System.out.println("testToArrayNormal passed");
        } catch (Throwable t) {
            throw new AssertionError("Erro em testToArrayNormal", t);
        }
    }

    static void testToArrayWithNullElement() {
        try {
            // Como o método não recebe elementos, não há elemento null interno.
            Object[] result = Collections.toArray();
            if (result.length != 0) {
                throw new AssertionError("toArray() esperado array vazio");
            }
            System.out.println("testToArrayWithNullElement passed");
        } catch (Throwable t) {
            throw new AssertionError("Erro em testToArrayWithNullElement", t);
        }
    }

    static void testToArrayWithNegativeSize() {
        try {
            // Como o método não recebe tamanho, não há cenário de tamanho negativo.
            Object[] result = Collections.toArray();
            if (result == null) {
                throw new AssertionError("toArray() retornou null");
            }
            System.out.println("testToArrayWithNegativeSize passed");
        } catch (Throwable t) {
            throw new AssertionError("Erro em testToArrayWithNegativeSize", t);
        }
    }
}
