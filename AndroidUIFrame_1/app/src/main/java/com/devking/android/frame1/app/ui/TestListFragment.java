package com.devking.android.frame1.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import com.devking.android.frame1.app.R;
import com.devking.android.frame1.app.multiplemodel.*;
import com.devking.android.frame1.app.view.CustomView;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * This is Fragment in Pager
 * Created by Kings on 2015/2/15.
 */
public class TestListFragment extends Fragment {

    private ListView listView;
    private ModelListAdapter modelListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        modelListAdapter = new ModelListAdapter(getActivity(), getModelManager());
        listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(modelListAdapter);

        modelListAdapter.setList(getList());
        modelListAdapter.notifyDataSetChanged();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (listView.getChildCount() > 0) {
                    if (listView.getChildAt(0).getTop() >= 0) {
                        EventBus.getDefault().post(true);
                    } else {
                        EventBus.getDefault().post(false);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildCount() > 0) {
                    if (listView.getChildAt(0).getTop() >= 0) {
                        EventBus.getDefault().post(true);
                    } else {
                        EventBus.getDefault().post(false);
                    }
                }
            }
        });
    }

    public ModelManager getModelManager() {
        return ModelManagerBuilder.begin().addModel(CustomView.class,true).build(ModelManager.class);
    }

    public List<ItemEntity> getList() {
        List<ItemEntity> resultList = new ArrayList<ItemEntity>();
        for (int i = 0; i < 20; i++) {
            ItemEntityCreator.create("Test").setModelView(CustomView.class).attach(resultList);
        }
        return resultList;
    }
}
