/*
 * @test
 * @summary Verifies that X500Principal.getName() delegates to the RFC 2253 format
 * @run main X500PrincipalWitupTest
 */
import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {
    public static void main(String[] args) {
        X500Principal p = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        String viaDefault = p.getName();
        String viaRfc2253 = p.getName(X500Principal.RFC2253);
        if (!viaDefault.equals(viaRfc2253)) {
            throw new RuntimeException("getName() differs from getName(RFC2253): " + viaDefault + " vs " + viaRfc2253);
        }
        String expected = "CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US";
        if (!expected.equals(viaDefault)) {
            throw new RuntimeException("Unexpected RFC 2253 name: " + viaDefault);
        }
    }
}
