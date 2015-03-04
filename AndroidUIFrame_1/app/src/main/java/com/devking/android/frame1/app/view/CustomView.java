package com.devking.android.frame1.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.devking.android.frame1.app.R;
import com.devking.android.frame1.app.multiplemodel.BaseItemModel;

/**
 * Custom View
 * Created by Kings on 2015/2/15.
 */
public class CustomView extends BaseItemModel<String> {

    private TextView textView;

    public CustomView(Context context) {
        super(context);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_item_list, this, true);
        textView = (TextView)view.findViewById(R.id.text);
    }

    @Override
    public void bindView() {
        textView.setText("Item: "+viewPosition);
    }
}
