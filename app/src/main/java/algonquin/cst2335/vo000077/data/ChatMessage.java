package algonquin.cst2335.vo000077.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "message")
    public String message;

    @ColumnInfo(name = "timeSent")
    public String timeSent;

    @ColumnInfo(name = "SendOrReceive")
    public String sendOrReceive;

    public ChatMessage(String message, String timeSent, String sendOrReceive) {
        this.message = message;
        this.timeSent = timeSent;
        this.sendOrReceive = sendOrReceive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public String getSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(String sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }

    public boolean isSentButton() {
        return "sent".equalsIgnoreCase(sendOrReceive);
    }
}
