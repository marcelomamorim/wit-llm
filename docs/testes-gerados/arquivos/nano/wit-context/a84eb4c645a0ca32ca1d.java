/*
 * @test
 * @summary Testa o método Executors.callable(final PrivilegedAction<?> action) para verificar comportamento com entrada nula.
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.security.PrivilegedAction;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testCallableWithNullAction();
    }

    private static void testCallableWithNullAction() {
        try {
            Callable<?> c = Executors.callable((PrivilegedAction<?>) null);
            // Se não lançar exceção, o teste falha
            System.err.println("Falha: esperado NullPointerException ao passar action null.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Esperado
            System.out.println("Sucesso: NullPointerException lançada como esperado.");
        } catch (Exception e) {
            System.err.println("Falha: exceção inesperada: " + e);
            System.exit(1);
        }
    }
}
