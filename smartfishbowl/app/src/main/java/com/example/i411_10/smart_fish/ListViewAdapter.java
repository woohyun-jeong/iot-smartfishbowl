package com.example.i411_10.smart_fish;

/**
 * Created by I411-10 on 2016-07-11.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater; //레이아웃 동적으로 교체
    private List<Fish> fishlist = null;
    private ArrayList<Fish> arraylist;
    //리스트 뷰 초기화 (값 전체 등록)
    public ListViewAdapter(Context context, List<Fish> fishlist) {
    //현재 화면의 리스트 뷰를 초기화 -> context
        mContext = context;
    //리스트 뷰를 현재 작업을 위해 값들을 이전 시키고
        this.fishlist = fishlist;
    //동적으로 화면의 뷰 전환
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(fishlist);
    }
    //동적으로 보여줄 것들
    public class ViewHolder {
        TextView fish_temp;
        TextView fish_name;
    }

    @Override
    public int getCount() {
        return fishlist.size();
    }

    @Override
    public Fish getItem(int id) {
        return fishlist.get(id);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    public View getView(final int id, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.fish_temp = (TextView) view.findViewById(R.id.fish_temp);
            holder.fish_name = (TextView) view.findViewById(R.id.fish_name);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.fish_temp.setText(fishlist.get(id).getTemp());
        holder.fish_name.setText(fishlist.get(id).getName());


        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, TabView.class);
                // Pass all data rank
                intent.putExtra("fish_temp", (fishlist.get(id).getTemp()));
                // Pass all data country
                intent.putExtra("fish_name",(fishlist.get(id).getName()));
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }
    //자동완성 필터
    // Filter Class
    public void filter(String charText) {
    //리스트 값을 다 지우고 입력한 부분
        fishlist.clear();
        if (charText.length() == 0) {
            fishlist.addAll(arraylist);
        }
        else
        {
            for (Fish f : arraylist)
            {
                if (f.getName().contains(charText))
                {
                    fishlist.add(f);
                }
            }
        }
        notifyDataSetChanged(); //데이터 갱신
    }

}