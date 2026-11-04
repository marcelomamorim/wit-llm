/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedStaticCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsIllegalArgumentException();
    }

    public static void testGetByteThrowsIllegalArgumentException() {
        try {
            // Cria uma instância fictícia do objeto, já que o método não usa o parâmetro
            Object obj = null;
            // Invoca o método getByte, que deve lançar IllegalArgumentException
            jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl instance = null;
            // Como não há construtor público, usamos reflexão para obter a instância
            Field field = jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl.class.getDeclaredField("field");
            field.setAccessible(true);
            instance = (jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl) field.get(null);
            // Chamada direta ao método
            instance.getByte(obj);
            // Se não lançar exceção, falha o teste
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Esperado
            System.out.println("Passed: IllegalArgumentException was thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
