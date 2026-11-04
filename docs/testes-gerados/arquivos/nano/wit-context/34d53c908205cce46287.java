/*
 * @test
 * @summary Teste do método selectedKeys() de sun.nio.ch.SelectorImpl
 * @run main SelectorImplWitupTest
 */

import sun.nio.ch.SelectorImpl;
import java.nio.channels.SelectionKey;
import java.util.Set;

public class SelectorImplWitupTest {

    public static void main(String[] args) {
        testSelectedKeysReturnsOpenSelector();
        testSelectedKeysThrowsWhenClosed();
    }

    private static void testSelectedKeysReturnsOpenSelector() {
        SelectorProvider provider = SelectorProvider.provider();
        SelectorImpl selector = null;
        try {
            selector = new SelectorImpl(provider);
            Set<SelectionKey> keys = selector.selectedKeys();
            if (keys == null) {
                throw new AssertionError("selectedKeys() retornou null");
            }
            if (!keys.isEmpty()) {
                System.out.println("Aviso: selectedKeys() retornou conjunto não vazio inicialmente.");
            }
            System.out.println("Teste de retorno de selectedKeys() em seletor aberto passou.");
        } catch (Exception e) {
            throw new AssertionError("Erro inesperado ao testar selectedKeys() em seletor aberto", e);
        } finally {
            if (selector != null) {
                try {
                    selector.close();
                } catch (Exception e) {
                    // Ignorar
                }
            }
        }
    }

    private static void testSelectedKeysThrowsWhenClosed() {
        SelectorProvider provider = SelectorProvider.provider();
        SelectorImpl selector = null;
        try {
            selector = new SelectorImpl(provider);
            selector.close();
            try {
                selector.selectedKeys();
                throw new AssertionError("Esperado ClosedSelectorException ao chamar selectedKeys() após fechar");
            } catch (java.nio.channels.ClosedSelectorException e) {
                System.out.println("Exceção esperada capturada ao chamar selectedKeys() após fechar.");
            }
        } catch (Exception e) {
            throw new AssertionError("Erro inesperado ao testar selectedKeys() após fechar", e);
        } finally {
            if (selector != null && selector.isOpen()) {
                try {
                    selector.close();
                } catch (Exception e) {
                    // Ignorar
                }
            }
        }
    }
}
