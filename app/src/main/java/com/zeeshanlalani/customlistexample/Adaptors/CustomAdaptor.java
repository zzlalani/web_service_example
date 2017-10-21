package com.zeeshanlalani.customlistexample.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zeeshanlalani.customlistexample.MainActivity;
import com.zeeshanlalani.customlistexample.Person;
import com.zeeshanlalani.customlistexample.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/21/2017.
 */

public class CustomAdaptor extends BaseAdapter {

    Context context;
    String[] student_names;
    String[] ids;
    LayoutInflater inflater;
    List<Person> list;

    public CustomAdaptor(Context _context, List<Person> contacts) {
        context = _context;
        list = contacts;

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView;
        if ( position == 0) {
            rowView = inflater.inflate(R.layout.list_header, null);
        } else {
            rowView = inflater.inflate(R.layout.list_view, null);
            TextView txtName = (TextView) rowView.findViewById(R.id.student_name);
            TextView txtId = (TextView) rowView.findViewById(R.id.student_id);

            Person p = list.get(position-1);
            txtName.setText(p.name);
            txtId.setText(p.id);
        }

        return rowView;
    }
}
