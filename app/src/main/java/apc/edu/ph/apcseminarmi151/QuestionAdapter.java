package apc.edu.ph.apcseminarmi151;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static apc.edu.ph.apcseminarmi151.R.id.et_question;

/**
 * Created by Mathew on 11/21/2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<Question> questions;
    private Context context;
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference questionsRef;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuestion;
        public CardView cv;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
            cv = (CardView) itemView.findViewById(R.id.cv_question);


        }
    }

    public QuestionAdapter(Context context, List<Question> questions){
        this.questions = questions;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_question_item, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Question question = questions.get(position);

        holder.tvQuestion.setText(question.getQuestion());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.fragment_new_question);
                final TextInputEditText et_answer = (TextInputEditText) dialog.findViewById(et_question);
                Button bt_sendq = (Button) dialog.findViewById(R.id.bt_sendq);
                TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);

                if(question.getAnswer() != null){
                    et_answer.setText(question.getAnswer());
                }

                tv_title.setText("Answer");

                bt_sendq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        question.setAnswer(et_answer.getText().toString());
                        questionsRef = rootRef.child("seminars/" + question.getSeminarId() + "/questions/" + question.getId());
                        questionsRef.setValue(question);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (question.getAnswer() == null ){
                    Toast.makeText(context, "Not answered yet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, question.getAnswer(), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

}
