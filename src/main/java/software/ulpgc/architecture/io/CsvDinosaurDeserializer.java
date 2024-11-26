package software.ulpgc.architecture.io;

import software.ulpgc.architecture.model.Dinosaur;

public class CsvDinosaurDeserializer implements DinosaurDeserializer{
    @Override
    public Dinosaur deserialize(String line) {
        return deserialize(line.split(","));
    }

    private Dinosaur deserialize(String[] fields) {
        return new Dinosaur(fields[0],fields[1],periodDdeserializer(fields[2].split(" ")),fields[5]);
    }

    private String periodDdeserializer(String[] fields) {
        return fields[0] + fields[1];
    }
}
