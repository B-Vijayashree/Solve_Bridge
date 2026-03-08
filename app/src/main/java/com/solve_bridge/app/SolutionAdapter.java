package com.solve_bridge.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.ViewHolder> {

    List<String> solutionList;

    public SolutionAdapter(List<String> solutionList) {
        this.solutionList = solutionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.solution_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSolutionText.setText(solutionList.get(position));
    }

    @Override
    public int getItemCount() {
        return solutionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSolutionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSolutionText = itemView.findViewById(R.id.tvSolutionText);
        }
    }
}
