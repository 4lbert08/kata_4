package software.ulpgc.kata4.architecture.control;

import software.ulpgc.kata4.architecture.io.*;
import software.ulpgc.kata4.architecture.model.Dinosaur;
import software.ulpgc.kata4.architecture.ui.ImportDialog;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ImportCommand implements  Command{
    private final ImportDialog dialog;

    public ImportCommand(ImportDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void execute() {
        try (DinosaurReader reader = new ZipDinosaurReader(inputFile(), new CsvDinosaurDeserializer());
             DinosaurWriter writer = DatabaseDinosaurWriter.open(outputFile())) {
            doExecute(reader, writer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doExecute(DinosaurReader reader, DinosaurWriter writer) throws IOException {
        Dinosaur dinosaur;
        while((dinosaur=reader.read())!=null)writer.write(dinosaur);
    }

    private File inputFile() {
        return dialog.get();
    }

    private File outputFile() {
        return new File("dinosaurs.db");
    }
}
