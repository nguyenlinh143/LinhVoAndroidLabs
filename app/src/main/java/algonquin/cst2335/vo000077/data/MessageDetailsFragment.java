package algonquin.cst2335.vo000077.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.vo000077.R;

public class MessageDetailsFragment extends Fragment {
    private TextView databaseIdTextView;
    private TextView messageTextView;
    private TextView sentOrReceiveTextView;
    private TextView timeSentTextView;

    private ChatMessage selectedMessage;

    public MessageDetailsFragment(ChatMessage message) {
        selectedMessage = message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_layout, container, false);

        // Initialize the TextViews
        databaseIdTextView = view.findViewById(R.id.idText);
        messageTextView = view.findViewById(R.id.messageText);
        //sentOrReceiveTextView = view.findViewById(R.id.messageText);
        timeSentTextView = view.findViewById(R.id.timeText);

        // Set the data in the TextViews
        databaseIdTextView.setText("Database ID: " + selectedMessage.getId());
        messageTextView.setText("Message: " + selectedMessage.getMessage());
    sentOrReceiveTextView.setText("Sent/Received: " + selectedMessage.getSendOrReceive());
        timeSentTextView.setText("Time Sent: " + selectedMessage.getTimeSent());

        return view;
    }
}
