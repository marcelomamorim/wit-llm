/* @test
   @summary Testa o método getName de X500Principal para diferentes cenários.
   @run main X500PrincipalWitupTest_P219929c2bfef
 */
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest_P219929c2bfef {
    public static void main(String[] args) {
        testGetNameWithNullOidMap();
        testGetNameWithNullFormat();
        testGetNameWithRFC1779Format();
        testGetNameWithRFC2253Format();
        testGetNameWithInvalidFormat();
    }

    private static void testGetNameWithNullOidMap() {
        X500Principal principal = createSamplePrincipal();
        try {
            principal.getName("RFC1779", null);
            throw new AssertionError("Expected NullPointerException for null oidMap");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testGetNameWithNullFormat() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        String result = principal.getName(null, oidMap);
        if (result == null) {
            throw new AssertionError("Result should not be null when format is null");
        }
    }

    private static void testGetNameWithRFC1779Format() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        String name = principal.getName("RFC1779", oidMap);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("RFC1779 name should be non-empty");
        }
    }

    private static void testGetNameWithRFC2253Format() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        String name = principal.getName("RFC2253", oidMap);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("RFC2253 name should be non-empty");
        }
    }

    private static void testGetNameWithInvalidFormat() {
        X500Principal principal = createSamplePrincipal();
        Map<String, String> oidMap = new HashMap<>();
        try {
            principal.getName("InvalidFormat", oidMap);
            throw new AssertionError("Expected IllegalArgumentException for invalid format");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static X500Principal createSamplePrincipal() {
        // Cria um X500Principal de exemplo usando um nome padrão válido.
        // Como o construtor público aceita uma String, podemos usar uma string de exemplo.
        return new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
    }
}
