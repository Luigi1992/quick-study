package com.gcw_rome_2014.quickstudy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcw_rome_2014.quickstudy.model.Exam;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Luigi on 18/01/2015.
 */
public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    private List<Exam> exams;
    private OnItemClickListener mItemClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mExamImageView;
        public TextView mExamIsRegistered;
        public TextView mExamDateView;
        public TextView mExamTimeView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.exam_name_text);
            mExamImageView = (ImageView) v.findViewById(R.id.exam_image_view);
            mExamIsRegistered = (TextView) v.findViewById(R.id.exam_registered);
            mExamDateView = (TextView) v.findViewById(R.id.exam_date_view);
            mExamTimeView = (TextView) v.findViewById(R.id.exam_time_view);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick (View v){
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }
    //Provide ClickListener for ViewHolder
    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExamAdapter(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(exams.get(position).getName());
        holder.mExamImageView.setImageResource(exams.get(position).getDifficulty().getImageValue());
        if (exams.get(position).isRegistered())
            holder.mExamIsRegistered.setText(R.string.exam_is_registered);
        else
            holder.mExamIsRegistered.setText(R.string.exam_not_registered);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        holder.mExamDateView.setText(sdf.format(exams.get(position).getDate().getTime()));
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", Locale.ITALIAN);
        holder.mExamTimeView.setText(sdf1.format(exams.get(position).getDate().getTime()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exams.size();
    }

    public void sort() {
        Collections.sort(this.exams, new Comparator<Exam>() {
            @Override
            public int compare(Exam e1, Exam e2) {
                return e1.getDate().compareTo(e2.getDate());
            }
        });

    }
}


