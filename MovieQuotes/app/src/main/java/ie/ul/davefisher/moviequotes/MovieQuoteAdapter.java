package ie.ul.davefisher.moviequotes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieQuoteAdapter extends RecyclerView.Adapter<MovieQuoteAdapter.MovieQuoteViewHolder> {

  private List<DocumentSnapshot> mMovieQuoteSnapshots = new ArrayList<>();

  public MovieQuoteAdapter() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference movieQuotesRef = db.collection("moviequotes");

    movieQuotesRef.orderBy("created", Query.Direction.DESCENDING).limit(50)
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        if (e != null) {
          Log.w(MainActivity.TAG, "Listen failed.", e);
          return;
        }
        mMovieQuoteSnapshots = documentSnapshots.getDocuments();
        notifyDataSetChanged();
      }
    });
  }

  @NonNull
  @Override
  public MovieQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.moviequote_view, parent, false);
    return new MovieQuoteViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull MovieQuoteViewHolder holder, int position) {
    DocumentSnapshot mq = mMovieQuoteSnapshots.get(position);
    holder.mQuoteTextView.setText((String)mq.get("quote"));
    holder.mMovieTextView.setText((String)mq.get("movie"));
  }

  @Override
  public int getItemCount() {
    return mMovieQuoteSnapshots.size();
  }

  public class MovieQuoteViewHolder extends RecyclerView.ViewHolder {

    private TextView mQuoteTextView;
    private TextView mMovieTextView;
    public MovieQuoteViewHolder(View itemView) {
      super(itemView);
      mQuoteTextView = itemView.findViewById(R.id.quote);
      mMovieTextView = itemView.findViewById(R.id.movie);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          DocumentSnapshot mq = mMovieQuoteSnapshots.get(getAdapterPosition());

          Context context = v.getContext();
          Intent intent = new Intent(context, MovieQuoteDetailActivity.class);
          intent.putExtra("document_id", mq.getId());
          context.startActivity(intent);
        }
      });
    }
  }
}
