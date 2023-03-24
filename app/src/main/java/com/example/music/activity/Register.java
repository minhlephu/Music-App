package com.example.music.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;
import com.example.music.R;
import com.example.music.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText name, password, phone, email;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name =findViewById(R.id.etfullname);
        password = findViewById(R.id.etpassword);
        phone = findViewById(R.id.etphone);
        email = findViewById(R.id.etemail);
        signup = findViewById(R.id.btnregister);

        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*^]).{6,15})";
//        FirebaseDatabase.getInstance("https://music-de534-default-rtdb.firebaseio.com/").getReference()
        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://music-de534-default-rtdb.firebaseio.com/");
        final DatabaseReference customer = database.getReference("users");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(phone.getText().toString()).exists()) {
                            Toast.makeText(Register.this, "Số điện thoại này đã được đăng ký.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(!isEmailValid((email.getText().toString())))
                            {
                                Toast.makeText(Register.this, "Địa chỉ email không hợp lệ.", Toast.LENGTH_SHORT).show();
                            }
                            else if(!isPasswordValid(password.getText().toString())) {
                                Toast.makeText(Register.this, "Mật khẩu không hợp lệ. Mật khẩu bao gồm: 1 chữ số, 1 chữ thường, 1 chữ hoa, 1 ký hiệu đặc biệt, độ dài tối thiểu là 6 ký tự", Toast.LENGTH_LONG).show();
                            }
                            else {
                                User user = new User(name.getText().toString(), password.getText().toString(), email.getText().toString());
                                customer.child(phone.getText().toString()).setValue(user);
                                Toast.makeText(Register.this, "Bạn đã đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                    public boolean isPasswordValid (final String password){
                        Pattern pattern;
                        Matcher matcher;
                        pattern = Pattern.compile(PASSWORD_PATTERN);
                        matcher = pattern.matcher(password);
                        return matcher.matches();

                    }

                    boolean isEmailValid (CharSequence email)
                    {
                        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
                    }
                });
            }
        });
    }
}