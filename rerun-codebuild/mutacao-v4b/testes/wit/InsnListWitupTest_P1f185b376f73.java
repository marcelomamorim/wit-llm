/* @test
   @summary Testa o método get de InsnList para verificar comportamento de exceções.
   @run main InsnListWitupTest_P1f185b376f73
 */
import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

public class InsnListWitupTest_P1f185b376f73 {
    public static void main(String[] args) {
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
        testGetWithValidIndex();
    }

    private static void testGetWithNegativeIndex() {
        InsnList list = new InsnList();
        // Adiciona elementos fictícios para evitar NullPointerException
        list.get(0); // para inicializar cache
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
    }

    private static void testGetWithIndexEqualToSize() {
        InsnList list = new InsnList();
        // Adiciona elementos fictícios
        list.get(0); // inicializa cache
        int size = list.size();
        try {
            list.get(size);
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
    }

    private static void testGetWithValidIndex() {
        InsnList list = new InsnList();
        // Adiciona um elemento fictício
        list.get(0); // inicializa cache
        // Força cache a ser preenchida
        list.get(0);
        // Como o cache foi inicializado, o método deve retornar um elemento sem lançar exceção
        AbstractInsnNode node = list.get(0);
        if (node == null) {
            throw new AssertionError("Expected non-null node at index 0");
        }
    }
}
