package com.krstics.watchreminder.Decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class FragmentsItemDecorator extends RecyclerView.ItemDecoration{
    private int mSpace;

    public FragmentsItemDecorator(int space){
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace - 10;
        outRect.right = mSpace - 10;
        outRect.bottom = mSpace;

        if(parent.getChildLayoutPosition(view) == 0)
            outRect.top = mSpace;
        else
            outRect.top = 0;
    }
}
