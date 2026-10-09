/* @test    @summary Test getName with valid formats and null map    @run main X500PrincipalWitupTest_Pcb0ed81a198b */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest_Pcb0ed81a198b {

    public static void main(String[] args) {
        // Instantiate X500Principal with a distinguished name string
        X500Principal principal = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");

        // Prepare a dummy OID map
        Map<String, String> oidMap = new HashMap<>();
        oidMap.put("2.5.4.3", "CN");
        oidMap.put("2.5.4.11", "OU");
        oidMap.put("2.5.4.10", "O");
        oidMap.put("2.5.4.6", "C");

        // Test getName with RFC1779 format
        String nameRFC1779 = principal.getName("RFC1779", oidMap);
        System.out.println("RFC1779: " + nameRFC1779);

        // Test getName with RFC2253 format
        String nameRFC2253 = principal.getName("RFC2253", oidMap);
        System.out.println("RFC2253: " + nameRFC2253);

        // Test getName with null format (should throw IllegalArgumentException)
        try {
            principal.getName(null, oidMap);
            System.out.println("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for null format");
        }

        // Test getName with invalid format
        try {
            principal.getName("InvalidFormat", oidMap);
            System.out.println("Expected IllegalArgumentException not thrown for invalid format");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for invalid format");
        }

        // Test getName with null oidMap (should throw NullPointerException)
        try {
            principal.getName("RFC1779", null);
            System.out.println("Expected NullPointerException not thrown for null oidMap");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null oidMap");
        }
    }
}
