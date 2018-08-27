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
import android.widget.TextView;

import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.databinding.FragmentAudioBinding;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.io.IOException;

public class FragmentAudio extends Fragment {
    private static final String EXERCISE_KEY = "exercise";
    private FragmentAudioBinding binding;
    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private Exercise exercise;
    private boolean testMode = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audio, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            exercise = bundle.getParcelable(EXERCISE_KEY);
            binding.setExercise(exercise);
            Button buttonPlay = view.findViewById(R.id.buttonPlayAudio);
            buttonPlay.setOnClickListener(view1 -> {
                stopAudio();
                playAudio(exercise.getAudio());
            });
        }
    }

    private void playAudio(String fileName) {
        String path = "audio/"+fileName+".mp3";
        try {
            AssetFileDescriptor descriptor = getContext().getAssets().openFd(path);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void stopAudio() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}
