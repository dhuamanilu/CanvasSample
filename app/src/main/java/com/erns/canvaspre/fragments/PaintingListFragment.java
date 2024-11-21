// PaintingListFragment.java
package com.erns.canvaspre.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erns.canvaspre.R;
import com.erns.canvaspre.adapters.PaintingAdapter;
import com.erns.canvaspre.model.Painting;
import com.erns.canvaspre.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento para mostrar la lista de pinturas.
 */
public class PaintingListFragment extends Fragment implements PaintingAdapter.OnPaintingClickListener {

    private RecyclerView recyclerView;
    private PaintingAdapter paintingAdapter;
    private List<Painting> paintingList; // Lista completa
    private List<Painting> filteredPaintingList; // Lista filtrada

    private EditText searchBar;
    private RadioGroup filterOptions;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_painting_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.painting_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBar = view.findViewById(R.id.painting_search_bar);
        filterOptions = view.findViewById(R.id.painting_filter_options);
        emptyView = view.findViewById(R.id.painting_empty_view);

        paintingList = new ArrayList<>();
        filteredPaintingList = new ArrayList<>();

        // Inicializar el adaptador con la lista filtrada (inicialmente vacía)
        paintingAdapter = new PaintingAdapter(getContext(), filteredPaintingList, this);
        recyclerView.setAdapter(paintingAdapter);

        // Cargar las pinturas desde el archivo Pictures.txt
        loadPaintingsFromFile();

        // Configurar el listener para el cambio de texto en la barra de búsqueda
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar
            }
        });

        // Configurar el listener para el cambio de opción en el RadioGroup
        filterOptions.setOnCheckedChangeListener((group, checkedId) -> {
            filter(searchBar.getText().toString());
        });

        updateEmptyView();
    }

    /**
     * Carga las pinturas desde el archivo Pictures.txt.
     */
    private void loadPaintingsFromFile() {
        // Leer y parsear los datos
        paintingList = DataUtils.loadPaintingsFromAssets(getContext());

        // Inicializar la lista filtrada
        filteredPaintingList.clear();
        filteredPaintingList.addAll(paintingList);

        // Notificar al adaptador
        if (paintingAdapter != null) {
            paintingAdapter.notifyDataSetChanged();
        }

        updateEmptyView();
    }

    /**
     * Filtra la lista de pinturas basada en el texto de búsqueda y la opción seleccionada.
     *
     * @param text Texto ingresado en la barra de búsqueda.
     */
    private void filter(String text) {
        String lowerText = text.toLowerCase();
        filteredPaintingList.clear();

        int checkedId = filterOptions.getCheckedRadioButtonId();

        for (Painting painting : paintingList) {
            boolean matches = false;

            if (checkedId == R.id.rb_painting_title) {
                matches = painting.getTitle().toLowerCase().contains(lowerText);
            } else if (checkedId == R.id.rb_painting_artist) {
                matches = painting.getArtist().toLowerCase().contains(lowerText);
            } else if (checkedId == R.id.rb_painting_description) {
                matches = painting.getDescription().toLowerCase().contains(lowerText);
            }

            if (matches) {
                filteredPaintingList.add(painting);
            }
        }

        paintingAdapter.notifyDataSetChanged();
        updateEmptyView();
    }

    /**
     * Actualiza la visibilidad del mensaje de "No se encontraron pinturas".
     */
    private void updateEmptyView() {
        if (filteredPaintingList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * Maneja el evento de clic en una pintura.
     *
     * @param position Posición del ítem clicado.
     */
    @Override
    public void onPaintingClick(int position) {
        Painting selectedPainting = filteredPaintingList.get(position);
        int paintingId = selectedPainting.getId();

        // Navegar al fragmento de detalle, pasando el paintingId
        PaintingDetailFragment detailFragment = PaintingDetailFragment.newInstance(paintingId);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
