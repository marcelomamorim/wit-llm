/* @test    @summary Test for Comparator.comparingDouble with null keyExtractor    @run main ComparatorWitupTest */

import java.util.Comparator;
import java.util.Objects;

public class ComparatorWitupTest {

    public static void main(String[] args) {
        testComparingDoubleWithNull();
        testComparingDoubleWithValidFunction();
    }

    private static void testComparingDoubleWithNull() {
        try {
            Comparator<Object> comp = Comparator.comparingDouble(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Esperado
        }
    }

    private static void testComparingDoubleWithValidFunction() {
        java.util.function.ToDoubleFunction<Object> func = obj -> 1.0;
        Comparator<Object> comp = Comparator.comparingDouble(func);
        Objects.requireNonNull(comp); // Apenas para verificar que não lança
    }
}
