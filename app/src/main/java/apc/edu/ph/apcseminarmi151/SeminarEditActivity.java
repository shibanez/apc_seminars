package apc.edu.ph.apcseminarmi151;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeminarEditActivity extends AppCompatActivity {

    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference seminarRef = rootRef.child("seminars");
    private DatabaseReference venuesRef = rootRef.child("venues");
    private DatabaseReference categoriesRef = rootRef.child("categories");;

    private TextInputEditText et_title, et_date, et_start, et_end, et_speaker, et_agenda;
    private Spinner sp_venue, sp_category;
    private Button bt_save;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seminar_edit);


        venuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> venues = new ArrayList<String>();
                for (DataSnapshot venueSnapshot : dataSnapshot.getChildren()) {
                    String venue = venueSnapshot.child("venue").getValue(String.class);
                    venues.add(venue);
                }
                sp_venue = (Spinner) findViewById(R.id.sp_venue);
                ArrayAdapter<String> venuesAdapter = new ArrayAdapter<String>(SeminarEditActivity.this,
                        android.R.layout.simple_spinner_item, venues);
                venuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_venue.setAdapter(venuesAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> categories = new ArrayList<String>();
                for (DataSnapshot venueSnapshot : dataSnapshot.getChildren()) {
                    String category = venueSnapshot.child("category").getValue(String.class);
                    categories.add(category);
                }
                sp_category = (Spinner) findViewById(R.id.sp_category);
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(SeminarEditActivity.this,
                        android.R.layout.simple_spinner_item, categories);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_category.setAdapter(categoryAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sp_category = (Spinner) findViewById(R.id.sp_category);


        et_title = (TextInputEditText) findViewById(R.id.et_seminar_title);
        et_date = (TextInputEditText) findViewById(R.id.et_date);
        et_start = (TextInputEditText) findViewById(R.id.et_start);
        et_end = (TextInputEditText) findViewById(R.id.et_end);
        et_speaker = (TextInputEditText) findViewById(R.id.et_speaker);
        et_agenda = (TextInputEditText) findViewById(R.id.et_agenda);
        sp_venue = (Spinner) findViewById(R.id.sp_venue);
        bt_save = (Button) findViewById(R.id.bt_save);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "Success", Snackbar.LENGTH_LONG)
               .setAction("Return", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeminarEditActivity.this, SeminarListActivity.class);
                startActivity(i);
            }
        });



        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title, category, date, start, end, venue, speaker, agenda;
                title = et_title.getText().toString();
                category = sp_category.getSelectedItem().toString();
                date = et_date.getText().toString();
                start = et_start.getText().toString();
                end = et_end.getText().toString();
                speaker = et_speaker.getText().toString();
                agenda = et_agenda.getText().toString();
                venue = sp_venue.getSelectedItem().toString();

                setNewSeminar(title, category, date, start, end, venue, speaker, agenda);

                snackbar.show();
            }
        });


    }

    private void setNewSeminar(String title, String category, String date, String start, String end, String venue, String speaker, String agenda) {
        DatabaseReference seminarRef = FirebaseDatabase.getInstance().getReference().child("seminars");
        Seminar seminar = new Seminar(title, category, date, start, end, venue, speaker, agenda);
        try {
            seminarRef.push().setValue(seminar);
        } catch (Exception e){
            Toast.makeText(this, "Connecting... please try again in a few seconds.", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, title + " " + date + " " + start + " " + end + " " + venue + " " + speaker + " " + agenda, Toast.LENGTH_SHORT).show();
    }

}
