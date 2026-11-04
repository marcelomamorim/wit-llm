/*
 * @test
 * @summary Testa o construtor PrintStream(String fileName) com entrada inválida.
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.FileNotFoundException;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullFileName();
    }

    public static void testConstructorWithNullFileName() {
        try {
            new PrintStream((String) null);
            System.err.println("Falha: não lançou NullPointerException ao passar null como fileName");
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException lançado como esperado");
        } catch (FileNotFoundException e) {
            System.err.println("Erro: FileNotFoundException inesperada ao passar null");
        } catch (Exception e) {
            System.err.println("Erro: exceção inesperada: " + e);
        }
    }
}
