/*
 * @test
 * @summary Testa o construtor de DeflaterInputStream com entrada nula.
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInput();
    }

    public static void testConstructorWithNullInput() {
        try {
            new DeflaterInputStream(null);
            System.err.println("Falha: Nenhuma exceção lançada ao passar entrada nula.");
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException lançada como esperado.");
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada: " + e);
        }
    }
}
