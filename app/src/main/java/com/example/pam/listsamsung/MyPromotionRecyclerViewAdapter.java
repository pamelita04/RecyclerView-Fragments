package com.example.pam.listsamsung;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pam.listsamsung.PromotionFragment.OnListFragmentInteractionListener;
import com.example.pam.listsamsung.dummy.DummyContent.DummyItem;
import com.example.pam.listsamsung.dummy.Promotion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPromotionRecyclerViewAdapter extends RecyclerView.Adapter<MyPromotionRecyclerViewAdapter.ViewHolder> {

    private final List<Promotion> mValues;
    //List<Promo> promos;
    private final OnListFragmentInteractionListener mListener;

    public MyPromotionRecyclerViewAdapter(List<Promotion> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_promotion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Promotion promo = mValues.get(position);

        holder.textViewTitle.setText(promo.getName());
        holder.textViewDescription.setText(promo.getShortDescription());

        Context context = holder.imageView.getContext();
        ImageView imageView = holder.imageView;
        Glide.with(context)
                .load(promo.getImage())
                .crossFade()
                .fitCenter()
                .placeholder(R.drawable.card02)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.5f)
                .into(imageView);


        //String enDateResult = promo.getEndDate();
        Date endDate = simpleDateFormatStringToDate(promo.getEndDate());
        String enDateResult = convertDateToString(endDate);
        holder.textViewDate.setText(enDateResult);

        Date today = Calendar.getInstance().getTime();

        if (endDate.after(today))

            if (promo.getStatus().equalsIgnoreCase("ACTIVE"))
                holder.textViewTypeStatus.setText(R.string.lbl_status_active);
            else
                holder.textViewTypeStatus.setText(R.string.lbl_status_noActive);

        else{

            int color = R.color.colorAccent;
            holder.textViewDate.setTextColor(ContextCompat.getColor(context, color));
            holder.textViewTypeStatus.setTextColor(ContextCompat.getColor(context, color));
            holder.textViewTypeStatus.setText(R.string.lbl_status_expired);
        }
    }

    private String convertDateToString (Date dateActual){

        //(2) crear una fecha "formateador" (el formato de fecha que queremos)
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        //(3) crear una nueva String usando el formato de fecha que queremos
        String folderName = formatter.format(dateActual);

        Log.i("date", "Date to String convert" + folderName);

        return folderName;
    }

    private Date simpleDateFormatStringToDate (String userInput){
        Date date = new Date();

        // (1) create a SimpleDateFormat object with the desired format.
        // this is the format/pattern we're expecting to receive.
        String expectedPattern = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
        try
        {
            // (2) give the formatter a String that matches the SimpleDateFormat pattern
            date = formatter.parse(userInput);
            // (3) prints out "Tue Sep 22 00:00:00 EDT 2009"
            Log.i("date", "String to date" + date);
            return date;
        }
        catch (ParseException e)
        {
            // execution will come here if the String that is given
            // does not match the expected format.
            e.printStackTrace();
        }
        return date;

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDescription, textViewDate, textViewTypeStatus,
                textViewTypeStatusDate;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            imageView = (ImageView) itemView.findViewById(R.id.promo_image);
            textViewTitle = (TextView) itemView.findViewById(R.id.promo_name);
            textViewDescription = (TextView) itemView.findViewById(R.id.promo_shortDescription);
            textViewDate = (TextView) itemView.findViewById(R.id.promo_date);
            textViewTypeStatus = (TextView) itemView.findViewById(R.id.promo_typeStatus);
            textViewTypeStatusDate = (TextView) itemView.findViewById(R.id.promo_typeStatusDate);
        }

        //@Override
        //public String toString() {
          //  return super.toString() + " '" + mContentView.getText() + "'";
        //}
    }
}
