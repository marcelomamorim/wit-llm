/* @test    @summary Testa o método privilegedCallable de Executors para comportamento padrão e exceções    @run main ExecutorsWitupTest_Peb99fd6c0bc7 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_Peb99fd6c0bc7 {

    public static void main(String[] args) {
        testPrivilegedCallableWithNull();
        testPrivilegedCallableWithValidCallable();
    }

    private static void testPrivilegedCallableWithNull() {
        try {
            Executors.privilegedCallable(null);
            System.err.println("Falha: esperado NullPointerException ao passar null");
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException lançado como esperado");
        } catch (Throwable t) {
            System.err.println("Falha: exceção inesperada: " + t);
        }
    }

    private static void testPrivilegedCallableWithValidCallable() {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "teste";
            }
        };
        try {
            Callable<String> result = Executors.privilegedCallable(callable);
            if (result != null) {
                System.out.println("Sucesso: Callable retornado não nulo");
            } else {
                System.err.println("Falha: Callable retornado é nulo");
            }
        } catch (Throwable t) {
            System.err.println("Falha: exceção inesperada ao passar Callable válido: " + t);
        }
    }
}
