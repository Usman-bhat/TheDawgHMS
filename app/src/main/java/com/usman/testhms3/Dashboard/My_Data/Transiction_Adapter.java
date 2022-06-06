package com.usman.testhms3.Dashboard.My_Data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usman.testhms3.R;

public class Transiction_Adapter extends RecyclerView.Adapter<Transiction_Adapter.myviewholder>
{
    Transiction_Model[] data;
    Context context;

    public Transiction_Adapter(Transiction_Model[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater =LayoutInflater.from( parent.getContext());
        View view = inflater.inflate( R.layout.transiction_single_row,parent,false );
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position)
    {
        Transiction_Model data1 = data[position];
        holder.t_type.setText( data1.getTransiction_type() );
        holder.t_amount.setText( "₹"+data1.getTransiction_amount() );
        holder.t_date.setText( data1.getTransiction_date() );
        holder.t_id.setText( "ID: "+data1.getTransiction_id() );
        Log.e("me",data1.getTransiction_type());
        if(data1.getTransiction_type().equals( "dt" )){
            Log.e( "me", "onBindViewHolder: ");
            holder.parent1.setBackgroundResource(R.color.colorDebit );
        }

    }

    @Override
    public int getItemCount() {
        return data.length;
    }



    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t_type,t_date,t_amount,t_id;
        RelativeLayout parent1;
        public myviewholder(@NonNull View itemView) {
            super( itemView );
            t_type = itemView.findViewById( R.id.t_type );
            t_date = itemView.findViewById( R.id.t_date );
            t_amount = itemView.findViewById( R.id.t_amount );
            t_id = itemView.findViewById( R.id.t_id );
            parent1 = itemView.findViewById( R.id.r11 );

        }
    }

}
