package com.emarkova.koreanwonkwang.presentation.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emarkova.koreanwonkwang.R;

/**
 * ViewHolder factory for opened lessons.
 */
public class BaseViewHolderFactory implements ViewHolderFactory {
    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View itemView = inflater.inflate(R.layout.item_layout, parent, false);
        return new BaseViewHolder(itemView);
    }
}
