/* @test    @summary Test for setAuthenticator method in HttpURLConnection to verify exception throwing behavior    @run main HttpURLConnectionWitupTest */

import java.net.HttpURLConnection;
import java.net.Authenticator;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetAuthenticatorThrowsException();
    }

    public static void testSetAuthenticatorThrowsException() {
        HttpURLConnection connection = null;
        try {
            // Instancia uma subclasse de HttpURLConnection, pois o construtor é protegido
            connection = new HttpURLConnection(null) {
                @Override
                public void connect() throws IOException {
                    // método vazio
                }
            };
            // Chamada que deve lançar UnsupportedOperationException
            connection.setAuthenticator(null);
            // Se não lançar, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Teste passou, exceção esperada
            System.out.println("setAuthenticator() threw UnsupportedOperationException as expected.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
