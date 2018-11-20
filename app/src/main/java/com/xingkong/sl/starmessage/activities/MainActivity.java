package com.xingkong.sl.starmessage.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xingkong.sl.starmessage.HttpCallbackListener;
import com.xingkong.sl.starmessage.R;
import com.xingkong.sl.starmessage.adapter.ItemAdapter;
import com.xingkong.sl.starmessage.model.Message;
import com.xingkong.sl.starmessage.util.DataUtil;
import com.xingkong.sl.starmessage.util.Global;
import com.xingkong.sl.starmessage.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinHai on 2017/1/9.
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button send;
    private EditText et_send;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private ItemAdapter mAdapter;

    private List<Message> mItemList = new ArrayList<>();

    private static String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        username = pref.getString("username","");

        setSupportActionBar(toolbar);

        toolbar.setSubtitle(username);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_exit:
                        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                        editor.putBoolean("isAutoLogin",false);
                        editor.putString("username","");
                        editor.putString("password","");
                        editor.apply();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        Toast.makeText(MainActivity.this,R.string.exit_succ,Toast.LENGTH_SHORT).show();
                        finish();
                }
                return true;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ItemAdapter(mItemList);
        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_send.setEnabled(false);
                String message = et_send.getText().toString().trim();
                HttpUtil.sendHttpRequest(Global.BASE_URL + Global.API_PARAM[2] + "?s=send" + "&u=" + username + "&w=" + message
                        , Global.REQUEST_METHOD[1]
                        , new HttpCallbackListener() {
                    @Override
                    public void onFinish(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.equals("succ")){
                                    refreshData();
                                }
                            }
                        });

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                et_send.setEnabled(true);
                et_send.setText("");
            }
        });

        refreshData();
    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(true);
        HttpUtil.sendHttpRequest(Global.BASE_URL + Global.API_PARAM[2], Global.REQUEST_METHOD[0], new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                System.out.println(response);
                DataUtil.parseDataToArray(response,mItemList);
                System.out.println(mItemList.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolBar);
        send = findViewById(R.id.bt_send);
        et_send = findViewById(R.id.et_send);
        swipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = findViewById(R.id.id_recyclerview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}