package com.emarkova.koreanwonkwang.presentation.fragments;

import android.content.res.AssetFileDescriptor;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.databinding.FragmentAudioBinding;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.io.IOException;

public class FragmentAudio extends Fragment {
    private FragmentAudioBinding binding;
    private Button buttonPlay;
    private MediaPlayer mediaPlayer;
    private Exercise exercise;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audio, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            exercise = bundle.getParcelable("exercise");
            binding.setExercise(exercise);
            buttonPlay = (Button) view.findViewById(R.id.buttonPlayAudio);
            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAudio();
                    playAudio(exercise.getAudio());
                }
            });
        }
    }

    private void playAudio(String fileName) {
        String path = "audio/"+fileName+".mp3";
        try {
            AssetFileDescriptor descriptor = getActivity().getAssets().openFd(path);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void stopAudio() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
