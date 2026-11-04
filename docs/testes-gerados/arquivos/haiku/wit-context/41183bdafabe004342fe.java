/*
 * @test
 * @summary Verify that SecurityManager.checkAccept rejects a null host with NullPointerException
 * @run main SecurityManagerWitupTest
 */
public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        int[] ports = {-1, 0, 80, 65535};
        for (int port : ports) {
            try {
                sm.checkAccept(null, port);
                throw new RuntimeException("NullPointerException not thrown for port " + port);
            } catch (NullPointerException e) {
                if (!"host can't be null".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
                }
            }
        }
    }
}
