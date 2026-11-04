/*
 * @test
 * @summary Test for java.lang.module.ModuleDescriptor.opens(String)
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testOpensWithValidPackageName();
        testOpensWithNullPackageName();
    }

    private static void testOpensWithValidPackageName() {
        ModuleDescriptor moduleDescriptor = new ModuleDescriptor();
        try {
            // Invoca o método com um nome de pacote válido
            ModuleDescriptor.Builder builder = moduleDescriptor.new Builder();
            builder.opens("com.example");
        } catch (Exception e) {
            throw new RuntimeException("Falha ao testar opens com nome de pacote válido", e);
        }
    }

    private static void testOpensWithNullPackageName() {
        ModuleDescriptor moduleDescriptor = new ModuleDescriptor();
        try {
            // Invoca o método com null, esperando uma IllegalArgumentException
            ModuleDescriptor.Builder builder = moduleDescriptor.new Builder();
            builder.opens(null);
            throw new AssertionError("Esperado IllegalArgumentException ao passar null como nome de pacote");
        } catch (IllegalArgumentException e) {
            // Teste passou
        } catch (Exception e) {
            throw new RuntimeException("Falha ao testar opens com null", e);
        }
    }
}
