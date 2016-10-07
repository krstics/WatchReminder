package com.krstics.watchreminder.Helpers;

import java.util.List;

public class ShowNameWrapper {

    private boolean mExpanded;
    private ShowNameListItem showNameListItem;

    public ShowNameWrapper(ShowNameListItem parentListItem) {
        showNameListItem = parentListItem;
        mExpanded = false;
    }


    public ShowNameListItem getParentListItem() {
        return showNameListItem;
    }

    public void setShowNameListItem(ShowNameListItem item) {
        showNameListItem = item;
    }

    public boolean isExpanded() {
        return mExpanded;
    }
    public void setExpanded(boolean expanded) {
        mExpanded = expanded;
    }

    public boolean isInitiallyExpanded() {
        return showNameListItem.isInitiallyExpanded();
    }

    public List<?> getChildItemList() {
        return showNameListItem.getChildItemList();
    }
}
