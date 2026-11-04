/* @test    @summary Test for ArrayList.replaceAll with null operator to verify NullPointerException is thrown.    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullOperator();
    }

    public static void testReplaceAllWithNullOperator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.replaceAll(null);
            // Se não lançar exceção, falha o teste
            throw new AssertionError("Expected NullPointerException was not thrown.");
        } catch (NullPointerException expected) {
            // Teste passou, exceção esperada
        }
    }
}
