package com.swdm.mp.smile;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.swdm.mp.smile.helper.RecognizeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;


public class MainActivity extends AppCompatActivity {
    //private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    // private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //Function
    //  private String subscription_key ="9cb02e2dc8b34259b4f8e9956de2638a"; //key1
    private Context context;
    // Flag to indicate the request of the next task to be performed
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    // The URI of photo taken with camera
    private Uri mUriPhotoTaken;


    public static File dir;


    //Application UI
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_history,
            R.drawable.ic_therapy,
            R.drawable.ic_setting,
            R.drawable.ic_howto
    };
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateDirectoryForPictures();

        verifyStoragePermissions(this);

            /*floating button - RecognizeActivity
            * */
        /*
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecognizeActivity.class);
                startActivity(intent);
            }
        });
*/

            /*floating button - Take a photo and album
            * */
        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                int id = menuItem.getItemId();


                //Take a photo
                if (id == R.id.action_take) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File saveFile = new File(dir, "IMG_" + System.currentTimeMillis() + ".jpg");

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        //
                        // Save the photo taken to a temporary file.
                        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        // File file = File.createTempFile("IMG_", ".jpg", storageDir);
                        mUriPhotoTaken = Uri.fromFile(saveFile);

                        refreshGallery(saveFile);

                        Bundle tBun = new Bundle();
                        tBun.putParcelable("ImageUri", mUriPhotoTaken);
                        Log.d("tagTemp", "tempUri" + mUriPhotoTaken);
                        //
                        Log.d("tag", "saveFile URI" + Uri.fromFile(saveFile));
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);
                        intent.putExtras(tBun);

                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);


                    }

                }

                if (id == R.id.action_album) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM);
                    }
                }

                return false;
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }



    // Save the activity state when it's going to stop.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ImageUri", mUriPhotoTaken);
    }

    // Recover the saved state when the activity is recreated.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mUriPhotoTaken = savedInstanceState.getParcelable("ImageUri");
    }
    private void CreateDirectoryForPictures ()
    {
        dir =  new File (
                Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES), "Smihealing");


        Log.d("directory",dir.toString());
        if (!dir.exists ())
        {
            dir.mkdirs();
        }
    }



    private void refreshGallery(File imageFile){


        MediaScannerConnection.scanFile(getApplicationContext(), new String[] { "file://"+imageFile.getPath() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("tag", "Scanned " + path);
                    }
                });

    }

    // Deal with the result of selection of the photos and faces.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case REQUEST_TAKE_PHOTO:
/*
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("ImageUri",mUriPhotoTaken);
                Intent mIntent = new Intent(this,RecognizeActivity.class);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
*/

            case REQUEST_SELECT_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_OK) {
                    Uri imageUri;
                    if (data == null || data.getData() == null) {
                        imageUri = mUriPhotoTaken;

                    } else {
                        imageUri = data.getData();
                        Log.d("LOGGGG","여기2"+imageUri);

                    }
                    //image URI ë„˜ê¹€
                    Intent intent = new Intent();
                    intent.setData(imageUri);
                    setResult(RESULT_OK, intent);
                    //finish();
                    //


                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ImageUri",imageUri);
                    Log.d("Uri","힘들게하네"+imageUri);
                    /*
                    String to = dir + "/IMG_"+System.currentTimeMillis()+".jpg";
                    try {
                        copyFile(imageUri,to);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */

                    Intent myIntent = new Intent(this,RecognizeActivity.class);
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);


                }
                break;
            default:
                break;
        }
    }

    public void copyFile(Uri source, String target) throws IOException {
        FileInputStream fis = (FileInputStream) getContentResolver().openInputStream(source);

        FileOutputStream fos = new FileOutputStream(target);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();
        }
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HistoryFragment(), "HISTORY");
        adapter.addFragment(new TherapyFragment(), "THERAPY");
        adapter.addFragment(new SettingFragment(), "SETTING");
        adapter.addFragment(new HowtoFragment(), "HOWTO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}

