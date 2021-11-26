package com.crictone.teamexpert;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crictone.teamexpert.util.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import static com.crictone.teamexpert.util.ApiConfig.GAMES_;
import static com.crictone.teamexpert.util.Utility.USER_NAME;

public class Refer_EarnActivity extends AppCompatActivity {

    Button inviteBtn;
    TextView referCodeText;
    String referCode, userID, username;
    TextView referResult;
    ImageButton copyImageButton;
    int total_refer=0;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer__earn);

        inviteBtn = findViewById(R.id.inviteButton);
        referCodeText = findViewById(R.id.user_refer_id);
        referResult = findViewById(R.id.totalInvite);
        copyImageButton = findViewById(R.id.copycode);
        imageView = findViewById(R.id.s1);
        imageView.setImageResource(R.drawable.app_icon);
        getSharedP();
        getData();
        getButtons();
    }
    private void getSharedP() {
        SaveSharedPreference.getUserName(getApplicationContext(), username);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        username = pref.getString(USER_NAME, "");
    }

    private void getData() {
        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.POST, GAMES_ + "refer.php?username=" + username, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        referCode = object.getString("userid");
                        referCodeText.setText(referCode);
                        if (referCode != null) {
                            String url = GAMES_ + "refercount.php?refer_code=" + referCode;
                            JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    referResult.setText("Your Invited Friends "+String.valueOf(response.length()));
                                  //  Toast.makeText(Refer_EarnActivity.this, String.valueOf(response.length()), Toast.LENGTH_SHORT).show();
                                    total_refer = response.length();
                                    for (int i = 0; i < response.length(); i++) {
                                        try {
                                            JSONObject object = response.getJSONObject(i);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(request);
                        }
                        //   Toast.makeText(Refer_EarnActivity.this, String.valueOf(response.length()), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
        queue3.add(request3);


    }
    private Integer[] imageIDs = {
            R.drawable.app_icon
    };
    private void getButtons() {
        copyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!referCode.isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", referCode);
                    assert clipboard != null;
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(Refer_EarnActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String share_content =   "CricTone - Fantasy Team Expert  \n" +
                        "\n" +
                        "\uD83D\uDCA5 Probable 11 for All Matches & Final Teams \n" +
                        "\n" +
                        "\uD83D\uDD25 Sport Fantasy Tips and Expert Advice For All Matches \n" +
                        "\n" +
                        "\uD83D\uDCA5 Pro Tips and Predictions with Premium Teams \n" +
                        "\n" +
                        "\uD83D\uDD25 Fantasy App Offers for Dream11, Myteam11, Fanfight, My11circle, Real11 etc \n" +
                        "\n" +
                        "✅ अगर आपने Fantasy leagues में Loss किया है तो एक बार इस एप्लीकेशन को ट्राय कर सकते हो। \n" +
                        "\n" +
                        "✅ यहाँ पर आपको मैच के प्रेडिक्शन के साथ प्रीमियम टीम भी फ्री में दिए जायेंगे। \n" +
                        "\n" +
                        "\uD83D\uDCA5 Play Fantasy Games and Earn M0ney\n" +
                        "\n" +
                        "\uD83D\uDC47\uD83C\uDFFE Download the App Now" + "\n https://bit.ly/crictone \n" +
                        "\n" +
                        "Use Refer Code: "+referCode;
                Toast.makeText(Refer_EarnActivity.this, "Wait..", Toast.LENGTH_SHORT).show();

                Bitmap b =BitmapFactory.decodeResource(getResources(),R.drawable.app_icon);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "", null);
                Uri imageUri =  Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.putExtra(Intent.EXTRA_TEXT,share_content);
                startActivity(Intent.createChooser(share, "Select"));
            }

        });
    }

//    private boolean checkWriteExternalPermission()
//    {
//        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//        int res = getContext().checkCallingOrSelfPermission(permission);
//        return (res == PackageManager.PERMISSION_GRANTED);
//    }

}