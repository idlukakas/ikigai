package br.com.lukakas.find_figure;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainActivityFragment extends Fragment {

    private DoodleView doodleView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View raiz =
                inflater.inflate (
                        R.layout.fragment_main,
                        container,
                        false
                );
        doodleView = raiz.findViewById(R.id.doodleView);
        return raiz;
    }

}



