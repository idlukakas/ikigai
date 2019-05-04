package br.com.lukakas.find_figure;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.lukakas.find_figure.R;

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



