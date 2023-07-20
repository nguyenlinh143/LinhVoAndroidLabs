package algonquin.cst2335.vo000077.data;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.vo000077.R;
import algonquin.cst2335.vo000077.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.vo000077.databinding.ReceiveMessageBinding;
import algonquin.cst2335.vo000077.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ArrayList<ChatMessage> messages;

    RecyclerView.Adapter<MyRowHolder> myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        ChatMessageDAO mDAO = db.cmDAO();

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        chatModel.selectedMessage.observe(this, (newValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newValue);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, chatFragment)
                    .addToBackStack(" ")
                    .commit();
        });

        setSupportActionBar(binding.myToolbar);

        if (messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll(mDAO.getAllMessages());
                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter));

                myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
                    @NonNull
                    @Override
                    public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        if (viewType == 0) {
                            SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                            return new MyRowHolder(binding.getRoot());
                        } else {
                            ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                            return new MyRowHolder(binding.getRoot());
                        }
                    }

                    @Override
                    public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                        ChatMessage chatMessage = messages.get(position);
                        holder.messageText.setText(chatMessage.getMessage());
                        holder.timeText.setText(chatMessage.getTimeSent());
                    }

                    @Override
                    public int getItemCount() {
                        return messages.size();
                    }

                    @Override
                    public int getItemViewType(int position) {
                        ChatMessage chatMessage = messages.get(position);
                        if (chatMessage.isSentButton()) {
                            return 0; // Sent message
                        } else {
                            return 1; // Received message
                        }
                    }
                };

            });
        }

        binding.sendButton.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();
            String currentTime = getCurrentTime();

            ChatMessage chatMessage = new ChatMessage(messageText, currentTime, "sent");
            messages.add(chatMessage);

            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();
            String currentTime = getCurrentTime();

            ChatMessage chatMessage = new ChatMessage(messageText, currentTime, "received");
            messages.add(chatMessage);

            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
        });



        binding.recycleView.setAdapter(myAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
        return sdf.format(new Date());
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            //click anywhere in the adapter object.
            itemView.setOnClickListener(click -> {

                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);
                /* MyRowHolder newRow = myAdapter.onCreateViewHolder(null, myAdapter.getItemViewType(position));

                //alert dialog to ask if you want to do this first.
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);

                builder.setMessage("Do you want to delete this message : " + messageText.getText())   //set sms on alert window
                        .setTitle("Question : ") // set the title of alert dialog
                        .setNegativeButton("No", (dialog, cl) -> { }) // dont delete anything. Lambda function empty
                        .setPositiveButton("Yes", (dialog, cl) -> {

                            ChatMessage removedMessage = messages.get(position);
                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);

                            Snackbar.make(messageText, "You are deleted message # " + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clck -> {
                                        messages.add(position, removedMessage);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        })
                        .show(); */
            });

            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.item_about) {
            Toast.makeText(this, "Application version 1.0, created by Linh VO", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.item_delete) {
            ChatMessage selectedMessage = chatModel.selectedMessage.getValue();
            if (selectedMessage != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to delete this message: " + selectedMessage.getMessage())
                        .setTitle("Delete Message")
                        .setNegativeButton("No", (dialog, cl) -> {})
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            int position = messages.indexOf(selectedMessage);
                            if (position != -1) {
                                messages.remove(position);
                                myAdapter.notifyItemRemoved(position);

                                Snackbar.make(binding.getRoot(), "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                        .setAction("Undo", clck -> {
                                            messages.add(position, selectedMessage);
                                            myAdapter.notifyItemInserted(position);
                                        })
                                        .show();
                            }
                        })
                        .show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
