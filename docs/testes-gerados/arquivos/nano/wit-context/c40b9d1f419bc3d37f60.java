/*
 * @test
 * @summary Test for StringUTF16.forEachRemaining with null Consumer
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;
import java.util.function.Consumer;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
    }

    public static void testForEachRemainingNullAction() {
        StringUTF16.LinesSpliterator spliterator = createSpliterator();
        try {
            spliterator.forEachRemaining(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException");
        }
    }

    private static StringUTF16.LinesSpliterator createSpliterator() {
        // Cria um objeto de exemplo que simula o comportamento necessário
        // Como o método é interno, criamos uma subclasse anônima para testar
        return new StringUTF16.LinesSpliterator() {
            @Override
            public void forEachRemaining(Consumer<? super String> action) {
                super.forEachRemaining(action);
            }
        };
    }
}
