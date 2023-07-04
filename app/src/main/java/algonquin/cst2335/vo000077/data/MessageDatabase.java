package algonquin.cst2335.vo000077.data;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Dao // DAO interface - responsible for defining the database operations (queries, updates, inserts, deletes).
@Database(entities = {ChatMessage.class}, version=1)
public abstract class MessageDatabase  extends RoomDatabase {
    public abstract ChatMessageDAO cmDAO();
}
