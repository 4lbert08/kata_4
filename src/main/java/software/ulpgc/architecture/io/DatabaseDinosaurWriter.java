package software.ulpgc.architecture.io;

import software.ulpgc.architecture.model.Dinosaur;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Types.VARCHAR;

public class DatabaseDinosaurWriter implements DinosaurWriter{
    private final Connection connection;
    private final PreparedStatement insertDinosaur;

    public DatabaseDinosaurWriter(String connection) throws SQLException {
        this(DriverManager.getConnection(connection));
    }

    public static DatabaseDinosaurWriter open(File file) throws SQLException {
        return new DatabaseDinosaurWriter("jdbc:sqlite:"+ file.getAbsolutePath());
    }

    public DatabaseDinosaurWriter(Connection connection) throws SQLException {
        this.connection = connection;
        disableAutoCommit();
        createTable();
        insertDinosaur = createInsertStatement();
    }

    private PreparedStatement createInsertStatement() throws SQLException {
        return connection.prepareStatement(insertDinosaurStatement);
    }

    private static final String createTableStatement = """
           CREATE TABLE dinosaurs (
               name TEXT PRIMARY KEY,
               diet TEXT,
               period TEXT,
               length TEXT
           )
       """;
    private static final  String deleteTableStatement = """
           DROP TABLE IF EXISTS dinosaurs
       """;
    private static final String insertDinosaurStatement = """
       INSERT INTO dinosaurs (name,diet,period,length)
       VALUES (?,?,?,?)
       """;
    private void createTable() throws SQLException {
        connection.createStatement().execute(deleteTableStatement);
        connection.createStatement().execute(createTableStatement);
    }

    private void disableAutoCommit() throws SQLException {
        connection.setAutoCommit(false);
    }

    @Override
    public void write(Dinosaur dinosaur) throws IOException {
        try {
            insertIntoDatabase(dinosaur).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement insertIntoDatabase(Dinosaur dinosaur) throws SQLException {
        insertDinosaur.clearParameters();
        for (Parameter parameter: getParametersOf(dinosaur)) {
            define(parameter);
        }
        return insertDinosaur;
    }

    private void define(Parameter parameter) {
        try {
            if (parameter.o==null) {
                insertDinosaur.setNull(parameter.id,parameter.type);
            } else {
                insertDinosaur.setObject(parameter.id,parameter.o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Parameter> getParametersOf(Dinosaur dinosaur) {
        return List.of(
                new Parameter(1, validParameter(dinosaur.name()), VARCHAR),
                new Parameter(2, validParameter(dinosaur.diet()), VARCHAR),
                new Parameter(3, validParameter(dinosaur.period()), VARCHAR),
                new Parameter(4, validParameter(dinosaur.length()), VARCHAR)
        );
    }

    private String validParameter(String line) {
        if ("".equals(line))return null;
        return line;
    }

    private record Parameter(int id, Object o, int type){}

    @Override
    public void close() throws Exception {
        try {
            connection.commit();
        } finally {
            connection.close();
        }
    }
}
