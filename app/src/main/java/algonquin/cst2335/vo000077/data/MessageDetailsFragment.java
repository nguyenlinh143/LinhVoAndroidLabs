package algonquin.cst2335.vo000077.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.vo000077.databinding.DetailsLayoutBinding;

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
        super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.details_layout, container, false);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);
binding.messageText.setText(selectedMessage.message);
        binding.timeText.setText(selectedMessage.timeSent);
        binding.idText.setText("id= " + selectedMessage.id);
        // Initialize the TextViews


        return binding.getRoot();
    }

}
