/*
 * @test
 * @summary Test for visitModule method in RemappingClassAdapter
 * @run main RemappingClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.commons.RemappingClassAdapter;

public class RemappingClassAdapterWitupTest {

    public static void main(String[] args) {
        testVisitModuleThrowsRuntimeException();
    }

    public static void testVisitModuleThrowsRuntimeException() {
        // Cria um objeto anônimo de ClassVisitor, pois não é necessário comportamento real
        ClassVisitor cv = new ClassVisitor() {
            // Implementação vazia, pois não será chamada neste teste
        };
        // Cria uma instância de RemappingClassAdapter com o construtor protegido acessível via herança
        RemappingClassAdapter adapter = new RemappingClassAdapter(0, cv, null) {
            // Construtor anônimo para acessar o construtor protegido
        };
        try {
            adapter.visitModule("testModule", 0, "1.0");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            // Verifica se a mensagem da exceção é a esperada
            if (!"RemappingClassAdapter is deprecated, use ClassRemapper instead".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // Caso a mensagem seja a esperada, o teste passa
        }
    }
}
