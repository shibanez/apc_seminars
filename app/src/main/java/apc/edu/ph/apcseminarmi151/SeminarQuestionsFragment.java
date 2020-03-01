package apc.edu.ph.apcseminarmi151;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathew on 11/12/2017.
 */

public class SeminarQuestionsFragment extends Fragment {
    private static final String TAG = "SeminarQuestionsFragment";

    private TextView title;
    private FloatingActionButton fabNewQ;
    private RecyclerView rv;
    private SeminarActivity seminarActivity;
    private Seminar seminar;
    private List<Question> questions = new ArrayList<>();
    private QuestionAdapter questionAdapter;

    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference questionsRef;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        seminarActivity = (SeminarActivity) getActivity();
        seminar = seminarActivity.getSeminar();


        rv = (RecyclerView) view.findViewById(R.id.rv_questions);
        questionAdapter = new QuestionAdapter(seminarActivity, questions);
        LinearLayoutManager tLayoutManager = new LinearLayoutManager(seminarActivity);
        rv.setLayoutManager(tLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        fabNewQ = (FloatingActionButton) view.findViewById(R.id.fab_newq);

        fabNewQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(seminarActivity);

                dialog.setContentView(R.layout.fragment_new_question);
                final TextInputEditText et_question = (TextInputEditText) dialog.findViewById(R.id.et_question);
                Button bt_sendq = (Button) dialog.findViewById(R.id.bt_sendq);

                bt_sendq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Question question = new Question(et_question.getText().toString());
                        questionsRef = rootRef.child("seminars/" + seminar.getId() + "/questions");
                        questionsRef.push().setValue(question);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        prepareQuestions(seminar.getId());


        return view;
    }

    private void prepareQuestions(String seminarId) {
        questionsRef = rootRef.child("seminars/" + seminarId + "/questions");

        questionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questions.clear();
                Question question;
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    question = questionSnapshot.getValue(Question.class);
                    question.setId(questionSnapshot.getKey());
                    question.setSeminarId(seminar.getId());
                    questions.add(question);

                }
                rv.setAdapter(questionAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
