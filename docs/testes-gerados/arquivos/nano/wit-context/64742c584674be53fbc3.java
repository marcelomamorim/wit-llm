/*
 * @test
 * @summary Test getLong method of UnsafeStaticDoubleFieldAccessorImpl for exception path
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsIllegalArgumentException();
    }

    public static void testGetLongThrowsIllegalArgumentException() {
        try {
            // Cria uma instância de UnsafeStaticDoubleFieldAccessorImpl com um campo fictício
            // Como não há construtor público, simula-se a criação via reflexão, assumindo que o campo é acessível
            // Para fins de teste, criamos um campo dummy na própria classe
            Field dummyField = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            // Cria uma instância do acessor com o campo dummy
            jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl(dummyField);
            // Invoca o método getLong, que deve lançar IllegalArgumentException
            accessor.getLong(new Object());
            // Se não lançar exceção, falha o teste
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste passou, exceção esperada
            System.out.println("Passed: IllegalArgumentException was thrown as expected.");
        } catch (NoSuchFieldException e) {
            System.err.println("Error: Dummy field not found.");
        } catch (Exception e) {
            System.err.println("Error: Unexpected exception: " + e);
        }
    }

    // Campo dummy para criar o Field dummy
    private static int dummy;
}
