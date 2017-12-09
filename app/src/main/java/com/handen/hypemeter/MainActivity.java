package com.handen.hypemeter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    EditText enter;
    TextView checkInternet;
    String urlText = "";
    double likes, reposts, comments;
    boolean isLogin = false;
    static boolean isDeleteLink = false;


    //c1c6d623017585c33da91cf07a36eea1fb709c890ef183de

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        String appKey = "c1c6d623017585c33da91cf07a36eea1fb709c890ef183de";
        Appodeal.disableLocationPermissionCheck();
        Appodeal.initialize(this, appKey, Appodeal.BANNER);
        Log.d("appodeal", Boolean.toString(Appodeal.isLoaded(Appodeal.BANNER_BOTTOM)));
        Appodeal.show(this, Appodeal.BANNER);

        likes = 0;
        reposts = 0;
        comments = 0;




        if(!VKSdk.isLoggedIn())
        {
            VKSdk.login(this, VKScope.WALL, VKScope.PHOTOS);
        }

        startButton = (Button) findViewById(R.id.startButton);
        enter = (EditText) findViewById(R.id.enterAdressEditText);
     //   enter.clearFocus();

        enter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Методы
                Editable ed = enter.getText();
                urlText = ed.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable ed = enter.getText();
                urlText = ed.toString();
            }
        });

//        enter.setCursorVisible(false);

        //   enter.setFocusable(false);
        checkInternet = (TextView) findViewById(R.id.checkInternetTV);
        checkInternet.setVisibility(View.INVISIBLE);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ResultActivity.class);System.out.print(" ");

  //              urlText = "https://vk.com/montager1337?z=photo419898369_456241738%2Fwall221080680_449";

//                urlText = "";
//                urlText = "https://vk.com/borsch?z=photo-460389_456270181%2Falbum-460389_00%2Frev";
//                urlText = "https://vk.com/feed?z=photo-143503361_456239727%2Falbum-143503361_00%2Frev";

                if (urlText.equals("")) {
                    Toast.makeText(MainActivity.this,
                            "Ваша ссылка пуста",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isOnline(getApplicationContext())) {
                    //      startActivity(intent);
                    Log.d("ONLINE", "ONLINE");
                    checkInternet.setVisibility(View.INVISIBLE);

                    if (urlText.contains("wall")) {
                        String text = urlText.substring(urlText.indexOf("wall") + 4);
                        if(text.contains("%"))
                        {
                            text = text.substring(0, text.indexOf('%'));
                        }

                        VKParameters paramsForPost = new VKParameters();
                        paramsForPost.put("posts", text);
                        paramsForPost.put(VKApiConst.EXTENDED, 1);
                        VKRequest requestPost = new VKRequest("wall.getById", paramsForPost);

                        requestPost.executeWithListener(new VKRequest.VKRequestListener() {
                            @Override
                            public void onComplete(VKResponse response) {
                                //Do complete stuff
                                Log.d("post", response.json.toString());
                                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                intent.putExtra("value", getValue(response.json.toString()));
                                intent.putExtra("URL", urlText);
                                startActivity(intent);


                            }

                            @Override
                            public void onError(VKError error) {
                                //Do error stuff
                                Log.d("post", error.toString());
                                showErrorToast();
                            }

                            @Override
                            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                                //I don't really believe in progress
                                Log.d("post", "attemptFailed");
                            }
                        });

                    } else {
                        if (urlText.contains("photo")) {

                            String text = urlText.substring(urlText.indexOf("photo") + 5);
                            if(text.contains("%"))
                            {
                                text = text.substring(0, text.indexOf('%'));
                            }



                            VKParameters paramsForPhoto = new VKParameters();
                            paramsForPhoto.put(VKApiConst.PHOTOS, text);
                            paramsForPhoto.put(VKApiConst.EXTENDED, 1);
                            VKRequest requestPhoto = new VKRequest("photos.getById", paramsForPhoto);

                            requestPhoto.executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    //Do complete stuff
                                    Log.d("photo", response.json.toString());
                                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                    intent.putExtra("value", getValue(response.json.toString()));
                                    intent.putExtra("URL", urlText);
                                  //  intent.putExtra("value", 159);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(VKError error) {
                                    //Do error stuff
                                    Log.d("photo", error.toString());
                                    showErrorToast();
                                }

                                @Override
                                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                                    //I don't really believe in progress
                                    Log.d("photo", "attemptFailed");
                                }
                            });


                        } else
                            showErrorToast();

                    }


                } else {
                    checkInternet.setVisibility(View.VISIBLE);
                    Log.d("ONLINE", "NOT ONLINE");

                }
            }
        });

    }

    public boolean isOnline(Context context) {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
// Пользователь успешно авторизовался
                Log.d("login", "succes");
            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Log.d("login", "error");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void showErrorToast() {
        Toast.makeText(MainActivity.this,
                "Пожалуйста, проверьте вашу ссылку и попробуйте снова",
                Toast.LENGTH_SHORT).show();
    }

    public double getValue(String s) {
        String likesResult = "";
        String repostResult = "";
        String commentResult = "";


        if (s.contains("likes")) {

            String likesString = s.substring(s.indexOf("\"likes\":") + 7, s.indexOf("}", s.indexOf("\"likes\":")) + 1);
            char[] arraylikes = likesString.toCharArray();
            ArrayList<Character> listLikes = new ArrayList<>();
            for (int i = 0; i < arraylikes.length; i++) {
                listLikes.add(arraylikes[i]);
            }


            String stringLikes = "";
            for (int i = 0; i < listLikes.size(); i++) {
                stringLikes += listLikes.get(i);
            }
//            System.out.println(stringLikes);

            int likeIndex = stringLikes.indexOf("\"count\":") + 8;
//            System.out.println(likeIndex);
            while (stringLikes.charAt(likeIndex) != ',' && stringLikes.charAt(likeIndex) != '}') {
                likesResult += stringLikes.charAt(likeIndex);
                likeIndex++;
            }

//            System.out.println(likesResult);

            if (s.contains("reposts")) {
                String repostString = s.substring(s.indexOf("\"reposts\":") + 10, s.indexOf("}", s.indexOf("\"reposts\":")) + 1);
                char[] arrayRepost = repostString.toCharArray();
                ArrayList<Character> listRepost = new ArrayList<>();
                for (int i = 0; i < arrayRepost.length; i++) {
                    listRepost.add(arrayRepost[i]);
                }


                String stringRepost = "";
                for (int i = 0; i < listRepost.size(); i++) {
                    stringRepost += listRepost.get(i);
                }
//            System.out.println(stringRepost);

                int repostIndex = stringRepost.indexOf("\"count\":") + 8;
//            System.out.println(repostIndex);
                while (stringRepost.charAt(repostIndex) != ',' && stringRepost.charAt(repostIndex) != '}') {
                    repostResult += stringRepost.charAt(repostIndex);
                    repostIndex++;
                }

//            System.out.println(repostResult);
            }


            if (s.contains("comments")) {
                String commentString = s.substring(s.indexOf("\"comments\":") + 11, s.indexOf("}", s.indexOf("\"comments\":")) + 1);
                char[] arrayComments = commentString.toCharArray();
                ArrayList<Character> listComments = new ArrayList<>();
                for (int i = 0; i < arrayComments.length; i++) {
                    listComments.add(arrayComments[i]);
                }


                String stringComment = "";
                for (int i = 0; i < listComments.size(); i++) {
                    stringComment += listComments.get(i);
                }
//                System.out.println(stringComment);

                int commentIndex = stringComment.indexOf("\"count\":") + 8;
//                System.out.println(commentIndex);
                while ((stringComment.charAt(commentIndex) != ',') && (stringComment.charAt(commentIndex) != '}')) {
                    commentResult += stringComment.charAt(commentIndex);
                    commentIndex++;
                }

//                System.out.println(commentResult);
            }

            if (likesResult.equals("")) {
                likesResult = "0";
            }
            if (repostResult.equals("")) {
                repostResult = "0";
            }
            if (commentResult.equals("")) {
                commentResult = "0";
            }


            likes = Double.parseDouble(likesResult.trim());
            Log.d("likes", likesResult.trim());
            reposts = Double.parseDouble(repostResult.trim());
            Log.d("reposts", repostResult.trim());
            comments = Double.parseDouble(commentResult.trim());
            Log.d("comments", commentResult.trim());
//            System.out.println(likes + "\t" + reposts + "\t" + comments);


        }

        return ((double) (likes * 1.0 + reposts * 4.0 + comments * 4.0));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(isDeleteLink)
        {
            enter.setText("");
            isDeleteLink = false;
        }
    }


}
