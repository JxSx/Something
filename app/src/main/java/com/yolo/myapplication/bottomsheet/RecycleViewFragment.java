package com.yolo.myapplication.bottomsheet;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yolo.myapplication.R;


/**
 * @author: jiaxin
 * @date: 2017-01-16 17:41
 */

public class RecycleViewFragment extends BaseBottomSheetFragment {

    public static final String TAG = RecycleViewFragment.class.getSimpleName();

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main_industry_list;
    }

    @Override
    protected void initView() {
        recyclerView1 = (RecyclerView) rootView.findViewById(R.id.recyclerView1);
        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.recyclerView2);
        initRecyclerView(recyclerView1);
        initRecyclerView(recyclerView2);

        recyclerView1.setAdapter(new MyAdapter());
        recyclerView2.setAdapter(new MyAdapter());
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public class MyAdapter extends RecyclerView.Adapter<Item> {

        @Override
        public Item onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Item(View.inflate(parent.getContext(), android.R.layout.simple_list_item_1, null));
        }

        @Override
        public void onBindViewHolder(Item holder, int position) {
            holder.tv.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    class Item extends RecyclerView.ViewHolder {

        TextView tv;

        public Item(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}
