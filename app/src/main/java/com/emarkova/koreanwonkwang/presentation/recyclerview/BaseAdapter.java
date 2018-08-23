package com.emarkova.koreanwonkwang.presentation.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.helpers.ConstantString;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;

import java.util.List;

public class BaseAdapter extends RecyclerView.Adapter {
    List<Lesson> mList;

    SparseArray<ViewHolderFactory> mFactoryMap;
    public BaseAdapter(List<Lesson> list) {
        this.mList = list;
        mFactoryMap = new SparseArray<>();
        mFactoryMap.put(1, new BaseViewHolderFactory());
        mFactoryMap.put(2, new ClosedViewHolderFactory());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ViewHolderFactory factory;
        if(viewType == 1)
            factory = mFactoryMap.get(1);
        else
            factory = mFactoryMap.get(2);
       LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
       return factory.createViewHolder(viewGroup, inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == 1) {
            ((BaseViewHolder)viewHolder).lessonName.setText(R.string.lesson + " " + mList.get(position).getNumber());
            //((BaseViewHolder)viewHolder).lessonName.setTextColor(0x00000000);
            ((BaseViewHolder)viewHolder).lessonTheme.setText(String.valueOf(mList.get(position).getPer()) + "%");
        }
        else {
            ((ClosedViewHolder)viewHolder).lessonName.setText(R.string.lesson + " " + mList.get(position).getNumber());
            ((ClosedViewHolder)viewHolder).lessonTheme.setText(R.string.lesson_closed);
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).isOpen())
            return 1;
        else
            return 2;
    }
}
