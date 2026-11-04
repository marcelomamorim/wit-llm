/*
 * @test
 * @summary Test for UnsafeIntegerFieldAccessorImpl.getFloat method exception path
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatWithInvalidObject();
    }

    private static void testGetFloatWithInvalidObject() {
        try {
            // Cria um campo fictício para passar como argumento, não é necessário instanciar uma classe real
            Field dummyField = null;
            // Invoca o método com um objeto que não é compatível com o campo esperado
            jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(dummyField);
            Object invalidObj = new String("invalid");
            accessor.getFloat(invalidObj);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
