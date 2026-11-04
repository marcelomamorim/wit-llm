/*
 * @test
 * @summary RDN(AVA) must reject a null AVA with NullPointerException
 * @modules java.base/sun.security.x509
 * @run main RDNWitupTest
 */

public class RDNWitupTest {
    public static void main(String[] args) {
        try {
            new RDN((AVA) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }
}
