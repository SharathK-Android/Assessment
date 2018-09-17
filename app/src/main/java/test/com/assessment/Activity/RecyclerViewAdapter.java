package test.com.assessment.Activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import test.com.assessment.Class.DataModel;
import test.com.assessment.Interface.OnItemClickListener;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {



        private Context mContext;
        private ArrayList<DataModel> arrData;
        private OnItemClickListener onItemClickListener;

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView name,city,salary,designation;
            public ImageView imgGraph;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.text_name);
                city = (TextView) view.findViewById(R.id.text_city);
                designation = (TextView) view.findViewById(R.id.text_designation);
                imgGraph=(ImageView)view.findViewById(R.id.img_graph);
            }
        }

        public RecyclerViewAdapter(Context mContext, ArrayList<DataModel> arrData,OnItemClickListener onItemClickListener) {
            this.mContext = mContext;
            this.arrData = arrData;
            this.onItemClickListener=onItemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_child_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.name.setText(arrData.get(position).getName());
            holder.city.setText(arrData.get(position).getCity());
            holder.designation.setText(arrData.get(position).getDesignation());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(position,holder.itemView);
                }
            });
            holder.city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnLocationClick(position);
                }
            });


        }

    @Override
    public int getItemCount() {
        return arrData.size();
    }


}
