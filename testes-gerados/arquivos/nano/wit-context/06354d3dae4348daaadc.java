/*
 * @test
 * @summary Tests the read(ByteBuffer) method of sun.nio.ch.DatagramChannelImpl.
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.ProtocolFamily;
import java.nio.ByteBuffer;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testReadWithNullBuffer();
        testReadWithValidBuffer();
    }

    private static void testReadWithNullBuffer() {
        try {
            // Cria uma instância de DatagramChannelImpl com um SelectorProvider padrão
            // Como o construtor requer um SelectorProvider, usamos uma implementação padrão
            // que pode ser obtida via SelectorProvider.provider()
            DatagramChannelImpl channel = new DatagramChannelImpl(java.nio.channels.spi.SelectorProvider.provider());
            try {
                channel.read(null);
                System.err.println("Erro: Esperado NullPointerException ao passar null como ByteBuffer.");
            } catch (NullPointerException expected) {
                System.out.println("Passou no teste de NullPointerException ao passar null.");
            } catch (IOException e) {
                System.err.println("Erro inesperado: " + e);
            }
        } catch (IOException e) {
            System.err.println("Falha na criação do DatagramChannelImpl: " + e);
        }
    }

    private static void testReadWithValidBuffer() {
        try {
            DatagramChannelImpl channel = new DatagramChannelImpl(java.nio.channels.spi.SelectorProvider.provider());
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
                int result = channel.read(buffer);
                System.out.println("Leitura bem-sucedida, bytes lidos: " + result);
            } catch (UnsupportedOperationException e) {
                System.out.println("Leitura não suportada neste contexto, como esperado.");
            } catch (IOException e) {
                System.err.println("Erro de IO durante leitura: " + e);
            }
        } catch (IOException e) {
            System.err.println("Falha na criação do DatagramChannelImpl: " + e);
        }
    }
}
