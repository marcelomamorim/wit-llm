/*
 * @test
 * @summary Test for Runtime.compareToIgnoreOptional method, focusing on null input.
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testCompareToIgnoreOptionalWithNull();
    }

    public static void testCompareToIgnoreOptionalWithNull() {
        Runtime runtime = Runtime.getRuntime();
        try {
            // Chamada com argumento nulo para verificar se lança NullPointerException
            runtime.compareToIgnoreOptional(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Exceção esperada, teste passa
        }
    }
}
