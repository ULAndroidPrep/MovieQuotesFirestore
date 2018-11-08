package ie.ul.davefisher.moviequotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
  }

  public void handleSignIn(View view) {
    Toast.makeText(this, "Sign in", Toast.LENGTH_SHORT).show();
  }

  public void handleSignUp(View view) {
    Toast.makeText(this, "Sign Up", Toast.LENGTH_SHORT).show();

  }
}
