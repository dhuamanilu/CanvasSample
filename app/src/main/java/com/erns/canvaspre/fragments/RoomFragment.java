// RoomFragment.java
package com.erns.canvaspre.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.erns.canvaspre.R;
import com.erns.canvaspre.listeners.RoomViewListener;

/**
 * Fragmento que representa una habitación con pinturas.
 */
public class RoomFragment extends Fragment {

    private static final String ARG_ROOM_ID = "room_id";
    private int roomId;

    private RoomViewListener roomViewListener;

    public RoomFragment() {
        // Constructor vacío requerido
    }

    /**
     * Crea una nueva instancia del fragmento con el ID de la habitación.
     *
     * @param roomId ID de la habitación.
     * @return Nueva instancia de RoomFragment.
     */
    public static RoomFragment newInstance(int roomId) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ROOM_ID, roomId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RoomViewListener) {
            roomViewListener = (RoomViewListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debe implementar RoomViewListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomId = getArguments().getInt(ARG_ROOM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        // Ejemplo: Si hay un botón para cerrar el fragmento
        Button closeButton = view.findViewById(R.id.detail_close_button);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> {
                if (roomViewListener != null) {
                    roomViewListener.closePictureFragment();
                }
            });
        }

        // Otras configuraciones del fragmento
    }

    @Override
    public void onDetach() {
        super.onDetach();
        roomViewListener = null;
    }
}
