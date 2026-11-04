/* @test    @summary Test for sun.security.x509.X500Name.emit(DerOutputStream out)    @run main X500NameWitupTest */

import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class X500NameWitupTest {

    public static void main(String[] args) throws IOException {
        testEmitNormal();
        testEmitOutOfMemoryError();
    }

    private static void testEmitNormal() throws IOException {
        // Cria um objeto X500Name simples
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Organization, C=US");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        // Invoca o método emit
        name.emit(dos);
        // Verifica se algum dado foi escrito
        if (baos.size() > 0) {
            System.out.println("testEmitNormal: Passed");
        } else {
            System.out.println("testEmitNormal: Failed - no output");
        }
    }

    private static void testEmitOutOfMemoryError() {
        // Cria um objeto X500Name com um nome grande para tentar causar OutOfMemoryError
        StringBuilder largeNameBuilder = new StringBuilder();
        largeNameBuilder.append("CN=Test");
        for (int i = 0; i < 1000000; i++) {
            largeNameBuilder.append(", O=Org");
        }
        String largeName = largeNameBuilder.toString();
        X500Name name = new X500Name(largeName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        try {
            name.emit(dos);
            System.out.println("testEmitOutOfMemoryError: Failed - no exception");
        } catch (OutOfMemoryError e) {
            System.out.println("testEmitOutOfMemoryError: Passed");
        } catch (Throwable t) {
            System.out.println("testEmitOutOfMemoryError: Failed - wrong exception: " + t);
        }
    }
}
