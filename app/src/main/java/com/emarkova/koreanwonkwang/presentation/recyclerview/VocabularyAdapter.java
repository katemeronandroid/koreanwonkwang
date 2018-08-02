package com.emarkova.koreanwonkwang.presentation.recyclerview;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.domain.usecases.DeleteWord;
import com.emarkova.koreanwonkwang.helpers.ConstantString;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityExercise;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityLesson;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityVocabulary;
import com.emarkova.koreanwonkwang.presentation.model.Word;

import java.util.List;
import java.util.Random;

public class VocabularyAdapter extends RecyclerView.Adapter{
    public static boolean editMode = false;
    private List<Word> mData;

    public VocabularyAdapter(List<Word> list) {
        this.mData = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.word_item, viewGroup, false);
        return new VocabularyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        VocabularyViewHolder holder = (VocabularyViewHolder)viewHolder;
        holder.koWord.setText(mData.get(i).getKoWord());
        holder.ruWord.setText(mData.get(i).getRuWord());
        holder.setId(mData.get(i).getId());
        holder.setPosition(i);
        if(editMode) {
            holder.imageEdit.setVisibility(View.VISIBLE);
            holder.imageDelete.setVisibility(View.VISIBLE);
        }
        else {
            holder.imageEdit.setVisibility(View.INVISIBLE);
            holder.imageDelete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class VocabularyViewHolder extends RecyclerView.ViewHolder {
        private TextView koWord;
        private TextView ruWord;
        private ImageView imageEdit;
        private ImageView imageDelete;
        private String id;
        private int position;
        public VocabularyViewHolder(@NonNull final View itemView) {
            super(itemView);
            koWord = (TextView)itemView.findViewById(R.id.textKo);
            ruWord = (TextView)itemView.findViewById(R.id.textRu);
            imageEdit = (ImageView)itemView.findViewById(R.id.editImage);
            imageDelete = (ImageView)itemView.findViewById(R.id.deleteImage);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(editMode) {
                        editMode = false;
                        notifyDataSetChanged();
                    }
                    else {
                        editMode = true;
                        notifyDataSetChanged();
                    }
                    //imageEdit.setVisibility(View.VISIBLE);
            /*        final AlertDialog.Builder ad = new AlertDialog.Builder(view.getContext());
                    ad.setTitle(ConstantString.ALERT_TITLE);
                    ad.setMessage(R.string.delete_word_alert);
                    ad.setPositiveButton(ConstantString.YES, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            (new DeleteWord()).deleteWord(id);
                            mData.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mData.size());
                            dialogInterface.cancel();
                        }
                    });
                    ad.setNegativeButton(ConstantString.NO, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = ad.create();
                    alert.show();*/
                    return false;
                }
            });
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
