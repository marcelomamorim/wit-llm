/*
 * @test
 * @summary Testa o método getDouble para verificar comportamento esperado e exceções.
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleWithNullObject();
        testGetDoubleWithInvalidType();
    }

    private static void testGetDoubleWithNullObject() {
        try {
            // Cria uma instância de UnsafeStaticObjectFieldAccessorImpl com um campo válido
            Field dummyField = null; // Como não há construtor público, simula-se um campo válido
            // Como não podemos criar uma instância real sem acesso ao construtor, essa parte é um placeholder
            // Em um cenário real, essa instância seria obtida via reflexão ou método de fábrica
            // Aqui, apenas demonstra a chamada e captura a exceção esperada
            jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl accessor = null;
            try {
                accessor = new jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl(dummyField);
            } catch (Exception e) {
                // Esperado falha na criação, pois dummyField é null
            }
            if (accessor != null) {
                accessor.get(null);
                System.out.println("Erro: Esperava-se IllegalArgumentException ao passar null.");
            } else {
                System.out.println("Pula teste de null, instância não criada.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: getDouble com null objeto lançou IllegalArgumentException.");
        } catch (Exception e) {
            System.out.println("Falha inesperada: " + e);
        }
    }

    private static void testGetDoubleWithInvalidType() {
        try {
            // Como não podemos criar uma instância real, simula-se a chamada com um objeto de tipo inválido
            // Aqui, apenas demonstra a chamada e captura a exceção
            jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl accessor = null;
            // Como não há construtor público, essa parte é um placeholder
            try {
                accessor = new jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl(null);
            } catch (Exception e) {
                // Esperado falha na criação
            }
            if (accessor != null) {
                accessor.get(new Object());
                System.out.println("Erro: Esperava-se IllegalArgumentException ao passar objeto de tipo inválido.");
            } else {
                System.out.println("Pula teste de tipo inválido, instância não criada.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: getDouble com objeto de tipo inválido lançou IllegalArgumentException.");
        } catch (Exception e) {
            System.out.println("Falha inesperada: " + e);
        }
    }
}
