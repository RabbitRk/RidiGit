package rk.rabbitt.ridi.Adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rk.rabbitt.ridi.R;


/**
 * Created by Rabbitt on 06,February,2019
 */
public class job_alert_adapter extends RecyclerView.Adapter<job_alert_adapter.holder> {

    private List<recycleAdapter> dataModelArrayList;

    public job_alert_adapter(List<recycleAdapter> datamodelArray) {
        this.dataModelArrayList = datamodelArray;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.job_alert_item, null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        recycleAdapter dataModel = dataModelArrayList.get(i);
        Log.i("recyclerre", dataModelArrayList.get(i).toString());
        holder.book_id.setText(dataModel.getBook_id());
        holder.v_type.setText(dataModel.getV_type());
        holder.travel_type.setText(dataModel.getTravel_type());
        holder.ori.setText(dataModel.getOrigin());
        holder.dest.setText(dataModel.getDestination());
        holder.dateof.setText(dataModel.getTimeat());
        holder.package_id.setText(dataModel.getPackage_id());
    }

    @Override
    public int getItemCount() {
        Log.i("adapterre", String.valueOf(dataModelArrayList.size()));
        return dataModelArrayList.size();
    }

    class holder extends RecyclerView.ViewHolder{

        TextView book_id, travel_type, v_type, dateof, ori, dest, package_id;

        holder(@NonNull View itemView) {
            super(itemView);

            book_id = itemView.findViewById(R.id.book_id);
            travel_type = itemView.findViewById(R.id.travel_type);
            v_type = itemView.findViewById(R.id.v_type);
            dateof = itemView.findViewById(R.id.dateof);
            ori = itemView.findViewById(R.id.ori);
            dest = itemView.findViewById(R.id.dest);
            package_id = itemView.findViewById(R.id.pakage);
        }
    }
}
