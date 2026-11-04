/*
 * @test
 * @summary Testa o método Arrays.setAll para entradas válidas e condições de exceção.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetAllWithValidGenerator();
        testSetAllWithNullGenerator();
        testSetAllWithNullArray();
    }

    static void testSetAllWithValidGenerator() {
        Integer[] array = new Integer[3];
        IntFunction<Integer> generator = i -> i * 10;
        Arrays.setAll(array, generator);
        if (array[0] != 0 || array[1] != 10 || array[2] != 20) {
            throw new AssertionError("Failed: valores incorretos após setAll com gerador válido");
        }
    }

    static void testSetAllWithNullGenerator() {
        Integer[] array = new Integer[2];
        try {
            Arrays.setAll(array, null);
            throw new AssertionError("Falha: esperado NullPointerException ao passar generator null");
        } catch (NullPointerException e) {
            // esperado
        }
    }

    static void testSetAllWithNullArray() {
        try {
            Arrays.setAll(null, i -> i);
            throw new AssertionError("Falha: esperado NullPointerException ao passar array null");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
