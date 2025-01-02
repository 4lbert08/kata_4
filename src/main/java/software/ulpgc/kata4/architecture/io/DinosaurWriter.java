package software.ulpgc.kata4.architecture.io;

import software.ulpgc.kata4.architecture.model.Dinosaur;

import java.io.IOException;

public interface DinosaurWriter extends AutoCloseable{
    void write(Dinosaur dinosaur) throws IOException;
}
