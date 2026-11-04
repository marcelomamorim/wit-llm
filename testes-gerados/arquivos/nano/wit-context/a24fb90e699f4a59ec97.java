/*
 * @test
 * @summary Test for java.util.Spliterators.nextLong()
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNextLongNormal();
        testNextLongThrows();
    }

    static void testNextLongNormal() {
        // Cria uma implementação de Spliterator com comportamento controlado
        class DummySpliterator extends Spliterators.AbstractSpliterator<Long>(Long.MAX_VALUE, 0) {
            boolean valueReady = true;
            long nextElement = 42L;

            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Long> action) {
                if (valueReady) {
                    action.accept(nextElement);
                    valueReady = false;
                    return true;
                }
                return false;
            }
        }
        DummySpliterator spliterator = new DummySpliterator();
        // Simula estado válido
        // Como nextLong() não depende de tryAdvance, apenas chama o método
        long result = Spliterators.nextLong(spliterator);
        assert result == 42L : "Expected 42L";
    }

    static void testNextLongThrows() {
        // Cria uma implementação de Spliterator que lança exceção
        class ThrowingSpliterator extends Spliterators.AbstractSpliterator<Long>(Long.MAX_VALUE, 0) {
            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Long> action) {
                return false;
            }
        }
        ThrowingSpliterator spliterator = new ThrowingSpliterator();
        // Força condição para lançar exceção
        // Como nextLong() verifica !hasNext() e lança, simula condição
        try {
            Spliterators.nextLong(spliterator);
            assert false : "Expected NoSuchElementException";
        } catch (java.util.NoSuchElementException e) {
            // esperado
        }
    }
}
