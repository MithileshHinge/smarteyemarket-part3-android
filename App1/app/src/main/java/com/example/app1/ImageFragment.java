package com.example.app1;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Home on 10-07-2017.
 */
public class ImageFragment extends Fragment {
    @Nullable
    private Context context;
    public static ArrayList<ImageModel> data = new ArrayList<>();
    private static ArrayList<File> checkEntry = new ArrayList<>();
    File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory("MagicEye"), "MagicEyePictures");

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_fragment,container,false);

        getActivity().setTitle("Pictures");
        context = getContext();
        try {
            for (File fileEntry : imageStorageDir.listFiles()) {
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println(fileEntry);
                System.out.println(fileEntry.getPath());
                if(checkEntry.contains(fileEntry)) {
                    System.out.println("continued");
                    continue;
                }
                ImageModel imageModel = new ImageModel();
                imageModel.setName(fileEntry.getName());
                imageModel.setUrl(fileEntry.getPath());
                String extension = (fileEntry.getName()).split("\\.")[1];
                checkEntry.add(fileEntry);
                if(extension.equals("jpg"))
                    data.add(imageModel);
            }
        } catch (NullPointerException n) {
            System.out.println("Picture directory empty");
            Toast.makeText(context, "No Files to Show!", Toast.LENGTH_SHORT).show();
        }

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.gallery_list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setHasFixedSize(true);

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(context, data);
        adapter.classSelector = 1;
        adapter.imageRecyclerView = recyclerView;
        recyclerView.setAdapter(adapter);
        System.out.println("............................!!!!!!!!!!!!!!!!!!!!!!!!!........................");

        return v;
    }
}
