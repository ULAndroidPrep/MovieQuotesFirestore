package ie.ul.davefisher.moviequotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//  private int mTempCounter = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    final RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);

    final MovieQuoteAdapter movieQuoteAdapter = new MovieQuoteAdapter();
    recyclerView.setAdapter(movieQuoteAdapter);


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



    // Temporary Auth learning area
    FirebaseAuth auth = FirebaseAuth.getInstance();
//    auth.signOut();  // For testing only

    FirebaseUser currentUser = auth.getCurrentUser();
    if (currentUser == null) {
      Log.d(Constants.TAG, "No user.  Need to sign in anonymously.");
      auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if(task.isSuccessful()) {
            Log.d(Constants.TAG, "We have a user!");
            recyclerView.setAdapter(new MovieQuoteAdapter()); // Remake the adapter
          }
        }
      });
    } else {
      Log.d(Constants.TAG, "This is the second (or later) launch.  Use the current user");
    }



    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showAddDialog();

//        Map<String, Object> mq = new HashMap<>();
//        mq.put("movie", "My movie " + mTempCounter);
//        mq.put("quote", "I can add documents");
//        db.collection("moviequotes").add(mq);
//        mTempCounter += 1;
      }
    });
  }


  private void showAddDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Add a Movie Quote");
    View view = getLayoutInflater().inflate(R.layout.movie_quote_dialog, null, false);
    builder.setView(view);
    final EditText quoteEditText = view.findViewById(R.id.dialog_quote_edittext);
    final EditText movieEditText = view.findViewById(R.id.dialog_movie_edittext);

    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        Map<String, Object> mq = new HashMap<>();
        mq.put(Constants.KEY_QUOTE, quoteEditText.getText().toString());
        mq.put(Constants.KEY_MOVIE, movieEditText.getText().toString());
        mq.put(Constants.KEY_USER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
        mq.put(Constants.KEY_CREATED, new Date());
        FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH).add(mq);
      }
    });
    builder.setNegativeButton(android.R.string.cancel, null);

    builder.create().show();
  }

}
