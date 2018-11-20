package com.summary.sundy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.summary.common.base.BaseActivity;
import com.summary.common.view.itemdecoration.DividerItemDecoration;
import com.summary.sundy.R;
import com.summary.sundy.adapter.PopupStyleAdapter;
import com.summary.sundy.model.PopupModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PopupWindowActivity extends BaseActivity {
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;
    PopupStyleAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.sy_popup_window_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
          initRecycleView();
    }

    private void initRecycleView() {
        adapter=new PopupStyleAdapter(getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext));
        mRecyclerView.setAdapter(adapter);
    }

    private List<PopupModel> getData() {
        List<PopupModel> items=new ArrayList<>();
        items.add(new PopupModel("WeiboPopupWindow","微博样式的popupwiondow",WeiboPopupWindowActivity.class));

        return items;
    }

    @Override
    public void initListeners() {
        super.initListeners();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PopupModel model= (PopupModel) adapter.getItem(position);
                Intent intent=new Intent(mContext,model.activity);
                startActivity(intent);
            }
        });
    }
}
