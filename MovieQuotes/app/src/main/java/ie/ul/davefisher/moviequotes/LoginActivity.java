package ie.ul.davefisher.moviequotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

  private EditText mEmailEditText;
  private EditText mPasswordEditText;
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mEmailEditText = findViewById(R.id.email_edittext);
    mPasswordEditText = findViewById(R.id.password_edittext);
    mAuth = FirebaseAuth.getInstance();

    if (mAuth.getCurrentUser() != null) {
      Intent mainIntent = new Intent(this, MainActivity.class);
      startActivity(mainIntent);
    }
  }



  public void handleSignIn(View view) {
    String email = mEmailEditText.getText().toString();
    String password = mPasswordEditText.getText().toString();
    if (email.length() < 4 || !email.contains("@")) {
      mEmailEditText.setError(getString(R.string.invalid_email));
    } else
    if (password.length() < 4) {
      mPasswordEditText.setError(getString(R.string.invalid_password));
    } else {
      mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if (task.isSuccessful()) {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
          } else {
            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  public void handleSignUp(View view) {
    String email = mEmailEditText.getText().toString();
    String password = mPasswordEditText.getText().toString();
    if (email.length() < 4 || !email.contains("@")) {
      mEmailEditText.setError(getString(R.string.invalid_email));
    } else
    if (password.length() < 4) {
      mPasswordEditText.setError(getString(R.string.invalid_password));
    } else {
      mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if (task.isSuccessful()) {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
          } else {
            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

}
