// PaintingAdapter.java
package com.erns.canvaspre.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.erns.canvaspre.R;
import com.erns.canvaspre.model.Painting;

import java.util.List;

/**
 * Adaptador para el RecyclerView de pinturas.
 */
public class PaintingAdapter extends RecyclerView.Adapter<PaintingAdapter.PaintingViewHolder> {

    private List<Painting> paintingList;
    private OnPaintingClickListener onPaintingClickListener;
    private Context context;

    /**
     * Interfaz para manejar los eventos de clic en los ítems.
     */
    public interface OnPaintingClickListener {
        void onPaintingClick(int position);
    }

    /**
     * Constructor del adaptador.
     *
     * @param context   Contexto de la aplicación.
     * @param paintingList Lista de pinturas.
     * @param listener  Listener para los eventos de clic.
     */
    public PaintingAdapter(Context context, List<Painting> paintingList, OnPaintingClickListener listener) {
        this.context = context;
        this.paintingList = paintingList;
        this.onPaintingClickListener = listener;
    }

    @NonNull
    @Override
    public PaintingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_painting, parent, false);
        return new PaintingViewHolder(view, onPaintingClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PaintingViewHolder holder, int position) {
        Painting painting = paintingList.get(position);
        holder.titleTextView.setText(painting.getTitle());
        holder.artistTextView.setText(painting.getArtist());
        holder.descriptionTextView.setText(painting.getDescription());

        // Asumiendo que 'imageLink' es el nombre del recurso drawable sin la extensión
        int imageResId = context.getResources().getIdentifier(
                painting.getImageLink().replace(".jpg", ""),
                "drawable",
                context.getPackageName());

        if (imageResId != 0) {
            holder.imageView.setImageResource(imageResId);
        } else {
            holder.imageView.setImageResource(R.drawable.mona_lisa); // Imagen placeholder
        }
    }

    @Override
    public int getItemCount() {
        return paintingList.size();
    }

    /**
     * Actualiza la lista de pinturas y notifica los cambios.
     *
     * @param newList Nueva lista de pinturas.
     */
    public void setPaintingList(List<Painting> newList) {
        paintingList = newList;
        notifyDataSetChanged();
    }

    /**
     * Clase ViewHolder para el adaptador.
     */
    public static class PaintingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView, artistTextView, descriptionTextView;
        ImageView imageView;
        OnPaintingClickListener onPaintingClickListener;

        public PaintingViewHolder(@NonNull View itemView, OnPaintingClickListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.painting_title);
            artistTextView = itemView.findViewById(R.id.painting_artist);
            descriptionTextView = itemView.findViewById(R.id.painting_description);
            imageView = itemView.findViewById(R.id.painting_image);
            this.onPaintingClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onPaintingClickListener != null) {
                onPaintingClickListener.onPaintingClick(getAdapterPosition());
            }
        }
    }
}
