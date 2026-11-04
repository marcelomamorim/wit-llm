/* @test    @summary Test for UnsafeObjectFieldAccessorImpl.get method handling invalid object types    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetWithInvalidObject();
    }

    public static void testGetWithInvalidObject() {
        try {
            // Cria um campo fictício para passar ao construtor
            Field dummyField = null;
            // Instancia a classe com um campo fictício (não usado na chamada)
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            // Chama o método get com um objeto de tipo inválido
            Object result = accessor.get("string_instead_of_expected_type");
            // Se não lançar exceção, o teste falha
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Esperado: a exceção deve ser lançada
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            // Qualquer outra exceção indica falha
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
