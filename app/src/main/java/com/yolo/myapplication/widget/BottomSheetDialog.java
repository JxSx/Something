package com.yolo.myapplication.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yolo.myapplication.R;

/**
 * @author: jiaxin
 * @date: 2017-04-25 14:27
 */

public class BottomSheetDialog extends AppCompatDialogFragment {

    public static BottomSheetDialog newInstance() {

        Bundle args = new Bundle();

        BottomSheetDialog fragment = new BottomSheetDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.base_dialog_fragment, null);
        RecyclerView recyclerView = (RecyclerView) customView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);//除去上下滑动的阴影


        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.BottomSheetDialogStyle)
                .setView(customView)
                .create();
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);

        return dialog;
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);
            return new ItemHolder(item);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.setText("" + position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class ItemHolder extends RecyclerView.ViewHolder {

            private TextView button;

            public ItemHolder(View itemView) {
                super(itemView);

                button = (TextView) itemView.findViewById(R.id.btn);

            }

            public void setText(String value) {
                button.setText(value);
            }
        }
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("这是标题")
                .setMessage("你确定这是内容吗？")
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null);


        AlertDialog dialog = builder.create();

        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);

        return dialog;
    }*/
}
