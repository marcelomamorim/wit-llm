/*
 * @test
 * @summary Verifies that BasicImageReader.getAttributes rejects out-of-range offsets with IndexOutOfBoundsException
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    static class Reader extends BasicImageReader {
        Reader(Path path) throws IOException {
            super(path);
        }
    }

    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        if (!Files.exists(modules)) {
            System.out.println("lib/modules not found; skipping");
            return;
        }

        try (Reader reader = new Reader(modules)) {
            expectIndexOutOfBounds(reader, -1);
            expectIndexOutOfBounds(reader, Integer.MIN_VALUE);
            expectIndexOutOfBounds(reader, Integer.MAX_VALUE);
        }
    }

    static void expectIndexOutOfBounds(BasicImageReader reader, int offset) {
        try {
            reader.getAttributes(offset);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("No IndexOutOfBoundsException for offset " + offset);
    }
}
