package com.krstics.watchreminder.Helpers;


import java.util.ArrayList;
import java.util.List;

public class ExpandableRecyclerViewAdapterHelper {
    public static List<Object> generateParentChildItemList(List<? extends ShowNameListItem> showNameItemList) {
        List<Object> showNameWrapperList = new ArrayList<>();
        ShowNameListItem showNameListItem;
        ShowNameWrapper showNameWrapper;

        int parentListItemCount = showNameItemList.size();
        for (int i = 0; i < parentListItemCount; i++) {
            showNameListItem = showNameItemList.get(i);
            showNameWrapper = new ShowNameWrapper(showNameListItem);
            showNameWrapperList.add(showNameWrapper);

            if (showNameWrapper.isInitiallyExpanded()) {
                showNameWrapper.setExpanded(true);

                int childListItemCount = showNameWrapper.getChildItemList().size();
                for (int j = 0; j < childListItemCount; j++) {
                    showNameWrapperList.add(showNameWrapper.getChildItemList().get(j));
                }
            }
        }

        return showNameWrapperList;
    }
}
