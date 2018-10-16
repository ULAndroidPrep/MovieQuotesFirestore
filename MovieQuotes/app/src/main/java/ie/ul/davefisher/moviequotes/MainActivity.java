package ie.ul.davefisher.moviequotes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//  public static final String TAG = "MQ";  // Should be in a Constants file.
//  private int mTempCounter = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

//    // Temporary learning area
//    final FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    // This is a single GET, we'll use Realtime updates!
//    db.collection("moviequotes")
//        .get()
//        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//          @Override
//          public void onComplete(@NonNull Task<QuerySnapshot> task) {
//            if (task.isSuccessful()) {
//              for (DocumentSnapshot document : task.getResult()) {
//                Log.d(TAG, document.getId() + " => " + document.getData());
//              }
//            } else {
//              Log.w(TAG, "Error getting documents.", task.getException());
//            }
//          }
//        });


    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();

//        Map<String, Object> mq = new HashMap<>();
//        mq.put("movie", "My movie " + mTempCounter);
//        mq.put("quote", "I can add documents");
//        db.collection("moviequotes").add(mq);
//        mTempCounter += 1;
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
