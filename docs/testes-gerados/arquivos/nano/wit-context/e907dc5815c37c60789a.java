/* @test    @summary Teste do método getFloat para verificar comportamento esperado e exceções.    @run main UnsafeStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatWithNullObject();
        testGetFloatWithInvalidType();
    }

    private static void testGetFloatWithNullObject() {
        try {
            // Cria uma instância de UnsafeStaticObjectFieldAccessorImpl com um campo válido
            Field field = DummyClass.class.getDeclaredField("floatField");
            UnsafeStaticObjectFieldAccessorImpl accessor = new UnsafeStaticObjectFieldAccessorImpl(field);
            // Invoca get com objeto nulo, espera-se que lance IllegalArgumentException
            accessor.get(null);
            System.err.println("Falha: Esperado IllegalArgumentException ao passar null.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: Exceção esperada ao passar null.");
        } catch (Throwable t) {
            System.err.println("Falha: Exceção inesperada: " + t);
        }
    }

    private static void testGetFloatWithInvalidType() {
        try {
            // Cria uma instância de UnsafeStaticObjectFieldAccessorImpl com um campo válido
            Field field = DummyClass.class.getDeclaredField("floatField");
            UnsafeStaticObjectFieldAccessorImpl accessor = new UnsafeStaticObjectFieldAccessorImpl(field);
            // Invoca get com objeto de tipo incorreto, espera-se que lance IllegalArgumentException
            accessor.get(new Object());
            System.err.println("Falha: Esperado IllegalArgumentException ao passar objeto de tipo incorreto.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: Exceção esperada ao passar objeto de tipo incorreto.");
        } catch (Throwable t) {
            System.err.println("Falha: Exceção inesperada: " + t);
        }
    }

    // Classe dummy para testes
    static class DummyClass {
        static float floatField = 1.23f;
    }
}
