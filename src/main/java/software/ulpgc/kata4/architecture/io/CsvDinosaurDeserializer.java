package software.ulpgc.kata4.architecture.io;

import software.ulpgc.kata4.architecture.model.Dinosaur;

public class CsvDinosaurDeserializer implements DinosaurDeserializer{
    @Override
    public Dinosaur deserialize(String line) {
        return deserialize(line.split(","));
    }

    private Dinosaur deserialize(String[] fields) {
        return new Dinosaur(fields[0],fields[1],periodDeserializer(fields[2].split(" ")),fields[5]);
    }

    private String periodDeserializer(String[] fields) {
        return fields[0] + fields[1];
    }
}
