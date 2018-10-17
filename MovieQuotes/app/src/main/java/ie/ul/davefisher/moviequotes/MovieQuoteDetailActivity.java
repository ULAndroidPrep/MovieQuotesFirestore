package ie.ul.davefisher.moviequotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MovieQuoteDetailActivity extends AppCompatActivity {

  private DocumentReference mDocRef;
  private DocumentSnapshot mMovieQuoteSnapshot;
  private TextView mQuoteTextView;
  private TextView mMovieTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_quote_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mQuoteTextView = findViewById(R.id.detail_quote);
    mMovieTextView = findViewById(R.id.detail_movie);



    String docId = getIntent().getStringExtra("document_id");
    mQuoteTextView.setText(docId);

    mDocRef = FirebaseFirestore.getInstance().collection("moviequotes").document(docId);

    mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
      @Override
      public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
        if (e != null) {
          Log.w(MainActivity.TAG, "Listen failed.", e);
          return;
        }
        if (documentSnapshot.exists()) {
          mMovieQuoteSnapshot = documentSnapshot;
          mQuoteTextView.setText((String)documentSnapshot.get("quote"));
          mMovieTextView.setText((String)documentSnapshot.get("movie"));
        }
      }
    });

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showEditDialog();
      }
    });
  }

  private void showEditDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Edit Movie Quote");
    View view = getLayoutInflater().inflate(R.layout.movie_quote_dialog, null, false);
    builder.setView(view);
    final EditText quoteEditText = view.findViewById(R.id.dialog_quote_edittext);
    final EditText movieEditText = view.findViewById(R.id.dialog_movie_edittext);
    quoteEditText.setText((String)mMovieQuoteSnapshot.get("quote"));
    movieEditText.setText((String)mMovieQuoteSnapshot.get("movie"));

    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        Map<String, Object> mq = new HashMap<>();
        mq.put("quote", quoteEditText.getText().toString());
        mq.put("movie", movieEditText.getText().toString());
        mq.put("created", new Date());
        mDocRef.update(mq);
      }
    });
    builder.setNegativeButton(android.R.string.cancel, null);
    builder.create().show();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId()) {
      case R.id.action_remove:
        mDocRef.delete();
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
