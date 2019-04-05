package nguyenhai1449.com.myinstagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, fullname, email, password;
    Button register;
    TextView txtLogin;


    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.edtUsername);
        fullname = findViewById(R.id.edtFullName);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        register = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        auth = FirebaseAuth.getInstance();
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Please wait..");
                progressDialog.show();

                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(RegisterActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else  if (str_password.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this, "Password mut have 6 characters", Toast.LENGTH_SHORT).show();
                }else {
                    register(str_username, str_fullname, str_email, str_password);
                }
            }
        });
    }


    public void register(final String username, final String fullname, final String email, final String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username.toLowerCase());
                    hashMap.put("bio", "");
                    hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/myinstagram-cf4b2.appspot.com/o/user.png?alt=media&token=34447bb7-014c-4f80-bce0-822fb69024cd");


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                }else {
                    Toast.makeText(RegisterActivity.this, "You can't register with this email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
