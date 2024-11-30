package software.ulpgc.architecture.io;

import software.ulpgc.architecture.model.Dinosaur;

import java.io.IOException;

public interface DinosaurWriter extends AutoCloseable{
    void write(Dinosaur dinosaur) throws IOException;
}
