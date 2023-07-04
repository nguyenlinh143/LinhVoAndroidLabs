package algonquin.cst2335.vo000077.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="message")
    String message;
    @ColumnInfo(name="timeSent")
    String timeSent;
    @ColumnInfo(name="SendOrReceive")
    boolean SendOrReceive;


    public ChatMessage(int id, String message, String timeSent, boolean SendOrReceive) {
        this.id = id;
        this.message = message;
        this.timeSent = timeSent;
        this.SendOrReceive = SendOrReceive;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return SendOrReceive;
    }
}
