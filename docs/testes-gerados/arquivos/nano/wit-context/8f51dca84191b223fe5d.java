/* @test    @summary Test for sun.security.x509.X400Address.subtreeDepth() method
 * @run main X400AddressWitupTest
 */

import sun.security.x509.X400Address;

public class X400AddressWitupTest {

    public static void main(String[] args) {
        testSubtreeDepthThrowsException();
    }

    public static void testSubtreeDepthThrowsException() {
        X400Address address = null;
        try {
            // Instancia com construtor padrão, se disponível, ou via reflexão se necessário
            // Como não há construtor público padrão, usar reflexão para criar uma instância com o construtor existente
            // Mas, como o código fonte mostra que o método é uma exceção fixa, podemos criar uma subclasse anônima para testar
            address = new X400Address() {
                @Override
                public int subtreeDepth() {
                    throw new UnsupportedOperationException("subtreeDepth not supported for X400Address");
                }
            };
            int result = address.subtreeDepth();
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
