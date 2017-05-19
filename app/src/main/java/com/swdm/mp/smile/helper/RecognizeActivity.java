package com.swdm.mp.smile.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.FaceRectangle;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;

import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.FaceAttribute;
import com.swdm.mp.smile.R;
import com.swdm.mp.smile.helper.ImageHelper;

import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecognizeActivity extends Activity implements
        OnChartValueSelectedListener {

    // Flag to indicate which task is to be performed.
    private static final int REQUEST_SELECT_IMAGE = 0;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    //The Textview of information
    private TextView tv;

    // The button to select an image
    private Button mButtonSelectImage;

    // The URI of the image selected to detect.
    private Uri mImageUri;

    // The image selected to detect.
    private Bitmap mBitmap;

    // The edit to show status and result.
    private TextView mTextView;

    private EmotionServiceClient client;


    //The result of analysis
    private float smileDegree = 0;
    private float userAge = 0;
    private float happiness = 0;
    //private float avg = 0;
    //Result graph
    protected HorizontalBarChart mChart;

    //Result message
    private TextView healingmsg;
    private Button msgBtn;
    Random mRand;


    //Message List according to degree
    //0-20 --level one
    private String[] lvOne = {"It might not work, but everyone has such a case. Let's try again!",
            "Smile and try again!",
            "There are various variables in life. Whatever they say We are always on your side.",
            "Don't worry about it. You can do more smile. It's just beginning. What's up?",
            "It will pass, We can ride it out together. One more try!",
            "A smile is the healing system of the heart. Keep smiling always!",
            "Life is not a waste as long as there is at least one person in the world who cares for you So when things go wrong and you feel like giving up – remember you’ve got me!",
            "Have faith in yourself! One more try!",
            "Better not to worry because if u r worried u get a wrinkle, So Why don't u smile & get a dimple. Always smile and be happy.",
            "Cheer up! Because you just received a message from someone who cares.",
            "When everything seems to be going against you, Remember that the airplane takes off against the wind, not with it.",
            "As soon as you trust yourself, you will know how to live.",
            "Courage doesn't always roar. Sometimes courage is the quiet voice at the end of the day saying, I will try again tomorrow.",
            "When we think about problems they grow double; but when we laugh about them, they become bubbles. Quit worrying and start smiling.",
            "Your smiling potential seems limitless! One more try~ ;)"

    };


    //21-40 -- level two
    private String[] lvTwo = {"I like you better because you are different.",
            "One of your smile is important.",
            "Thank you for sharing your shy smile.",
            "May the strength of the past reflect in your future.",
            "A smile is the cooling system of the brain. Keep smiling always!",
            "The biggest enemy of success is fear of failure. So when fear knocks at your door, Send courage to open the door and success will wait for you.",
            "Welcome this day with a smile on your lips and a good thought in your heart. Let's Go!",
            "When we think about problems they grow double. but when we laugh about them, they become bubbles. Quit worrying and start smiling.",
            "Your smiling potential seems limitless! One more try ~ ;)"
    };


    //41-60 -- level three
    private String[] lvThree = {"You've done a nice job! It's wonderful! :)",
            "The smiles of today will be tomorrow's strength and future.",
            "You have so many extraordinary gifts–how can you expect to live an ordinary life?",
            "A smile is the lighting system of the face. Keep smiling always!",
            "Life is not a problem to be solved but a gift to be enjoyed. Make the best of this day!",
            "If 5 seconds of smile can make a photograph more beautiful,Then just imagine if you keep always smiling, How beautiful your life will be. So keep smiling!!!!!",
            "Welcome this day with a smile on your lips and a good thought in your heart. Let's Go!",
            "When we think about problems they grow double. but when we laugh about them, they become bubbles. Quit worrying and start smiling.",
            "You're really something special!"
    };


    //61-80 -- level four
    private String[] lvFour = {"Your presence makes me feel better.",
            "With your smile, you take daily vitamins.",
            "Your smile is like a flower that blooms in spring",
            "With your smile, the pain in our face disappears as well.",
            "A smile is nearly always inspired by another smile.Here’s one for you and let it spread around!",
            "A smile is a wonderful thing! It warms the heart and cools the sting. Keep smiling!",
            "Smile when in leisure. Smile when in pain. Smile when troubles pour like rain. Smile when someone hurt your feelings, cuz you know, smiling always starts the healing!",
            "You're even more beautiful on the inside than you are on the outside.",
            "Your smile is a candle in the darkness. :)"
    } ;


    //81-100 -- level five
    private String[] lvFive = {"This is the masterpiece in the world, you know.",
            "Your smile is a miracle! :)",
            "Darkness can not beat the light. Your smile is light.",
            "Nothing better than that :)",
            "Your smile is more important than the air i breathe!",
            "Just do as you've been doing so far.",
            "A smile is nearly always inspired by another smile.Here’s one for you and let it spread around!",
            "A smile is a wonderful thing! It warms the heart and cools the sting. Keep smiling!",
            "You have the best laugh!",
            "You should be proud of yourself!",
            "Is that your picture next to \"charming\" in the dictionary?",
            "On a scale from 1 to 10, you're an 11.",
            "You're even more beautiful on the inside than you are on the outside.",
            "Your smile is gorgeous!!"


    };


    String mStr[] = new String[3];




    //error message
    String errorMessage;


    //
    Intent intent;

    //Capture
    ImageButton bt_capture;
    private LinearLayout container = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);

        //
        this.container = (LinearLayout)findViewById(R.id.mainLayout);
        //reslut capturing
        bt_capture = (ImageButton)findViewById(R.id.bt_capture);

        if (client == null) {
            client = new EmotionServiceRestClient(getString(R.string.subscription_key));
        }

        tv = (TextView) findViewById(R.id.textView);
        // mButtonSelectImage = (Button) findViewById(R.id.buttonSelectImage);
        //mTextView = (TextView) findViewById(R.id.editTextResult);
        tv.setSelected(true);

        mRand = new Random();


        intent = getIntent();
        Bundle bd = intent.getExtras();

        mImageUri = intent.getParcelableExtra("ImageUri");

        mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mImageUri, getContentResolver());
        if (mBitmap != null) {
            // Show the image on screen.
            ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
            imageView.setImageBitmap(mBitmap);

            // Add detection log.
            Log.d("RecognizeActivity", "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
                    + "x" + mBitmap.getHeight());

            doRecognize();

        }





    }


    public void capture(View v) {
        Bitmap bitmap = screenShot();
        saveBitmap(bitmap);


    }


    public Bitmap screenShot(){

        View view = findViewById(R.id.mainLayout).getRootView();
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache();

    }

    public void saveBitmap(Bitmap bitmap) {
        File dir =  new File (
                Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES), "Smihealing");

        File imagePath = new File(dir,"/IMG_"+System.currentTimeMillis()+".jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
        MediaScannerConnection.scanFile(this, new String[] {

                        imagePath.getAbsolutePath()},

                null, new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri)

                    {


                    }

                });



    }
    public void doRecognize() {
        // mButtonSelectImage.setEnabled(false);

        // Do emotion detection using auto-detected faces.
        try {
            new doRequest(false).execute();
        } catch (Exception e) {
            //mTextView.append("Error encountered. Exception is: " + e.toString());
            errorMessage ="Error encountered. Exception is: " + e.toString();
            Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
        }

        String faceSubscriptionKey = getString(R.string.faceSubscription_key);
        if (faceSubscriptionKey.equalsIgnoreCase("Please_add_the_face_subscription_key_here")) {
            //mTextView.append("\n\nThere is no face subscription key in res/values/strings.xml. Skip the sample for detecting emotions using face rectangles\n");
            errorMessage = "\n\nThere is no face subscription key in res/values/strings.xml. Skip the sample for detecting emotions using face rectangles\n";
            Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
        } else {
            // Do emotion detection using face rectangles provided by Face API.
            try {
                new doRequest(true).execute();
            } catch (Exception e) {
                //mTextView.append("Error encountered. Exception is: " + e.toString());
                errorMessage = "Error encountered. Exception is: " + e.toString();
                Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
            }
        }


    }



    private List<RecognizeResult> processWithAutoFaceDetection() throws EmotionServiceException, IOException {
        Log.d("emotion", "Start emotion detection with auto-face detection");

        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        long startTime = System.currentTimeMillis();
        // -----------------------------------------------------------------------
        // KEY SAMPLE CODE STARTS HERE
        // -----------------------------------------------------------------------

        List<RecognizeResult> result = null;
        //
        // Detect emotion by auto-detecting faces in the image.
        //
        result = this.client.recognizeImage(inputStream);

        String json = gson.toJson(result);
        Log.d("result", json);

        Log.d("emotion", String.format("Detection done. Elapsed time: %d ms", (System.currentTimeMillis() - startTime)));
        // -----------------------------------------------------------------------
        // KEY SAMPLE CODE ENDS HERE
        // -----------------------------------------------------------------------
        return result;
    }

    private List<RecognizeResult> processWithFaceRectangles() throws EmotionServiceException, com.microsoft.projectoxford.face.rest.ClientException, IOException {
        Log.d("emotion", "Do emotion detection with known face rectangles");
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        long timeMark = System.currentTimeMillis();
        Log.d("emotion", "Start face detection using Face API");
        FaceRectangle[] faceRectangles = null;
        String faceSubscriptionKey = getString(R.string.faceSubscription_key);

        FaceServiceRestClient faceClient = new FaceServiceRestClient(faceSubscriptionKey);

        Face faces[] = faceClient.detect(inputStream, false, false,
                new FaceServiceRestClient.FaceAttributeType[] {
                        FaceServiceRestClient.FaceAttributeType.Age,
                        FaceServiceRestClient.FaceAttributeType.Gender,
                        FaceServiceRestClient.FaceAttributeType.Glasses,
                        FaceServiceRestClient.FaceAttributeType.Smile,
                        FaceServiceRestClient.FaceAttributeType.HeadPose });
        Log.d("emotion", String.format("Face detection is done. Elapsed time: %d ms", (System.currentTimeMillis() - timeMark)));


        if (faces != null) {
            faceRectangles = new FaceRectangle[faces.length];

            for (int i = 0; i < faceRectangles.length; i++) {
                // Face API and Emotion API have different FaceRectangle definition. Do the conversion.
                com.microsoft.projectoxford.face.contract.FaceRectangle rect = faces[i].faceRectangle;
                faceRectangles[i] = new com.microsoft.projectoxford.emotion.contract.FaceRectangle(rect.left, rect.top, rect.width, rect.height);
                userAge = (float)faces[i].faceAttributes.age;
                smileDegree = (float)faces[i].faceAttributes.smile * 100;
                Log.d("Age", userAge+"");
                Log.d("smile", smileDegree+"");

            }
        }

        List<RecognizeResult> result = null;
        if (faceRectangles != null) {
            inputStream.reset();

            timeMark = System.currentTimeMillis();
            Log.d("emotion", "Start emotion detection using Emotion API");
            // -----------------------------------------------------------------------
            // KEY SAMPLE CODE STARTS HERE
            // -----------------------------------------------------------------------
            result = this.client.recognizeImage(inputStream, faceRectangles);

            String json = gson.toJson(result);
            Log.d("result", json);
            // -----------------------------------------------------------------------
            // KEY SAMPLE CODE ENDS HERE
            // -----------------------------------------------------------------------
            Log.d("emotion", String.format("Emotion detection is done. Elapsed time: %d ms", (System.currentTimeMillis() - timeMark)));
        }
        return result;
    }

    private class doRequest extends AsyncTask<String, String, List<RecognizeResult>> {
        // Store error message
        private Exception e = null;
        private boolean useFaceRectangles = false;

        public doRequest(boolean useFaceRectangles) {
            this.useFaceRectangles = useFaceRectangles;
        }

        @Override
        protected List<RecognizeResult> doInBackground(String... args) {
            if (this.useFaceRectangles == false) {
                try {
                    return processWithAutoFaceDetection();
                } catch (Exception e) {
                    this.e = e;    // Store error
                }
            } else {
                try {
                    return processWithFaceRectangles();
                } catch (Exception e) {
                    this.e = e;    // Store error
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RecognizeResult> result) {
            super.onPostExecute(result);
            // Display based on error existence

            if (this.useFaceRectangles == false) {
                //mTextView.append("\n\nRecognizing emotions with auto-detected face rectangles...\n");
                errorMessage = "\n\nRecognizing emotions with auto-detected face rectangles...\n";
                //   Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
            } else {

//                mTextView.append("\n\nRecognizing emotions with existing face rectangles from Face API...\n");
                errorMessage = "\n\nRecognizing emotions with existing face rectangles from Face API...\n";
                //  Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();

            }
            if (e != null) {
                //mTextView.setText("Error: " + e.getMessage());
                errorMessage = "Error: " + e.getMessage();
                Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                this.e = null;
            } else {
                if (result.size() == 0) {
                    //    mTextView.append("No emotion detected :(");
                    errorMessage = "No emotion detected !";
                    Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();

                } else {
                    Integer count = 0;
                    // Covert bitmap to a mutable bitmap by copying it
                    Bitmap bitmapCopy = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas faceCanvas = new Canvas(bitmapCopy);
                    faceCanvas.drawBitmap(mBitmap, 0, 0, null);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(5);
                    paint.setColor(Color.RED);

                    for (RecognizeResult r : result) {
                        //mTextView.append(String.format("\nFace #%1$d \n", count));
                        //mTextView.append(String.format("\t anger: %1$.5f\n", r.scores.anger));
                        //mTextView.append(String.format("\t contempt: %1$.5f\n", r.scores.contempt));
                        //mTextView.append(String.format("\t disgust: %1$.5f\n", r.scores.disgust));
                        //mTextView.append(String.format("\t fear: %1$.5f\n", r.scores.fear));
                        //mTextView.setText(String.format("\t happiness: %1$.1f\n", r.scores.happiness * 100));
                        Log.d("happiness:", r.scores.happiness+"");

                        String hResult = String.format("%1$.1f", r.scores.happiness * 100);
                        Log.d("hResult:", hResult);
                        happiness = Float.parseFloat(hResult);
                        Log.d("happiness : ", happiness+"");


                        //mTextView.append(String.format("\t neutral: %1$.5f\n", r.scores.neutral));
                        //mTextView.append(String.format("\t sadness: %1$.5f\n", r.scores.sadness));
                        //mTextView.append(String.format("\t surprise: %1$.5f\n", r.scores.surprise));
                        //mTextView.append(String.format("\t face rectangle: %d, %d, %d, %d", r.faceRectangle.left, r.faceRectangle.top, r.faceRectangle.width, r.faceRectangle.height));
                        faceCanvas.drawRect(r.faceRectangle.left,
                                r.faceRectangle.top,
                                r.faceRectangle.left + r.faceRectangle.width,
                                r.faceRectangle.top + r.faceRectangle.height,
                                paint);


                        count++;
                    }
                    ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
                    imageView.setImageDrawable(new BitmapDrawable(getResources(), mBitmap));

                }

                //   mTextView.setSelection(0);
                barChart(userAge,happiness,smileDegree);

            }

            //  mButtonSelectImage.setEnabled(true);
        }
    }



    public void barChart (float age, float happy, float smile){

        //Graph

        mChart = (HorizontalBarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);


        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(5);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        //mChart.setDrawBarShadow(true);

        mChart.setDrawGridBackground(false);
        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = mChart.getAxisLeft();
        //yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        //yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yl.setStartAtZero(true);
        yl.setAxisMaximum(100f);
        //yl.setInverted(true);

        YAxis yr = mChart.getAxisRight();
        //yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(true);
        yr.setAxisMaximum(100f);
        //yr.setInverted(true);

        setData(3, 100, age,happy,smile);
        mChart.setFitBars(true);
        mChart.animateY(2500);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        l.setTextSize(8f);



    }

    private void setData(int count, float range ,float age, float happy, float smile) {

        float barWidth = 2f;
        float spaceForBar = 2f;
        ArrayList<BarEntry> yVals0 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

//        for (int i = 0; i < count; i++) {

        yVals0.add(new BarEntry(0*spaceForBar, smile));
        yVals1.add(new BarEntry(1*spaceForBar, happy));
        yVals2.add(new BarEntry(2*spaceForBar, age));


        //      }


        BarDataSet set0,set1,set2;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set0 = (BarDataSet)mChart.getData().getDataSetByIndex(2);
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(1);
            set2 = (BarDataSet)mChart.getData().getDataSetByIndex(0);

            set0.setValues(yVals0);
            //set0.setValueTextColor(Color.GREEN);
            //set0.setColors(Color.GREEN);

            set1.setValues(yVals1);
            //set1.setValueTextColor(Color.RED);
            //set1.setColors(Color.RED);

            set2.setValues(yVals2);
//            set2.setValueTextColor(Color.YELLOW);
//            set2.setColors(Color.YELLOW);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {

            set0 = new BarDataSet(yVals0, "Smile ");
            set0.setValueTextColor(Color.rgb(255, 102, 0));
            set0.setColors(Color.rgb(255, 102, 0));

            set1 = new BarDataSet(yVals1, "Happiness ");
            set1.setValueTextColor(Color.rgb(204, 0, 0));
            set1.setColors(Color.rgb(204, 0, 0));

            set2 = new BarDataSet(yVals2, "Age");
            set2.setValueTextColor(Color.rgb(0, 51, 0));
            set2.setColors(Color.rgb(0, 51, 0));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set2);
            dataSets.add(set1);
            dataSets.add(set0);


            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(barWidth);

            mChart.setData(data);

            //display message
            // healingmsg = (TextView) findViewById(R.id.healingmsg);
            msgBtn = (Button)findViewById(R.id.msgBtn);


            msgBtn.setShadowLayer(3, 3, 5, Color.WHITE);
            mStr[0] = new String(" ");
            mStr[1] = new String(" ");
            mStr[2] = new String(" ");


           float avg = happiness;

            Log.d("LogavgHappy", happy + "");
            Log.d("LogavgSmile", smile + "");
            Log.d("LogavgAverage", avg + "");

            int size;
            int num[] = new int[3];

            int i = 0;

            if (0 <= avg && avg < 21) {
                size = lvOne.length - 1;

                for (i = 0; i < 3; i++) {
                    num[i] = getRandom(size, 0);
                    mStr[i] = lvOne[num[i]];
                }


                msgBtn.setText(mStr[0]);

            } else if (21 <= avg && avg < 41) {
                size = lvTwo.length - 1;
                for (i = 0; i < 3; i++) {
                    num[i] = getRandom(size, 0);
                    mStr[i] = lvTwo[num[i]];
                }
                msgBtn.setText(mStr[0]);

            } else if (41 <= avg && avg < 61) {
                size = lvThree.length - 1;

                for (i = 0; i < 3; i++) {
                    num[i] = getRandom(size, 0);
                    mStr[i] = lvThree[num[i]];
                }
                msgBtn.setText(mStr[0]);

            } else if (61 <= avg && avg < 81) {
                size = lvFour.length - 1;

                for (i = 0; i < 3; i++) {
                    num[i] = getRandom(size, 0);
                    mStr[i] = lvFour[num[i]];
                }
                msgBtn.setText(mStr[0]);

            } else if (81 <= avg && avg < 101) {
                size = lvFive.length - 1;

                for (i = 0; i < 3; i++) {
                    num[i] = getRandom(size, 0);
                    mStr[i] = lvFive[num[i]];
                }
                msgBtn.setText(mStr[0]);
            }

        }


    }



    protected RectF mOnValueSelectedRectF = new RectF();
    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    };

    public int getRandom(int max, int offset) {


        int nResult = mRand.nextInt(max) + offset;


        return nResult;


    }

    public void clickMsg(View v){
        String text = msgBtn.getText().toString();

        if(text.equals("Reading your smile...")){

        }

        else if((!text.equals(mStr[0]))&&text.equals(mStr[2])){
            msgBtn.setText(mStr[0]);
        }

        else if((!text.equals(mStr[1]))&&text.equals(mStr[0])){
            msgBtn.setText(mStr[1]);
        }
        else if((!text.equals(mStr[2]))&&text.equals(mStr[1])){
            msgBtn.setText(mStr[2]);
        }

    }


}