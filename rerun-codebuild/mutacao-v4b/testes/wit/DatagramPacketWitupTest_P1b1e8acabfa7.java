/* @test
   @summary Testa o método setPort de DatagramPacket para entradas válidas e inválidas.
   @run main DatagramPacketWitupTest_P1b1e8acabfa7
 */
import java.net.DatagramPacket;
public class DatagramPacketWitupTest_P1b1e8acabfa7 {
    public static void main(String[] args) {
        testSetPortValid();
        testSetPortInvalidNegative();
        testSetPortInvalidTooLarge();
    }
    private static void testSetPortValid() {
        try {
            byte[] buf = new byte[10];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            packet.setPort(0);
            packet.setPort(65535);
        } catch (Exception e) {
            throw new AssertionError("Valid port values should not throw", e);
        }
    }
    private static void testSetPortInvalidNegative() {
        try {
            byte[] buf = new byte[10];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            packet.setPort(-1);
            throw new AssertionError("Expected IllegalArgumentException for port -1");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for port -1", e);
        }
    }
    private static void testSetPortInvalidTooLarge() {
        try {
            byte[] buf = new byte[10];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            packet.setPort(65536);
            throw new AssertionError("Expected IllegalArgumentException for port 65536");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for port 65536", e);
        }
    }
}
