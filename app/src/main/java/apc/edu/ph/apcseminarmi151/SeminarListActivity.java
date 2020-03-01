package apc.edu.ph.apcseminarmi151;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeminarListActivity extends AppCompatActivity {

    private List<Seminar> seminarList = new ArrayList<>();
    private RecyclerView rv;
    private SeminarAdapter seminarAdapter;
    private DatabaseReference seminarsRef;
    private ProgressBar spinner;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seminar_list);

        spinner = (ProgressBar) findViewById(R.id.progressCircle);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        rv = (RecyclerView) findViewById(R.id.rv);
        seminarsRef = FirebaseDatabase.getInstance().getReference("seminars");

        seminarAdapter = new SeminarAdapter(this, seminarList);
        LinearLayoutManager tLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(tLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout.setColorSchemeResources(R.color.primaryColor, R.color.primaryLightColor, R.color.primaryDarkColor);

        prepareSeminars();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeminarListActivity.this, SeminarEditActivity.class);
                startActivity(i);
            }
        });



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rv.removeAllViewsInLayout();
                        seminarList.clear();
                        prepareSeminars();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }, 2500);
            }
        });

    }



    private void prepareSeminars() {
        spinner.setVisibility(View.VISIBLE);
        seminarsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Seminar seminar;
                for (DataSnapshot seminarSnapshot : dataSnapshot.getChildren()) {
                    seminar = seminarSnapshot.getValue(Seminar.class);
                    seminar.setId(seminarSnapshot.getKey());
                    seminarList.add(seminar);
                    rv.setAdapter(seminarAdapter);
                }
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}