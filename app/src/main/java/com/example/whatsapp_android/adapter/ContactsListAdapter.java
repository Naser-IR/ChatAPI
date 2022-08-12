package com.example.whatsapp_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_android.ChatActivity;
import com.example.whatsapp_android.Data.Contact;
import com.example.whatsapp_android.PersonalChat;
import com.example.whatsapp_android.R;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    private LayoutInflater mInflter;
    private List<Contact> contacts;
    private Context context;
    private ChatActivity chatContext;


    public void setContacts(List<Contact> con){
        contacts = con;
        notifyDataSetChanged();
    }

    public ContactsListAdapter(Context context) {
        this.context = context;
        this.chatContext = (ChatActivity) context;
        mInflter = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflter.inflate(R.layout.contact_layout,parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts !=null){
            final Contact curr = contacts.get(position);
            holder.nickname.setText(curr.name);
            if (curr.last != null) {
                holder.lastmesage.setText(curr.last);
                holder.lastMessageTime.setText(curr.lastdate.substring(11,16));

            } else {
                holder.lastmesage.setText("");
            }
            if (context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                holder.press.setOnClickListener(v -> {
                    chatContext.goToContact(curr.contname);
                });
            } else {
                holder.press.setOnClickListener(v -> {
                    chatContext.openChatContact(curr.contname);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (contacts !=null)
            return contacts.size();
        else return 0;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView nickname;
        private TextView lastmesage;
        private RelativeLayout press;
        private TextView lastMessageTime;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_name);
            lastmesage = itemView.findViewById(R.id.lastmessage);
            press = itemView.findViewById(R.id.contactItem);
            lastMessageTime = itemView.findViewById(R.id.lastMessageTime);
        }
    }
}
