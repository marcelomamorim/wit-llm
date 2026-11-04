/*
 * @test
 * @summary Testa o método write(int b) na classe java.nio.channels.Channels
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteMethod();
    }

    private static void testWriteMethod() throws IOException {
        // Cria um canal de saída fictício usando um canal de byte de memória
        WritableByteChannel channel = Channels.newChannel(System.out);
        // Testa o método write(int b) com um valor válido
        try {
            Channels.write(65); // ASCII para 'A'
        } catch (Exception e) {
            throw new AssertionError("write(65) falhou", e);
        }
        // Testa o método write(int b) com um valor negativo
        try {
            Channels.write(-1);
        } catch (Exception e) {
            throw new AssertionError("write(-1) falhou", e);
        }
        // Testa o método write(int b) com um valor maior que 255
        try {
            Channels.write(300);
        } catch (Exception e) {
            throw new AssertionError("write(300) falhou", e);
        }
    }
}
