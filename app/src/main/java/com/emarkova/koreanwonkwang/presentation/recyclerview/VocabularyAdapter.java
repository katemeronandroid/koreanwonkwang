package com.emarkova.koreanwonkwang.presentation.recyclerview;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPVocabularyView;
import com.emarkova.koreanwonkwang.presentation.MVP.VocabularyPresenterImp;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityVocabulary;
import com.emarkova.koreanwonkwang.presentation.model.Word;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter  implements MVPVocabularyView{
    public static boolean editMode = false;
    private final List<Word> mData;
    private final VocabularyPresenterImp presenter;

    public VocabularyAdapter(List<Word> list, VocabularyPresenterImp presenter) {
        this.mData = list;
        this.presenter = presenter;
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
        holder.setWord(mData.get(i));
        holder.setPosition(i);
        if(editMode) {
            holder.imageEdit.setVisibility(View.VISIBLE);
            holder.imageDelete.setVisibility(View.VISIBLE);
            ActivityVocabulary.setFABVisibility(View.GONE);
        }
        else {
            holder.imageEdit.setVisibility(View.GONE);
            holder.imageDelete.setVisibility(View.GONE);
            ActivityVocabulary.setFABVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void setWordsList(List<Word> list) {}

    private class VocabularyViewHolder extends RecyclerView.ViewHolder {
        private TextView koWord;
        private TextView ruWord;
        private ImageView imageEdit;
        private ImageView imageDelete;
        private Word word;
        private int position;
        VocabularyViewHolder(@NonNull final View itemView) {
            super(itemView);
            koWord = itemView.findViewById(R.id.textKo);
            ruWord = itemView.findViewById(R.id.textRu);
            imageEdit = itemView.findViewById(R.id.editImage);
            imageDelete = itemView.findViewById(R.id.deleteImage);
            initListeners();
            itemView.setOnLongClickListener((View view) -> {
                if(editMode) {
                    editMode = false;
                    notifyDataSetChanged();
                }
                else {
                    editMode = true;
                    notifyDataSetChanged();
                }
                return false;
            });
        }

        private void initListeners() {
            imageDelete.setOnClickListener(view -> {
                new AlertDialog.Builder(view.getContext())
                .setTitle(R.string.alert)
                .setMessage(R.string.delete_word_alert)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    presenter.deleteWord(word.getId());
                    mData.remove(position);
                    notifyItemRemoved(position);
                    dialogInterface.cancel();
                })
                .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel()).show();
            });
            imageEdit.setOnClickListener(view -> {
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View layout = inflater.inflate(R.layout.dialog_add_word, null);
                final EditText koWord = layout.findViewById(R.id.koreanInputDialog);
                final EditText ruWord = layout.findViewById(R.id.russianInputDialog);
                koWord.setText(word.getKoWord());
                ruWord.setText(word.getRuWord());
                new AlertDialog.Builder(view.getContext())
                .setView(layout)
                .setPositiveButton(R.string.save, (dialogInterface, i) -> {
                    word.setKoWord(koWord.getText().toString());
                    word.setRuWord(ruWord.getText().toString());
                    notifyItemChanged(position);
                    presenter.updateWord(word);
                    dialogInterface.cancel();
                }).show();
            });
        }

        void setPosition(int position) {
            this.position = position;
        }

        public void setWord(Word word) {
            this.word = word;
        }
    }
}
