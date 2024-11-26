package software.ulpgc.architecture.io;

import software.ulpgc.architecture.model.Dinosaur;

public interface DinosaurDeserializer {
    Dinosaur deserialize(String line);
}
