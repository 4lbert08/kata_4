package software.ulpgc.kata4.architecture.io;

import software.ulpgc.kata4.architecture.model.Dinosaur;

import java.io.IOException;

public interface DinosaurReader extends AutoCloseable{
    Dinosaur read() throws IOException;
}
