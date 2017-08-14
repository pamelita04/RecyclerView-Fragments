/**package com.example.pam.listsamsung;

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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


 * Created by Pam on 7/8/2017.


public class Adapter extends RecyclerView.Adapter<Adapter.PromotionsViewHolder> {

    List<Promotion> listPromotions;

    public Adapter(List<Promotion> listPromotions){
        this.listPromotions = listPromotions;
    }

    @Override
    public PromotionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        PromotionsViewHolder holder = new PromotionsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PromotionsViewHolder holder, int position) {

        Promotion promo = listPromotions.get(position);

        holder.textViewTitle.setText(promo.getName());
        holder.textViewDescription.setText(promo.getShortDescription());

        Context context = holder.imageView.getContext();
        ImageView imageView = holder.imageView;
        Glide.with(context)
                .load(promo.getImage())
                .crossFade()
                .fitCenter()
                .placeholder(R.drawable.card03)
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

        return listPromotions.size();
    }


    public static class PromotionsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDescription, textViewDate, textViewTypeStatus,
                textViewTypeStatusDate;
        ImageView imageView;

        public PromotionsViewHolder (View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.promo_image);
            textViewTitle = (TextView) itemView.findViewById(R.id.promo_name);
            textViewDescription = (TextView) itemView.findViewById(R.id.promo_shortDescription);
            textViewDate = (TextView) itemView.findViewById(R.id.promo_date);
            textViewTypeStatus = (TextView) itemView.findViewById(R.id.promo_typeStatus);
            textViewTypeStatusDate = (TextView) itemView.findViewById(R.id.promo_typeStatusDate);
        }
    }
}
**/
