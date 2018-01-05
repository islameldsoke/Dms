package com.eldsoke.islam.dmstask;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public final static String token = "092b5901e25d2d9f756f42231dad8f3e2218b8fd";
    final int limit = 10;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    int mPage = 1;
    List<Repositories> repositoriesList;
    RepoAdapter repoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        repositoriesList = new ArrayList<>();
        repoAdapter = new RepoAdapter(this, repositoriesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(repoAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if (MyCustomApplication.isOnline()) {
                    swipeRefreshLayout.setRefreshing(true);
                    getRepo(mPage, limit);
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                mPage++;
                getRepo(mPage, limit);


            }
        });

        getRepo(mPage, limit);
    }

    void getRepo(int page, int limit) {


        WebService.getInstance().getApi().getRepo(page, limit)
                .enqueue(new Callback<List<Repositories>>() {
                    @Override
                    public void onResponse(Call<List<Repositories>> call, Response<List<Repositories>> response) {

                        Log.e("res", response.toString());

                        if (response.body() != null) {
                            swipeRefreshLayout.setRefreshing(true);
                            repositoriesList.addAll(response.body());
                            repoAdapter.notifyItemRangeInserted(repoAdapter.getItemCount(), repositoriesList.size() - 1);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            Toast.makeText(MainActivity.this, "No Cash", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Repositories>> call, Throwable t) {
                        Log.e("fail", t.getMessage());

                    }
                });
    }

    @Override
    public void onRefresh() {

        if (MyCustomApplication.isOnline()) {
            try {
                WebService.getInstance().deletCache();
                repositoriesList.clear();
            } catch (IOException e) {
                Log.e("cache", e.getMessage());
            }
            getRepo(mPage, limit);
            swipeRefreshLayout.setRefreshing(false);

        } else {
            repositoriesList.clear();
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            try {
                WebService.getInstance().deletCache();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
