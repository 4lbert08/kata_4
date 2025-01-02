package software.ulpgc.kata4.app;

import software.ulpgc.kata4.architecture.control.ImportCommand;
import software.ulpgc.kata4.architecture.ui.ImportDialog;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        new ImportCommand(importDialog()).execute();
    }

    private static ImportDialog importDialog() {
        return new ImportDialog() {
            @Override
            public File get() {
                return new File("C:\\Users\\User\\Universidad\\3º\\IS2\\katas\\kata4\\archive.zip");
            }
        };
    }
}
