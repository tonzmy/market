package com.example.market.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.market.R;
import com.example.market.ui.me.order.allorders.AllOrderFragment;
import com.example.market.ui.me.order.cancelledorder.CancelledOrderFragment;
import com.example.market.ui.me.order.deliveringorder.DeliveringOrderFragment;
import com.example.market.ui.me.order.pendingorder.PendingOrderFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private static final int[] TAB_TITLES = new int[]{R.string.tab_all_order, R.string.tab_pending_order,
            R.string.tab_delivering_order, R.string.tab_cancelled_order};
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AllOrderFragment.newInstance();
            case 1:
                return PendingOrderFragment.newInstance();
            case 2:
                return DeliveringOrderFragment.newInstance();
            case 3:
                return CancelledOrderFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
