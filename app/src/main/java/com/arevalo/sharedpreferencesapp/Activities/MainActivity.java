package com.arevalo.sharedpreferencesapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.arevalo.sharedpreferencesapp.Models.User;
import com.arevalo.sharedpreferencesapp.R;
import com.arevalo.sharedpreferencesapp.Repositories.UserRepository;

public class MainActivity extends AppCompatActivity {

    private TextView fullnameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullnameText = findViewById(R.id.fullname_text);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sp.getString("username",null);

        User usuario;
        usuario = UserRepository.findByUsername(username);

        if(usuario != null){
            fullnameText.setText(usuario.getFullname());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout_item:
                callLogout();
                break;
            case R.id.test_item:
                callPoll();
        }
        return super.onOptionsItemSelected(item);
    }

    private void callPoll() {
        startActivity(new Intent(this, PollActivity.class));
    }

    public void callLogout(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().remove("isLogged").commit();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}

