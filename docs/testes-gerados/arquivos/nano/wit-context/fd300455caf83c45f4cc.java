/*
 * @test
 * @summary Test for UnsafeByteFieldAccessorImpl.getBoolean method behavior
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanAlwaysThrows();
    }

    public static void testGetBooleanAlwaysThrows() {
        class Dummy {
        }
        try {
            // Obtém o campo de uma classe dummy para passar como argumento
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            // Cria uma instância de UnsafeByteFieldAccessorImpl com o campo dummy
            jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(dummyField);
            // Invoca getBoolean, que deve lançar IllegalArgumentException
            accessor.getBoolean(new Dummy());
            // Se não lançar, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Esperado: o método deve lançar IllegalArgumentException
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Dummy class does not have the expected field", e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
