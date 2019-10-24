package com.arevalo.sharedpreferencesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arevalo.sharedpreferencesapp.Models.User;
import com.arevalo.sharedpreferencesapp.R;
import com.arevalo.sharedpreferencesapp.Repositories.UserRepository;

import java.util.prefs.PreferenceChangeEvent;

public class LoginActivity extends AppCompatActivity {

    private View loginPanel;
    private EditText username, password;
    private Button siginButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPanel= findViewById(R.id.login_panel);
        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        progressBar = findViewById(R.id.progressbar);
        siginButton = findViewById(R.id.sigin);
        siginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });

        loadLoastUsername();
    }

    public void doLogin(){
        String userna = username.getText().toString();
        String pass = password.getText().toString();

        if(userna.isEmpty()){
            username.setError("Ingrese el usuario");
            Toast.makeText(this,"Ingrese el usuario",Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.isEmpty()){
            password.setError("Ingrese el password");
            Toast.makeText(this,"Ingrese el password",Toast.LENGTH_SHORT).show();
            return;
        }

        loginPanel.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        User usuario;

        usuario = UserRepository.Login(userna,pass);

         if(usuario  == null){
             Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_SHORT).show();
             loginPanel.setVisibility(View.VISIBLE);
             progressBar.setVisibility(View.GONE);
             return;
         }


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                sp.edit()
                .putBoolean("isLogged",true)
                .putString("username",userna)
                .commit();

         startActivity(new Intent(this,MainActivity.class));
         finish();

    }

    private void loadLoastUsername(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String usu = sp.getString("username",null);
        if(usu !=null){
            username.setText(usu);
            password.requestFocus();
        }
    }

}
