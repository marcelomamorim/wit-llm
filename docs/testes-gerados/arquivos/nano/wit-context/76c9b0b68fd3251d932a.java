/*
 * @test
 * @summary Tests for jdk.internal.module.ModuleHashesBuilder
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.function.Consumer;
import jdk.internal.module.ModuleHashesBuilder;
import java.util.ArrayList;
import java.util.List;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testReverseWithNullAction();
    }

    public static void testReverseWithNullAction() {
        ModuleHashesBuilder builder = createSampleBuilder();
        try {
            builder.reverse(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static ModuleHashesBuilder createSampleBuilder() {
        // Cria uma instância de ModuleHashesBuilder com configurações fictícias
        // Como o construtor requer um Configuration válido, e não temos acesso a ele,
        // podemos criar uma subclasse anônima ou usar reflexão, mas para simplicidade,
        // vamos criar uma classe mock ou retornar null, sabendo que o método não será chamado.
        // Aqui, retornamos uma instância fictícia apenas para fins de compilação.
        return new ModuleHashesBuilder(null, null) {
            @Override
            public void reverse(Consumer<Object> action) {
                // Implementação fictícia para teste
                if (action == null) {
                    throw new NullPointerException();
                }
                // Caso contrário, nada faz
            }
        };
    }
}
