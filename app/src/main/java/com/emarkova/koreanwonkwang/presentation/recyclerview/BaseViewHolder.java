package com.emarkova.koreanwonkwang.presentation.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.emarkova.koreanwonkwang.R;


public class BaseViewHolder extends RecyclerView.ViewHolder {
    public TextView lessonName;
    public TextView lessonTheme;
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        lessonName = (TextView)itemView.findViewById(R.id.fileName);
        lessonTheme = (TextView)itemView.findViewById(R.id.fileDesc);
    }
}
