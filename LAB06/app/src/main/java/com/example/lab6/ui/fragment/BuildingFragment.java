package com.example.lab6.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab6.R;
import com.example.lab6.model.RoomData;
import com.example.lab6.ui.view.CustomPlanView;
import com.example.lab6.ui.viewmodel.BuildingViewModel;

import java.io.InputStream;
import java.util.List;

public class BuildingFragment extends Fragment {

    private BuildingViewModel viewModel;
    private CustomPlanView planView;

    public BuildingFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        planView = new CustomPlanView(requireContext());
        return planView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(BuildingViewModel.class);

        viewModel.rooms.observe(getViewLifecycleOwner(), new Observer<List<RoomData>>() {
            @Override
            public void onChanged(List<RoomData> roomData) {
                planView.setRooms(roomData);
            }
        });

        planView.setOnRoomClickListener(new CustomPlanView.OnRoomClickListener() {
            @Override
            public void onRoomClick(RoomData room) {
                viewModel.selectRoom(room);
                showRoomDialog(room);
            }
        });

        viewModel.loadRoomsFromAssets();
    }

    private void showRoomDialog(RoomData room) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View popup = inflater.inflate(R.layout.popup_room, null);
        ImageView iv = popup.findViewById(R.id.popup_image);
        TextView title = popup.findViewById(R.id.popup_title);
        TextView desc = popup.findViewById(R.id.popup_description);

        title.setText(room.getName());
        desc.setText(room.getDescription());

        String imageName = room.getImage();
        if (imageName != null && !imageName.isEmpty()) {
            try {
                InputStream is = requireContext().getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(is, null);
                is.close();
                iv.setImageDrawable(d);
                iv.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                iv.setVisibility(View.GONE);
            }
        } else {
            iv.setVisibility(View.GONE);
        }

        new AlertDialog.Builder(requireContext())
                .setView(popup)
                .setPositiveButton("Cerrar", null)
                .show();
    }
}
