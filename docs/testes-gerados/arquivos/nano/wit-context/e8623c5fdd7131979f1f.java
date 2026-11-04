/* @test    @summary Test for UnsafeByteFieldAccessorImpl.getByte method handling invalid object types    @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteWithInvalidObject();
    }

    public static void testGetByteWithInvalidObject() throws Exception {
        // Cria um campo fictício para passar ao construtor, não será usado na chamada real
        Field dummyField = null;
        // Instancia a classe alvo com o campo fictício
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(dummyField);
        Object invalidObject = new String("invalid");
        try {
            accessor.getByte(invalidObject);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Exceção esperada, teste passa
        }
    }
}
