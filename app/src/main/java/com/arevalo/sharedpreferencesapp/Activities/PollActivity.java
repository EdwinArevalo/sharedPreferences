package com.arevalo.sharedpreferencesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.arevalo.sharedpreferencesapp.Activities.LoginActivity;
import com.arevalo.sharedpreferencesapp.Activities.MainActivity;
import com.arevalo.sharedpreferencesapp.Models.User;
import com.arevalo.sharedpreferencesapp.R;
import com.arevalo.sharedpreferencesapp.Repositories.UserRepository;

public class PollActivity extends AppCompatActivity {

    private EditText fullname, specialty;
    private RadioGroup radioSex;
    private CheckBox acpetCheck;

    private SharedPreferences sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        fullname = findViewById(R.id.username_input);
        specialty = findViewById(R.id.specialty_input);
        radioSex = findViewById(R.id.radioSex);
        acpetCheck = findViewById(R.id.checkbox);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sp.getString("username",null);
        User usuario;
        usuario = UserRepository.findByUsername(username);
        if(usuario != null){
            fullname.setText(usuario.getFullname());
        }

        sp2 = PreferenceManager.getDefaultSharedPreferences(this);

        specialty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String special= editable.toString();
                sp2.edit().putString("specialty",special).commit();
            }
        });

        String special= sp2.getString("specialty",null);
        if(special != null){
            specialty.setText(special);
        }

        radioSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioF:
                        sp2.edit().putString("sex","F").commit();
                        break;
                    case R.id.radioM:
                        sp2.edit().putString("sex","M").commit();
                        break;
                }
            }
        });

        String sex = sp2.getString("sex",null);
        if(sex != null){
            if(sex .equals("F")){
                radioSex.check(R.id.radioF);
            }else if(sex.equals("M")){
                radioSex.check(R.id.radioM);
            }
        }

        acpetCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(acpetCheck.isChecked()){
                    sp2.edit().putBoolean("check",false).commit();
                }
            }
        });

        Boolean check = sp2.getBoolean("check",false);
        acpetCheck.setChecked(check);

    }

    public void callSubmit(View view) {
        sp2.edit()
                .remove("specialty")
                .remove("sex")
                .commit();
        acpetCheck.setChecked(false);
        Toast.makeText(this,"Encuesta enviada",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,MainActivity.class));
    }
}
