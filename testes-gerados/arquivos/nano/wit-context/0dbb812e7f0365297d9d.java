/*
 * @test
 * @summary Testa o método Arrays.asList(T... a) para verificar comportamento com entrada nula.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testAsListWithNull();
        testAsListWithEmptyArray();
        testAsListWithValidArray();
    }

    private static void testAsListWithNull() {
        try {
            List<Object> result = Arrays.asList((Object[]) null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Esperado
        }
    }

    private static void testAsListWithEmptyArray() {
        List<Object> result = Arrays.asList(new Object[0]);
        if (result.size() != 0) {
            throw new AssertionError("Expected empty list for empty array");
        }
    }

    private static void testAsListWithValidArray() {
        String[] array = {"a", "b", "c"};
        List<String> list = Arrays.asList(array);
        if (list.size() != 3 || !list.get(0).equals("a") || !list.get(1).equals("b") || !list.get(2).equals("c")) {
            throw new AssertionError("Unexpected list contents");
        }
    }
}
