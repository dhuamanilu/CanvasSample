// PaintingDetailFragment.java
package com.erns.canvaspre.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.erns.canvaspre.R;
import com.erns.canvaspre.model.Painting;
import com.erns.canvaspre.utils.DataUtils;

import java.util.List;

/**
 * Fragmento para mostrar los detalles de una pintura.
 */
public class PaintingDetailFragment extends Fragment {

    private static final String ARG_PAINTING_ID = "painting_id";
    private int paintingId;
    private TextView titleTextView, artistTextView, descriptionTextView;
    private ImageView imageView;
    private ImageButton closeButton;

    public PaintingDetailFragment() {
        // Constructor vacío requerido
    }

    /**
     * Crea una nueva instancia del fragmento con el ID de la pintura.
     *
     * @param paintingId ID de la pintura.
     * @return Nueva instancia de PaintingDetailFragment.
     */
    public static PaintingDetailFragment newInstance(int paintingId) {
        PaintingDetailFragment fragment = new PaintingDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAINTING_ID, paintingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paintingId = getArguments().getInt(ARG_PAINTING_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_painting_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        titleTextView = view.findViewById(R.id.detail_painting_title);
        artistTextView = view.findViewById(R.id.detail_painting_artist);
        descriptionTextView = view.findViewById(R.id.detail_painting_description);
        imageView = view.findViewById(R.id.detail_painting_image);
        closeButton = view.findViewById(R.id.detail_close_button);

        closeButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        loadPaintingDetails();
    }

    /**
     * Carga los detalles de la pintura seleccionada.
     */
    private void loadPaintingDetails(){
        // Obtener la lista de pinturas
        List<Painting> allPaintings = DataUtils.loadPaintingsFromAssets(getContext());

        // Buscar la pintura por ID
        Painting selectedPainting = null;
        for (Painting painting : allPaintings) {
            if (painting.getId() == paintingId) {
                selectedPainting = painting;
                break;
            }
        }

        if (selectedPainting != null) {
            titleTextView.setText(selectedPainting.getTitle());
            artistTextView.setText(selectedPainting.getArtist());
            descriptionTextView.setText(selectedPainting.getDescription());

            // Cargar la imagen
            int imageResId = getResources().getIdentifier(
                    selectedPainting.getImageLink().replace(".jpg", ""),
                    "drawable",
                    getActivity().getPackageName());

            if (imageResId != 0) {
                imageView.setImageResource(imageResId);
            } else {
                imageView.setImageResource(R.drawable.mona_lisa); // Imagen placeholder
            }

        } else {
            // Manejar el caso donde la pintura no se encuentra
            titleTextView.setText("Pintura no encontrada");
            artistTextView.setText("");
            descriptionTextView.setText("");
            imageView.setImageResource(R.drawable.mona_lisa);
        }
    }
}