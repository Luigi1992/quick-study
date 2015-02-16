package com.gcw_rome_2014.quickstudy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcw_rome_2014.quickstudy.model.Exam;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Luigi on 18/01/2015.
 */
public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    private Exam[] exams;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExamAdapter(Exam[] exams) {
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", Locale.ITALIAN);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(exams[position].getName());
        holder.mExamImageView.setImageResource(exams[position].getDifficulty().getImageValue());
        if (exams[position].isRegistered())
            holder.mExamIsRegistered.setText(R.string.exam_is_registered);
        else
            holder.mExamIsRegistered.setText(R.string.exam_not_registered);
        holder.mExamDateView.setText(sdf.format(exams[position].getExamDate().getTime()));
        holder.mExamTimeView.setText(sdf1.format(exams[position].getExamDate().getTime()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exams.length;
    }
}
