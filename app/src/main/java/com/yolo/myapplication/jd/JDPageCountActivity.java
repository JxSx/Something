package com.yolo.myapplication.jd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yolo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class JDPageCountActivity extends Activity {

    private static final int PAGE_PER = 20;//每页显示数量
    private static final int COUNT = 100;//总数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jd_page_count);

        final TextView tvPageCount = (TextView) findViewById(R.id.tv_page_count);

        tvPageCount.post(new Runnable() {
            @Override
            public void run() {
                AnimUtils.hide(tvPageCount);
            }
        });

        final AnimListenerBuilder builder = new AnimListenerBuilder();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int page;
                    if (lastVisiblePosition % PAGE_PER == 0) {
                        page = lastVisiblePosition / PAGE_PER;
                    } else {
                        page = lastVisiblePosition / PAGE_PER + 1;
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                builder.setNewState(newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && builder.isAnimFinish()) {
                    //如果是IDLE状态，并且显示动画执行完毕，再执行隐藏动画，避免出现动画闪烁
                    //如果快速简短滑动，可能导致出现IDLE状态，但是动画未执行完成。因此无法执行隐藏动画。所以保存当前newState，在onAnimationEnd中增加判断。
                    AnimUtils.hide(tvPageCount);
                } else if (tvPageCount.getVisibility() != View.VISIBLE) {
                    AnimUtils.show(tvPageCount, builder.build());
                }
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Item> {

        private List<String> stringList = new ArrayList();

        MyAdapter() {
            for (int i = 0; i < COUNT; i++) {
                stringList.add("数据" + i);
            }
        }

        @Override
        public Item onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = View.inflate(parent.getContext(), android.R.layout.simple_list_item_1, null);
            return new Item(root);
        }

        @Override
        public void onBindViewHolder(Item holder, int position) {
            holder.tv.setText(stringList.get(position));
        }

        @Override
        public int getItemCount() {
            return COUNT;
        }

        class Item extends RecyclerView.ViewHolder {

            TextView tv;

            public Item(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
