package com.example.ashish.movielisting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.ashish.movielisting.rest.RetrofitManager;
import com.example.ashish.movielisting.rest.response.MovieListing;
import com.example.ashish.movielisting.rest.response.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Result> upcomingMovieList = new ArrayList<>();
    private MovieListAdapter rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_movie_listing);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);*/

       /* LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
*/
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        getMovieListing();

//        List<MovieItemList> staggeredListContent = getMovieList();
         rcAdapter= new MovieListAdapter(MainActivity.this, upcomingMovieList);
        recyclerView.setAdapter(rcAdapter);

    }

    private List<MovieItemList> getMovieList() {
        List<MovieItemList> listView = new ArrayList<>();
        listView.add(new MovieItemList("Sultan", R.drawable.sultan, "6th july", 9.0f));
        listView.add(new MovieItemList("Bajrangi Bhaijaan", R.drawable.bajrangi, "15th july", 8.5f));
        listView.add(new MovieItemList("Kick", R.drawable.kick, "25th july", 9.5f));
        listView.add(new MovieItemList("Jai Ho", R.drawable.jaiho, "1st august", 9.0f));
        return listView;
    }

    private void getMovieListing() {
        RetrofitManager.getInstance().getMovieList("9ee4b67c5f201aa49b4344bcd6d83ef3", new Callback<MovieListing>() {
            @Override
            public void onResponse(Call<MovieListing> call, Response<MovieListing> response) {
                Log.i(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {

                    upcomingMovieList.addAll(response.body().getResults());
                    rcAdapter.notifyDataSetChanged();

                    Log.i(TAG, "onResponse: " + response.body().getResults().get(0).getOriginalTitle());


                }
            }

            @Override
            public void onFailure(Call<MovieListing> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

}
