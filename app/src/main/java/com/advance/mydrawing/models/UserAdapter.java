package com.advance.mydrawing.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.advance.mydrawing.databinding.UserItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zulfikar on 30 Dec 2023 at 22:14.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();
    private Context context;
    private OnAudioClickListener onAudioClickListener;

    public interface OnAudioClickListener {
        void onAudioClick(String audioUrl);
    }


    public UserAdapter(Context context, OnAudioClickListener listener) {
        this.context = context;
        this.onAudioClickListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        UserItemBinding binding = UserItemBinding.inflate(layoutInflater, parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user, onAudioClickListener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final UserItemBinding binding;

        public UserViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User user, OnAudioClickListener audioClickListener) {
            binding.setUser(user);
            binding.executePendingBindings();

            binding.cardView.setOnClickListener(v -> {
                if (audioClickListener != null) {
                    audioClickListener.onAudioClick(user.getImage());
                }
            });
        }
    }
}
