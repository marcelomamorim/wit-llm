/*
 * @test
 * @summary Testa o método newChannel de java.nio.channels.Channels para entrada nula.
 * @run main ChannelsWitupTest
 */

import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    public static void main(String[] args) {
        testNewChannelNullInput();
        testNewChannelFileInputStream();
    }

    public static void testNewChannelNullInput() {
        try {
            ReadableByteChannel ch = Channels.newChannel(null);
            System.err.println("Erro: esperado NullPointerException não ocorreu");
        } catch (NullPointerException e) {
            System.out.println("Passou: NullPointerException esperado ao passar null");
        } catch (Exception e) {
            System.err.println("Erro: exceção inesperada: " + e);
        }
    }

    public static void testNewChannelFileInputStream() {
        try {
            InputStream fis = new FileInputStream("/dev/null");
            ReadableByteChannel ch = Channels.newChannel(fis);
            if (ch != null) {
                System.out.println("Passou: canal criado a partir de FileInputStream");
            } else {
                System.err.println("Erro: canal retornado é null");
            }
        } catch (Exception e) {
            System.err.println("Erro: exceção inesperada: " + e);
        }
    }
}
