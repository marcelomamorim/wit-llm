/* @test    @summary Test for Properties.forEach with null BiConsumer    @run main PropertiesWitupTest */

import java.util.Properties;
import java.util.function.BiConsumer;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
    }

    public static void testForEachWithNullAction() {
        Properties props = new Properties();
        // Adiciona uma entrada para garantir que map não esteja vazio
        props.put("key", "value");
        try {
            props.forEach(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Teste bem-sucedido: exceção esperada
        }
    }
}
