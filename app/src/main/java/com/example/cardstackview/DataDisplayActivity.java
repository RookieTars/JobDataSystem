package com.example.cardstackview;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataDisplayActivity extends AppCompatActivity {

    private DataAdapter adapter;
    private DrawerLayout mDrawerLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);
        Intent intent=getIntent();
        String account=intent.getStringExtra("account");
        String college=intent.getStringExtra("college");
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        sendRequestWithOkHttp(account,college);
    }

    private void sendRequestWithOkHttp(String account, final String college) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("category",college)
                            .build();
                    Request request=new Request.Builder()
                            .url("")
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseWithGson(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseWithGson(String responseData) {
        Gson gson=new Gson();
        List<Data> dataList=gson.fromJson(responseData,new TypeToken<List<Data>>()
        {}.getType());
        showDataInRecyclerView(dataList);
    }

    private void showDataInRecyclerView(final List<Data> dataList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
                GridLayoutManager layoutManager=new GridLayoutManager(DataDisplayActivity.this,
                        1);
                recyclerView.setLayoutManager(layoutManager);
                adapter=new DataAdapter(dataList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
