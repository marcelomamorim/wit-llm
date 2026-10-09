/* @test
   @summary Testa o método getUTCTime para verificar comportamento esperado e exceções.
   @run main DerInputBufferWitupTest_P8c353c912350
 */
import java.io.IOException;
import java.util.Date;
import sun.security.util.DerInputBuffer;
public class DerInputBufferWitupTest_P8c353c912350 {
    public static void main(String[] args) throws Exception {
        testGetUTCTimeNormal();
        testGetUTCTimeShortRead();
        testGetUTCTimeInvalidLength();
    }
    private static void testGetUTCTimeNormal() throws Exception {
        byte[] data = new byte[20];
        DerInputBuffer dib = new DerInputBuffer(data);
        int len = 15;
        try {
            Date result = new DerInputBuffer(data).getUTCTime(len);
            if (result == null) {
                throw new AssertionError("Expected non-null Date");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }
    private static void testGetUTCTimeShortRead() {
        byte[] data = new byte[10];
        DerInputBuffer dib = new DerInputBuffer(data);
        try {
            dib.getUTCTime(20);
            throw new AssertionError("Expected IOException for short read");
        } catch (IOException e) {
            if (!e.getMessage().contains("short read of DER UTC Time")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
    private static void testGetUTCTimeInvalidLength() {
        byte[] data = new byte[20];
        DerInputBuffer dib = new DerInputBuffer(data);
        try {
            dib.getUTCTime(10);
            throw new AssertionError("Expected IOException for invalid length (<11)");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER UTC Time length error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            dib.getUTCTime(18);
            throw new AssertionError("Expected IOException for invalid length (>17)");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER UTC Time length error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
