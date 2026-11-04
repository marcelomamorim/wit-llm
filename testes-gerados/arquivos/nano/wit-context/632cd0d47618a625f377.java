/*
 * @test
 * @summary Test for getInt method in UnsafeQualifiedStaticFloatFieldAccessorImpl
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    public static void testGetIntThrowsException() {
        try {
            // Cria uma instância fictícia da classe, usando reflexão para acessar o construtor se necessário
            // Como não há construtor público, não podemos instanciá-la diretamente, então apenas chamamos o método
            // que é estático na classe, simulando o comportamento esperado.
            // Como o método getInt lança exceção imediatamente, podemos invocá-lo diretamente.
            jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl instance = null;
            // Como não há construtor público, não instanciamos, apenas chamamos o método estático.
            // Mas o método não é estático, então criamos uma subclasse anônima para invocar.
            // Como alternativa, podemos usar reflexão para invocar o método.
            java.lang.reflect.Method method = jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl.class.getDeclaredMethod("getInt", Object.class);
            method.setAccessible(true);
            Object result = method.invoke(instance, new Object());
            // Esperamos que uma exceção seja lançada, então se chegar aqui, o teste falhou.
            System.err.println("Erro: getInt não lançou exceção como esperado.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passou: getInt lançou IllegalArgumentException como esperado.");
            } else {
                System.err.println("Falha: getInt lançou uma exceção inesperada: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Erro ao invocar getInt: " + e);
        }
    }
}
