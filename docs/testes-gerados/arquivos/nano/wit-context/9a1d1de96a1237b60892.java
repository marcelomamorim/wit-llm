/*
 * @test
 * @summary Testa o método replace de java.util.Properties para entradas nulas.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullValue();
        testReplaceWithNonNulls();
    }

    private static void testReplaceWithNullKey() {
        Properties props = new Properties();
        Object result = null;
        try {
            result = props.replace(null, "value");
            // Espera-se que lance NullPointerException
            System.out.println("testReplaceWithNullKey: Falhou - nenhuma exceção lançada");
        } catch (NullPointerException e) {
            System.out.println("testReplaceWithNullKey: Passou");
        } catch (Exception e) {
            System.out.println("testReplaceWithNullKey: Falhou - exceção inesperada: " + e);
        }
    }

    private static void testReplaceWithNullValue() {
        Properties props = new Properties();
        Object result = null;
        try {
            result = props.replace("key", null);
            // Espera-se que lance NullPointerException
            System.out.println("testReplaceWithNullValue: Falhou - nenhuma exceção lançada");
        } catch (NullPointerException e) {
            System.out.println("testReplaceWithNullValue: Passou");
        } catch (Exception e) {
            System.out.println("testReplaceWithNullValue: Falhou - exceção inesperada: " + e);
        }
    }

    private static void testReplaceWithNonNulls() {
        Properties props = new Properties();
        props.setProperty("existingKey", "oldValue");
        Object result = null;
        try {
            result = props.replace("existingKey", "newValue");
            if ("oldValue".equals(result)) {
                System.out.println("testReplaceWithNonNulls: Passou");
            } else {
                System.out.println("testReplaceWithNonNulls: Falhou - valor retornado incorreto: " + result);
            }
        } catch (Exception e) {
            System.out.println("testReplaceWithNonNulls: Falhou - exceção inesperada: " + e);
        }
    }
}
