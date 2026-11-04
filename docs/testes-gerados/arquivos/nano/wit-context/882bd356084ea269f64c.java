/* @test    @summary Testa o método getLong de UnsafeBooleanFieldAccessorImpl para verificar comportamento esperado.
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongThrowsException();
    }

    public static void testGetLongThrowsException() throws Exception {
        // Cria uma instância de UnsafeBooleanFieldAccessorImpl com um campo fictício
        // Como o construtor não é público, usamos reflexão para acessá-lo.
        // Como o método é protegido, podemos criar uma subclasse anônima ou usar reflexão.
        // Aqui, optamos por usar reflexão para criar a instância.
        Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl");
        Field field = null; // campo fictício, não usado, apenas para passar na construção
        // Como o construtor não é público, usamos reflexão para acessá-lo.
        java.lang.reflect.Constructor<?> ctor = clazz.getDeclaredConstructor(Field.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance((Field) null); // campo null, pois não usamos
        // Invoca o método getLong, que deve lançar uma exceção
        try {
            java.lang.reflect.Method getLongMethod = clazz.getMethod("getLong", Object.class);
            getLongMethod.invoke(accessor, new Object());
            // Se não lançar exceção, falha o teste
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Exceção esperada, teste passa
        }
    }
}
