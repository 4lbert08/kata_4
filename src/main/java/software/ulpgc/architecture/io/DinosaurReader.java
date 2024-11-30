package software.ulpgc.architecture.io;

import software.ulpgc.architecture.model.Dinosaur;

import java.io.IOException;

public interface DinosaurReader extends AutoCloseable{
    Dinosaur read() throws IOException;
}
