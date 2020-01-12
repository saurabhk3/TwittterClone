package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    ArrayList<String> userList;
    ArrayAdapter<String> adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setTitle("UsersList");

        listView = (ListView)findViewById(R.id.userList);
        userList = new ArrayList<>();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked,userList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = (CheckedTextView)view;
                if(checkedTextView.isChecked()){
                    ParseUser.getCurrentUser().add("following",userList.get(i)); // add the selected user to follow array
                    Toast.makeText(UserListActivity.this,"Followed!",Toast.LENGTH_SHORT).show();

                }else{
                    ParseUser.getCurrentUser().getList("following").remove(userList.get(i));
                    List tempUser =ParseUser.getCurrentUser().getList("following");
                    ParseUser.getCurrentUser().remove("following");
                    ParseUser.getCurrentUser().add("following",tempUser);
                    Toast.makeText(UserListActivity.this,"UnFollowed!",Toast.LENGTH_SHORT).show();
                }
                ParseUser.getCurrentUser().saveInBackground();
            }
        });

        showActiveUsers();
    }
    public void showActiveUsers(){
        ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();

        userParseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    for(ParseUser users:objects){
                        userList.add(users.getString("username"));
                    }
                    adapter.notifyDataSetChanged();
                    for(String user:userList){
                        if(ParseUser.getCurrentUser().getList("following").contains(user)){
                            listView.setItemChecked(userList.indexOf(user),true);
                        }
                    }
//                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.tweet){
            Intent intent = new Intent(getApplicationContext(),TweetActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId()==R.id.logout){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            return false;
        }
        return false;
    }
}
