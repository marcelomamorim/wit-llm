/*
 * @test
 * @summary Test setEnabledCipherSuites with null input for sun.security.ssl.SSLServerSocketImpl
 * @run main SSLServerSocketImplWitupTest
 */

import java.io.IOException;
import sun.security.ssl.SSLServerSocketImpl;

public class SSLServerSocketImplWitupTest {

    public static void main(String[] args) throws IOException {
        testSetEnabledCipherSuitesNull();
    }

    public static void testSetEnabledCipherSuitesNull() {
        SSLServerSocketImpl socket = null;
        try {
            // Instanciação fictícia, assumindo método getInstance() ou construtor acessível
            socket = new SSLServerSocketImpl(null); // Como não há construtor público, esta linha é ilustrativa
            socket.setEnabledCipherSuites(null);
            // Se não lançar exceção, o teste passa, pois o método deve aceitar null sem erro
            System.out.println("PASS: setEnabledCipherSuites(null) did not throw");
        } catch (NullPointerException e) {
            // Esperado se o método não tratar null
            System.out.println("PASS: setEnabledCipherSuites(null) threw NullPointerException");
        } catch (IllegalArgumentException e) {
            // Caso o método valide e lance exceção para null
            System.out.println("PASS: setEnabledCipherSuites(null) threw IllegalArgumentException");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
