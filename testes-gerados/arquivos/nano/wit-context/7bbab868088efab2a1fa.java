/*
 * @test
 * @summary Testa o método getDouble de UnsafeQualifiedCharacterFieldAccessorImpl para verificar comportamento com entrada inválida.
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
    }

    public static void testGetDoubleWithInvalidObject() throws Exception {
        // Cria uma instância de Field fictícia para o teste, usando reflexão
        // Como não há construtor público, usamos reflexão para obter um campo real da classe alvo
        Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl");
        Field field = null;
        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().equals("field")) {
                f.setAccessible(true);
                field = f;
                break;
            }
        }
        if (field == null) {
            throw new RuntimeException("Campo 'field' não encontrado na classe alvo.");
        }
        // Cria uma instância de UnsafeQualifiedCharacterFieldAccessorImpl
        Object accessor = clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(field, false);
        // Cria um objeto que não é compatível com o campo
        Object invalidObj = new Object();
        try {
            // Invoca getDouble com objeto inválido
            clazz.getMethod("getDouble", Object.class).invoke(accessor, invalidObj);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Esperado IllegalArgumentException não foi lançada.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Exceção inesperada: " + cause, cause);
            }
            // Teste passou ao lançar IllegalArgumentException
        }
    }
}
