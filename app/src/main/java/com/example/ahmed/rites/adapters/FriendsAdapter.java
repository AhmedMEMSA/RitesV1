package com.example.ahmed.rites.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.rites.Pojo.Friend;
import com.example.ahmed.rites.R;


import java.util.ArrayList;

/**
 * Created by ahmed on 3/12/2016.
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.PersonViewHolder> {


    private Context context;

    public FriendsAdapter() {
        items = new ArrayList<>();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView fcv;
        TextView name;
        TextView id;


        PersonViewHolder(View itemView) {
            super(itemView);
            fcv = (CardView) itemView.findViewById(R.id.friends_card_view);
            name = (TextView) itemView.findViewById(R.id.friends_name_tv);
            id = (TextView) itemView.findViewById(R.id.friends_id_tv);
        }


    }

    ArrayList<Friend> items;

    public FriendsAdapter(ArrayList<Friend> items) {
        this.items = items;
    }

    public ArrayList<Friend> getItems() {
        return items;
    }

    public void setItems(ArrayList<Friend> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }


    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.friends_rv_item, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        int i = items.get(position).getId();
        String d = i+"";
        holder.id.setText(d);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        this.notifyDataSetChanged();
    }

    public Friend getItem(int position) {
        return items.get(position);
    }


}