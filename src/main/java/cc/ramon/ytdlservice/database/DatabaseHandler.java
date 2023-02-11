package cc.ramon.ytdlservice.database;

public class DatabaseHandler {

    private static DatabaseHandler databaseHandlerInstance;

    private DatabaseHandler() {
    }

    public static DatabaseHandler getInstance() {
        if (databaseHandlerInstance == null) {
            databaseHandlerInstance = new DatabaseHandler();
        }
        return databaseHandlerInstance;
    }


}
