package com.swdm.mp.smile;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/*
the History fragment shows user's album named Smihealing. (maybe)

 */
public class HistoryFragment extends Fragment {


    View rootView;
    private final String image_titles[] = {
            "Img1",
            "Img2",
            "Img3",
            "Img4",
            "Img5",
            "Img6",
            "Img7",
            "Img8",
            "Img9",
            "Img10",
            "Img11",
            "Img12",
            "Img13",
    };

    private final Integer image_ids[] = {
            R.drawable.ic_album,
            R.drawable.ic_camera,
            R.drawable.ic_history,
            R.drawable.ic_hope,
            R.drawable.ic_wisdom,
            R.drawable.ic_star,
            R.drawable.ic_money,
            R.drawable.ic_letter,
            R.drawable.ic_life,
            R.drawable.ic_letter,
            R.drawable.ic_life,
            R.drawable.ic_money,
            R.drawable.ic_wisdom,
    };
    public HistoryFragment() {
        // Required empty public constructor
    }

    int notificationID=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        //---look up the notification manager service--
        NotificationManager nm = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        //---cancel the notification that we started--
//        nm.cancel(getActivity().getIntent().getExtras().getInt("notificationID"));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.history_fragment, container, false);

//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Smihealing");


        // Inflate the layout for this fragment
        return rootView;
    }

    public void showGallery(){
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getActivity(), createLists);
        recyclerView.setAdapter(adapter);


    }
    public class CreateList {

        private String image_title;
        private Integer image_id;
        private String image_Location;
        public String getImage_title() {
            return image_title;
        }

        public void setImage_title(String android_version_name) {
            this.image_title = android_version_name;
        }

        public Integer getImage_ID() {
            return image_id;
        }


        public void setImage_ID(Integer android_image_url) {
            this.image_id = android_image_url;
        }
        public void setImage_Location(String android_image_location)
        {
            this.image_Location = android_image_location;

        }

        public String getImage_Location(){
            return image_Location;

        }
    }

    private ArrayList<CreateList> prepareData(){
        ArrayList<CreateList> theimage = new ArrayList<>();
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Smihealing".toString();
        File f = new File(path);
        File file[] = f.listFiles();
        for (int i=0; i < file.length; i++)
        {
            CreateList createList = new CreateList();
            createList.setImage_Location("file://"+file[i].getPath());
            Log.d("TAG",""+f.getPath());
            createList.setImage_title(file[i].getName());
            theimage.add(createList);
        }
        return theimage;
    }

    @Override
    public void onResume(){
        super.onResume();
        showGallery();

    }

}
