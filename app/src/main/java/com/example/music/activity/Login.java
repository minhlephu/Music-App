package com.example.music.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.music.R;
import com.example.music.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText etphone, etpassword;
    Button signIn;
    TextView RegisterNow;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etphone = (EditText) findViewById(R.id.etphone);
        etpassword = (EditText) findViewById(R.id.etpassword);
        signIn = (Button) findViewById(R.id.loginBtn);
        RegisterNow = findViewById(R.id.RegisterNowBtn);
        Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT);

        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://music-de534-default-rtdb.firebaseio.com/");
        final DatabaseReference customer = database.getReference("users");
        RegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT);
//                ProgressDialog mDialog = new ProgressDialog(SignIn.this);
//                mDialog.sentMessage("Please wait...");
                customer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(etphone.getText().toString()).exists()) {
                            User user = dataSnapshot.child(etphone.getText().toString()).getValue(User.class);
                            user.setPhone(etphone.getText().toString());
                            if (user.getPassword().equals(etpassword.getText().toString())) {
                                Intent homeIntent = new Intent(Login.this, MainActivity.class);
                                currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Login.this, "Bạn chưa đăng ký. Đăng ký", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

    }
}

