package io.github.wulkanowy.ui.main.timetable.tab;

import java.util.List;

import io.github.wulkanowy.ui.base.BaseContract;

public interface TimetableTabContract {

    interface View extends BaseContract.View {

        void updateAdapterList(List<TimetableHeader> headerItems);

        void expandItem(int item);

        void onRefreshSuccess();

        void hideRefreshingBar();

        void showNoItem(boolean show);

        void showProgressBar(boolean show);

        void setFreeWeekName(String text);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void onFragmentActivated(boolean isSelected);

        void setArgumentDate(String date);

        void onRefresh();
    }
}
