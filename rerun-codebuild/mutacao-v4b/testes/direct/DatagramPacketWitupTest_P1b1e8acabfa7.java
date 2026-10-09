/* @test
   @summary Teste do método setPort() da classe java.net.DatagramPacket.
   @run main DatagramPacketWitupTest_P1b1e8acabfa7
 */
import java.net.DatagramPacket;
public class DatagramPacketWitupTest_P1b1e8acabfa7 {
    public static void main(String[] args) {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        // Testa valor válido de porta
        packet.setPort(8080);
        if (packet.port != 8080) {
            throw new AssertionError("Port should be 8080");
        }
        // Testa valor mínimo válido
        packet.setPort(0);
        if (packet.port != 0) {
            throw new AssertionError("Port should be 0");
        }
        // Testa valor máximo válido
        packet.setPort(65535);
        if (packet.port != 65535) {
            throw new AssertionError("Port should be 65535");
        }
        // Testa valor negativo (deve lançar IllegalArgumentException)
        try {
            packet.setPort(-1);
            throw new AssertionError("Expected IllegalArgumentException for port -1");
        } catch (IllegalArgumentException e) {
            // esperado
        }
        // Testa valor acima do limite (deve lançar IllegalArgumentException)
        try {
            packet.setPort(65536);
            throw new AssertionError("Expected IllegalArgumentException for port 65536");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
}
