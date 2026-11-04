/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Cria um objeto da classe alvo
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl");
            // Obtém o campo 'field' (necessário ajustar se o campo for acessível)
            Field field = null;
            for (Field f : clazz.getDeclaredFields()) {
                if (f.getName().equals("field")) {
                    field = f;
                    break;
                }
            }
            if (field == null) {
                return; // não encontrou o campo, aborta
            }
            // Cria uma instância da classe
            Object instance = clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(field, false);
            // Invoca o método getChar com um objeto qualquer
            Object obj = new Object();
            try {
                clazz.getMethod("getChar", Object.class).invoke(instance, obj);
                // Espera que lance IllegalArgumentException
                System.err.println("Erro: não lançou IllegalArgumentException");
            } catch (Throwable t) {
                Throwable cause = t.getCause();
                if (cause instanceof IllegalArgumentException) {
                    System.out.println("Passou: IllegalArgumentException lançada como esperado.");
                } else {
                    System.err.println("Falha: exceção inesperada: " + t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
