/*
 * @test
 * @summary Test for UnsafeDoubleFieldAccessorImpl.getDouble(Object)
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
        testGetDoubleWithValidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        Field dummyField = null;
        // Cria uma instância de UnsafeDoubleFieldAccessorImpl com um campo fictício
        // Como o construtor é package-private, usar reflexão para acessá-lo
        // Mas, como não há construtor público, podemos criar uma subclasse anônima para testar
        // ou usar reflexão para criar uma instância, porém, para simplicidade, vamos usar reflexão
        // para obter uma instância válida do método, simulando uma situação inválida
        // Aqui, como não podemos criar uma instância válida facilmente, vamos testar o método com null
        UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(dummyField);
        try {
            accessor.getDouble(null);
            System.out.println("Erro: esperado IllegalArgumentException ao passar null");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: IllegalArgumentException ao passar null");
        }
    }

    private static void testGetDoubleWithValidObject() throws Exception {
        class Dummy {
            public double value = 42.0;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(field);
        double result = accessor.getDouble(dummy);
        if (result == 42.0) {
            System.out.println("Passou: getDouble retornou o valor esperado");
        } else {
            System.out.println("Erro: getDouble retornou " + result);
        }
    }
}
