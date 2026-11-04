/*
 * @test
 * @summary Testa o método read() da classe java.nio.channels.Channels.
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    public static void main(String[] args) throws IOException {
        testReadReturnsByteWhenDataAvailable();
        testReadReturnsMinusOneWhenNoData();
    }

    private static void testReadReturnsByteWhenDataAvailable() throws IOException {
        // Cria um InputStream com um byte fixo
        byte[] data = {0x7F};
        java.io.InputStream is = new java.io.ByteArrayInputStream(data);
        java.nio.channels.ReadableByteChannel channel = Channels.newChannel(is);
        // Invoca o método read()
        int result = Channels.read(channel);
        // Verifica se o resultado é o byte esperado
        if (result != 0x7F) {
            throw new AssertionError("Expected 0x7F, got " + result);
        }
    }

    private static void testReadReturnsMinusOneWhenNoData() throws IOException {
        // Cria um InputStream vazio
        java.io.InputStream is = new java.io.ByteArrayInputStream(new byte[0]);
        java.nio.channels.ReadableByteChannel channel = Channels.newChannel(is);
        // Invoca o método read()
        int result = Channels.read(channel);
        // Verifica se o resultado é -1
        if (result != -1) {
            throw new AssertionError("Expected -1, got " + result);
        }
    }
}
