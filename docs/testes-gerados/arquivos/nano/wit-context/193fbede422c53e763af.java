/*
 * @test
 * @summary Test for getShort method in UnsafeQualifiedStaticLongFieldAccessorImpl
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    public static void testGetShortThrowsException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para instanciar
            boolean isReadOnly = false;
            Object accessor = clazz.getConstructor(Field.class, boolean.class).newInstance(field, isReadOnly);
            // Invoca o método getShort, que deve lançar uma exceção
            clazz.getMethod("getShort", Object.class).invoke(accessor, new Object());
            // Se não lançar exceção, falha o teste
            System.err.println("Erro: esperado uma IllegalArgumentException.");
            System.exit(1);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passou: getShort lançou IllegalArgumentException como esperado.");
                System.exit(0);
            } else {
                System.err.println("Falha: inesperada " + e);
                System.exit(1);
            }
        }
    }
}
