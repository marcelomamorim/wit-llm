/*
 * @test
 * @summary Testa o método addAll de Collections com exceção UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        List<Integer> list = Collections.unmodifiableList(Arrays.asList(1, 2, 3));
        List<Integer> toAdd = Arrays.asList(4, 5);
        try {
            // Tentativa de adicionar elementos a uma lista imutável deve lançar UnsupportedOperationException
            list.addAll(1, toAdd);
            System.err.println("Falha: exceção não lançada ao tentar addAll em lista imutável.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Esperado
            System.out.println("Sucesso: UnsupportedOperationException lançada como esperado.");
        } catch (Exception e) {
            System.err.println("Falha: exceção inesperada: " + e);
            System.exit(1);
        }
    }
}
