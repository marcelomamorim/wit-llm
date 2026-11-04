/*
 * @test
 * @summary Testa o método get de Collections para índices inválidos.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testGetWithInvalidIndex();
    }

    public static void testGetWithInvalidIndex() {
        try {
            Collections.get(-1);
            System.err.println("Erro: esperado IndexOutOfBoundsException para índice negativo.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passou: IndexOutOfBoundsException lançada para índice negativo.");
        }
        try {
            Collections.get(0); // Supondo que a lista esteja vazia, esse índice é inválido.
            System.err.println("Erro: esperado IndexOutOfBoundsException para lista vazia.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passou: IndexOutOfBoundsException lançada para lista vazia.");
        }
    }
}
