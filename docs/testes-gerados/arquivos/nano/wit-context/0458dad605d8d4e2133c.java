/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedStaticIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsIllegalArgumentException();
    }

    public static void testGetByteThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, pois não há detalhes do campo real
            // Como o método getByte lança IllegalArgumentException sempre, apenas invoca e verifica a exceção
            Object instance = clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(field, false);
            UnsafeQualifiedStaticIntegerFieldAccessorImpl accessor = (UnsafeQualifiedStaticIntegerFieldAccessorImpl) instance;
            accessor.getByte(new Object());
            // Se não lançar exceção, falha o teste
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Esperado
            System.out.println("IllegalArgumentException thrown as expected.");
        } catch (ReflectiveOperationException e) {
            // Problema na reflexão
            e.printStackTrace();
            System.exit(1);
        } catch (Throwable t) {
            // Outros problemas
            t.printStackTrace();
            System.exit(1);
        }
    }
}
