package software.ulpgc.kata4.architecture.io;

import software.ulpgc.kata4.architecture.model.Dinosaur;

public interface DinosaurDeserializer {
    Dinosaur deserialize(String line);
}
