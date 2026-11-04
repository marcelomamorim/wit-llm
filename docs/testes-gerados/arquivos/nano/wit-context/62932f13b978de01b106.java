/*
 * @test
 * @summary Tests for jdk.internal.org.objectweb.asm.tree.analysis.Frame.getLocal(int)
 * @run main FrameWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class FrameWitupTest {

    public static void main(String[] args) {
        testGetLocalValidIndex();
        testGetLocalInvalidIndex();
    }

    public static void testGetLocalValidIndex() {
        // Cria um array de valores de exemplo
        Object[] values = {"a", "b", "c"};
        // Cria uma instância de Frame com 3 locais
        jdk.internal.org.objectweb.asm.tree.analysis.Frame<Object> frame = new jdk.internal.org.objectweb.asm.tree.analysis.Frame<>(3, 3);
        // Inicializa os valores internos
        for (int i = 0; i < values.length; i++) {
            frame.values[i] = values[i];
        }
        // Verifica se getLocal retorna o valor esperado
        Object result = null;
        try {
            result = frame.getLocal(1);
        } catch (IndexOutOfBoundsException e) {
            // Caso inesperado, falha o teste
            throw new AssertionError("Unexpected IndexOutOfBoundsException for valid index", e);
        }
        if (result == null || !result.equals("b")) {
            throw new AssertionError("Expected 'b', got: " + result);
        }
        System.out.println("testGetLocalValidIndex passed.");
    }

    public static void testGetLocalInvalidIndex() {
        // Cria uma instância de Frame com 2 locais
        jdk.internal.org.objectweb.asm.tree.analysis.Frame<Object> frame = new jdk.internal.org.objectweb.asm.tree.analysis.Frame<>(2, 2);
        // Tenta acessar índice inválido (igual a locals)
        try {
            frame.getLocal(2); // index igual a locals, deve lançar IndexOutOfBoundsException
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= locals");
        } catch (IndexOutOfBoundsException e) {
            // Esperado
            assert e.getMessage().contains("Trying to access an inexistant local variable") : "Unexpected exception message: " + e.getMessage();
        }
        System.out.println("testGetLocalInvalidIndex passed.");
    }
}
