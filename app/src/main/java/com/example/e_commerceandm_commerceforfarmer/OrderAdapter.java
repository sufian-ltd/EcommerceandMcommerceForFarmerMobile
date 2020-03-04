package com.example.e_commerceandm_commerceforfarmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {
    Context context;
    List<Order> list;
    public OrderAdapter(Context context, List<Order> list) {
        super(context, R.layout.order_list_item, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        TextView t1, t2,t3,t4;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
            holder = new ViewHolder();
            holder.t1 = convertView.findViewById(R.id.tvProName);
            holder.t2 = convertView.findViewById(R.id.tvCost);
            holder.t3 = convertView.findViewById(R.id.tvOrderDate);
            holder.t4=convertView.findViewById(R.id.tvDeliveryDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.t1.setText("Product Name: "+list.get(position).getProductName());
        holder.t2.setText("Total Cost: "+list.get(position).getCost()+"TK | Quantity: "+list.get(position).getQtn());
        holder.t3.setText("Order Date: "+list.get(position).getOrderDate());
        holder.t4.setText("Delivery Date: "+list.get(position).getDeliveryDate());
        return convertView;
    }


}
