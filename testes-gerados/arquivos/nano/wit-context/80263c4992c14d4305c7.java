/*
 * @test
 * @summary Teste do método unread(byte[] b) de PushbackInputStream para verificar comportamento com entradas válidas e inválidas.
 * @run main PushbackInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testUnreadWithValidInput();
        testUnreadWithNullInput();
    }

    private static void testUnreadWithValidInput() {
        byte[] data = {1, 2, 3};
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            PushbackInputStream pbis = new PushbackInputStream(bais, 10);
            byte[] toUnread = {4, 5};
            pbis.unread(toUnread);
            int firstByte = pbis.read();
            int secondByte = pbis.read();
            int thirdByte = pbis.read();
            int fourthByte = pbis.read();
            assert firstByte == 4 : "First byte should be 4";
            assert secondByte == 5 : "Second byte should be 5";
            assert thirdByte == 1 : "Third byte should be 1";
            assert fourthByte == 2 : "Fourth byte should be 2";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testUnreadWithValidInput", e);
        }
    }

    private static void testUnreadWithNullInput() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1, 2, 3});
        PushbackInputStream pbis = new PushbackInputStream(bais, 10);
        try {
            pbis.unread((byte[]) null);
            throw new AssertionError("Expected IOException when unread(null)");
        } catch (IOException e) {
            // Expected exception
            assert e.getMessage() != null : "Exception message should not be null";
        }
    }
}
