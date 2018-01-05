package com.eldsoke.islam.dmstask;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by islam on 1/3/2018.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.MyViewHolder> {

    private Context context;
    private List<Repositories> repositoriesList;

    public RepoAdapter(Context context, List<Repositories> repositoriesList) {
        this.context = context;
        this.repositoriesList = repositoriesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(repositoriesList.get(position).getRepoName());
        holder.description.setText(repositoriesList.get(position).getDescription());
        holder.userName.setText(repositoriesList.get(position).getOwner().getLogin());
        final List<String> urls = new ArrayList<>();
        urls.add(repositoriesList.get(position).getRepositoryUrl());
        urls.add(repositoriesList.get(position).getOwner().getRepositoryUrl());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new MaterialDialog.Builder(context)
                        .title("URLS")
                        .items(urls)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                String url = text.toString();
                                Intent intent = new Intent(context, UrlsActivity.class);
                                intent.putExtra("url", url);
                                context.startActivity(intent);
                            }
                        })
                        .show();

                return true;
            }
        });

        if (repositoriesList.get(position).getFork().equals("false")) {
            holder.itemView.setBackgroundResource(R.color.green);
        } else {
            holder.itemView.setBackgroundResource(R.color.gray);
        }

    }

    public void clear() {
        this.repositoriesList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return repositoriesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, userName;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_tv);
            description = itemView.findViewById(R.id.description_tv);
            userName = itemView.findViewById(R.id.userName_tv);

        }
    }
}
