package com.example.confidant.Service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.confidant.Domain.Secrete;
import com.example.confidant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepakr on 3/29/2016.
 */
public class MyAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Secrete> beanList;
    private LayoutInflater inflater;
    List<Secrete> mStringFilterList;
    ValueFilter valueFilter;


    public MyAdapter(Context context, List<Secrete> beanList) {
        this.context = context;
        this.beanList = beanList;
        mStringFilterList = beanList;
    }


    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int i) {
        return beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtName = (TextView) view.findViewById(R.id.list_text1);
        TextView txtDesc = (TextView) view.findViewById(R.id.list_text2);

        Secrete bean = beanList.get(i);
        String name = bean.getSecreteName();
        String desc = bean.getUsername();

        txtName.setText(name);
        txtDesc.setText(desc);
        return view;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Secrete> filterList = new ArrayList<Secrete>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getSecreteName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        Secrete bean = new Secrete(mStringFilterList.get(i)
                                .getSecreteName(),"","", mStringFilterList.get(i)
                                .getUsername());
                        filterList.add(bean);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            beanList = (ArrayList<Secrete>) results.values;
            notifyDataSetChanged();
        }

    }
}