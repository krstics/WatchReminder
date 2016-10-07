package com.krstics.watchreminder.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.krstics.watchreminder.Listeners.ShowNameExpandCollapseListener;

public class ShowNameHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private boolean mExpanded;
    private ShowNameExpandCollapseListener showNameExpandCollapseListener;

    public interface ShowNameExpandCollapseListener {

        void onParentListItemExpanded(int position);

        void onParentListItemCollapsed(int position);
    }


    public ShowNameHolder(View itemView) {
        super(itemView);
        mExpanded = false;
    }

    public void setMainItemClickToExpand() {
        itemView.setOnClickListener(this);
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(boolean expanded) {
        mExpanded = expanded;
    }

    public void onExpansionToggled(boolean expanded) {

    }

    public ShowNameExpandCollapseListener getShowNameExpandCollapseListener(){
        return showNameExpandCollapseListener;
    }

    public void setShowNameExpandCollapseListener(ShowNameExpandCollapseListener listener){
        showNameExpandCollapseListener = listener;
    }

    @Override
    public void onClick(View view) {
        if(mExpanded)
            collapseView();
        else
            expandeView();
    }

    public boolean shouldItemViewClickToggleExpansion(){
        return true;
    }

    protected void expandeView(){
        setExpanded(true);
        onExpansionToggled(false);

        if(showNameExpandCollapseListener != null){
            showNameExpandCollapseListener.onParentListItemExpanded(getAdapterPosition());
        }
    }

    protected void collapseView() {
        setExpanded(false);
        onExpansionToggled(true);

        if (showNameExpandCollapseListener != null) {
            showNameExpandCollapseListener.onParentListItemCollapsed(getAdapterPosition());
        }
    }
}
