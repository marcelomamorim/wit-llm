/*
 * @test
 * @summary Teste do método Channels.newChannel para entradas nulas.
 * @run main ChannelsWitupTest
 */

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.util.Objects;

public class ChannelsWitupTest {

    public static void main(String[] args) {
        testNewChannelNullOut();
        testNewChannelWithFileOutputStream();
    }

    public static void testNewChannelNullOut() {
        try {
            WritableByteChannel ch = Channels.newChannel(null);
            System.err.println("Erro: esperado NullPointerException não ocorreu.");
        } catch (NullPointerException e) {
            System.out.println("Passou: NullPointerException esperado ao passar null.");
        } catch (Exception e) {
            System.err.println("Erro: exceção inesperada: " + e);
        }
    }

    public static void testNewChannelWithFileOutputStream() {
        try (FileOutputStream fos = new FileOutputStream("temp.txt")) {
            WritableByteChannel ch = Channels.newChannel(fos);
            if (ch != null) {
                System.out.println("Passou: canal criado com FileOutputStream.");
            } else {
                System.err.println("Erro: canal retornado é null.");
            }
        } catch (Exception e) {
            System.err.println("Erro: exceção ao criar canal com FileOutputStream: " + e);
        }
    }
}
