package software.ulpgc.architecture.io;

import software.ulpgc.architecture.model.Dinosaur;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipDinosaurReader implements DinosaurReader{
    private final BufferedReader reader;
    private final DinosaurDeserializer deserializer;

    public ZipDinosaurReader(File file, DinosaurDeserializer deserializer) throws IOException {
        this.reader = readerOf(file);
        this.deserializer = deserializer;
        skipHeader();
    }

    private void skipHeader() throws IOException {
        reader.readLine();
    }

    private static BufferedReader readerOf(File file) throws IOException {
        return new BufferedReader(new InputStreamReader(getZipInputOf(file)));
    }

    private static ZipInputStream getZipInputOf(File file) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
        ZipEntry entry = zipInputStream.getNextEntry();
        return zipInputStream;
    }


    @Override
    public Dinosaur read() throws IOException {
        return deserialize(reader.readLine());
    }

    private Dinosaur deserialize(String line) {
        if(line==null)return null;
        return deserializer.deserialize(line);
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
