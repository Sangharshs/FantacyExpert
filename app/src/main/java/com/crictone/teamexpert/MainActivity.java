package com.crictone.teamexpert;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.crictone.teamexpert.Adapters.CommonAdapter;
import com.crictone.teamexpert.Adapters.SliderAdapterExample;
import com.crictone.teamexpert.Adapters.indicatorAdapter;
import com.crictone.teamexpert.Fragments.FantacyPredictionFrag.FantacyPredictionFragment;
import com.crictone.teamexpert.Fragments.LiveScoreFragment.LivescoreFragment;
import com.crictone.teamexpert.Fragments.News.NewsFragment;
import com.crictone.teamexpert.Model.GameModel;
import com.crictone.teamexpert.Model.Match_Model;
import com.crictone.teamexpert.Model.SliderItem;
import com.crictone.teamexpert.util.ApiConfig;
import com.crictone.teamexpert.util.SaveSharedPreference;
import com.facebook.ads.AdView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.onesignal.OneSignal;
import com.smarteist.autoimageslider.SliderView;
import com.suddenh4x.ratingdialog.AppRating;
import com.suddenh4x.ratingdialog.preferences.RatingThreshold;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.crictone.teamexpert.util.ApiConfig.GAMES_;
import static com.crictone.teamexpert.util.ApiConfig.IMG;
import static com.crictone.teamexpert.util.Utility.USER_NAME;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static String PROMOTIONAL_BANNER;
    public static String PROMOTIONAL_BANNER_URL;
    public static String PRIVACY_POLICY;
    public static String TERMS_CONDITIONS;
    public static String READ_ME;
    public static int MINIMUM_AMOUNT;

    private Match_Model matchModel;
    private NavigationView navigationView;
    private AdView adView;
    private DrawerLayout drawerLayout;
    private indicatorAdapter.callback indicatorCallback;
    public static final String BrodcastStringForAction = "checkinternet";
    private View noInternet, fragmentHome;

    private IntentFilter mIntentFilter;
    private Button retryButton;
    private CircleImageView float_button_icon;
    private CardView cardView;

    public static List<GameModel> gameModelList = new ArrayList<>();
    public static List<Match_Model> matchModelList = new ArrayList<>();

    private ShimmerFrameLayout shimmer0, shimmer1, shimmer2;
    // LottieAnimationView lottieAnimationView;
    private RecyclerView matches_recyclerview, gamecategory_recyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String payment_Method, pnumber, matches_category, slider_category;
    private indicatorAdapter indicatorAdapter;
    //    Second Slider
    private SliderAdapterExample adapter;
    private SliderItem sliderItem;
    private List<SliderItem> sliderItemList;
    private SliderView sliderView;
    private EditText pnumberEdittext;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private String username, email, total_earning;
    private TextView userTextV, emailTextV;
    private LinearLayout linearLayout;
    private int selectedId, position;
    private TextView setMinimumAlert;
    private CommonAdapter cricketAdapter;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        noInternet = findViewById(R.id.no_internet_design);
        fragmentHome = findViewById(R.id.main_layout);
        float_button_icon = findViewById(R.id.floatbuttonicon);
        retryButton = findViewById(R.id.restart_button);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.liveScore);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId("111113ef-c774-4937-8526-8b449758764b");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        new AppRating.Builder(this)
                .setMinimumLaunchTimes(5)
                .setMinimumDays(7)
                .setMinimumLaunchTimesToShowAgain(5)
                .setMinimumDaysToShowAgain(10)
                .setRatingThreshold(RatingThreshold.FOUR)
                .showIfMeetsConditions();


        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BrodcastStringForAction);
        Intent serviceIntent = new Intent(getApplicationContext(), MyServices.class);
        startService(serviceIntent);


        load_app_setting();
        load_navigation_items();
        JsonArrayRequest request0 = new JsonArrayRequest(Request.Method.POST, GAMES_ + "minlimit.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        MINIMUM_AMOUNT = object.getInt("minimum_limit");
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

        RequestQueue queue0 = Volley.newRequestQueue(getApplicationContext());
        queue0.add(request0);


        load_app_setting();


        if (isOnline(this)) {
            if (noInternet != null)
                noInternet.setVisibility(View.VISIBLE);
            setVisibility_ON();
        } else {
            setVisibility_OFF();
        }

    }


    public Boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();


        if (ni != null && ni.isConnectedOrConnecting())
            return true;
        else
            return false;
    }


    //Radio Button Start//
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        payment_Method = "";
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.googlepaybtn:
                if (checked)
                    payment_Method = "Google Pay";
                break;
            case R.id.paytmbtn:
                if (checked)
                    payment_Method = "Paytm";
                break;
        }
        Toast.makeText(getApplicationContext(), payment_Method, Toast.LENGTH_SHORT).show();
    }
    //Radio Button End//

    // Show Floating Action Button Start//
    private void show_float_button() {
        if (ApiConfig.FLOATING_BUTTON_ICON != null) {
            float_button_icon.setVisibility(View.INVISIBLE);
            float_button_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApiConfig.FLOATING_BUTTON_URL != null && !ApiConfig.FLOATING_BUTTON_URL.isEmpty()) {
                        Uri uri = Uri.parse(ApiConfig.FLOATING_BUTTON_URL);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        Glide.with(getApplicationContext()).load(IMG + ApiConfig.FLOATING_BUTTON_ICON).into(float_button_icon);

                    } else {
                        float_button_icon.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    // Show Floating Action Button End//

    private void load_app_setting() {
        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.POST, GAMES_ + "app_setting.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        PRIVACY_POLICY = object.getString("privacy_policy");
                        TERMS_CONDITIONS = object.getString("terms");
                        READ_ME = object.getString("read_me");

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
        RequestQueue queue2 = Volley.newRequestQueue(getApplicationContext());
        queue2.add(request2);

    }

    //   Start For Send Payment Request //
    private void send_payment_request() {
        pnumber = pnumberEdittext.getText().toString();
        if (username != null && email != null && total_earning != null && payment_Method != null && !pnumber.isEmpty()) {

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[5];
                    field[0] = "username";
                    field[1] = "email";
                    field[2] = "user_points";
                    field[3] = "payment_type";
                    field[4] = "payment_number";
                    //Creating array for data
                    String[] data = new String[5];
                    data[0] = username;
                    data[1] = email;
                    data[2] = total_earning;
                    data[3] = payment_Method;
                    data[4] = pnumber;

                    Log.d("DataL", username + email + payment_Method + pnumber);

                    PutData putData = new PutData(GAMES_ + "payment_req.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            //End ProgressBar (Set visibility to GONE)
                            // progressLayout.setVisibility(View.GONE);
                            Log.i("PutData", String.valueOf(putData));
                            if (result.equals("Request Send Successfully")) {
                                Toast.makeText(MainActivity.this, "Request Send Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    // End Write and Read data with URL
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "All Fields Are Require.", Toast.LENGTH_SHORT).show();
        }


    }

    //   End Payment Request Code //
    private void load_navigation_items() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_rate_us) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else if (id == R.id.nav_check_update) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else if (id == R.id.nav_subscribe) {
                    //   bp.subscribe(MainActivity.this, SUBSCRIPTION_ID);
                } else if (id == R.id.nav_share_app) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    String linkshare = "*Crictone - Fantasy Team Expert*\n" +
                            "Fantasy Sports Pro Tips and Expert Advice with Premium Teams For All Matches...! \uD83D\uDC47\uD83C\uDFFE Download the App Now \n" +
                            "https://bit.ly/crictone";
                    String sybject = "Download App" + R.string.app_name;
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_SUBJECT, sybject);
                    share.putExtra(Intent.EXTRA_TEXT, linkshare);
                    startActivity(Intent.createChooser(share, "Share using"));
                } else if (id == R.id.nav_privacy_policy) {
                    if (PRIVACY_POLICY != null) {
                        Intent intent = new Intent(MainActivity.this, NavigationItemsActivity.class);
                        intent.putExtra("privacy_policy", PRIVACY_POLICY);
                        startActivity(intent);
                    }
                } else if (id == R.id.nav_read_me) {
                    if (READ_ME != null) {
                        Intent intent = new Intent(MainActivity.this, NavigationItemsActivity.class);
                        intent.putExtra("read_me", READ_ME);
                        startActivity(intent);
                    }
                } else if (id == R.id.nav_terms_and_conditions) {
                    if (TERMS_CONDITIONS != null) {
                        Intent intent = new Intent(MainActivity.this, NavigationItemsActivity.class);
                        intent.putExtra("terms", TERMS_CONDITIONS);
                        startActivity(intent);
                    }
                } else if (id == R.id.nav_contact_us) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = {"crictone07@gmail.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Send mail"));
                } else if (id == R.id.refer_earn) {
                    startActivity(new Intent(MainActivity.this, Refer_EarnActivity.class));
                } else if (id == R.id.nav_login) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else if (id == R.id.nav_logout) {
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                    SaveSharedPreference.getUserName(getApplicationContext(), username);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    pref.getString(USER_NAME, "");
                    pref.edit().clear().apply();
                    recreate();
                } else if (id == R.id.nav_news) {
                    startActivity(new Intent(MainActivity.this, NewsmActivity.class));
                } else if (id == R.id.nav_top_fantacy_apps) {
                    startActivity(new Intent(MainActivity.this, TopFantacyAppsActivity.class));
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else if (id == R.id.redeem_points) {

                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.withdraw_dialouge);
                    dialog.getWindow().setLayout(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                    TextView amounttext = dialog.findViewById(R.id.editText_amount);
                    setMinimumAlert = dialog.findViewById(R.id.setAlertMinim);
                    pnumberEdittext = dialog.findViewById(R.id.editText_googlepaynumber);
                    radioGroup = dialog.findViewById(R.id.radioGroup);

                    Button send_req = dialog.findViewById(R.id.send_request);
                    selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton1 = dialog.findViewById(selectedId);
                    setMinimumAlert.setText("Minimum " + String.valueOf(MINIMUM_AMOUNT) + " Coins Are Required For Redeem.");

                    send_req.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (Integer.parseInt(total_earning) >= MINIMUM_AMOUNT) {
                                send_payment_request();
                            } else {
                                Toast.makeText(MainActivity.this, "Minimum " + MINIMUM_AMOUNT + " Coins Are Required.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    if (total_earning != null)
                        amounttext.setText(String.valueOf(total_earning));
                    dialog.show();
                } else {
                    Toast.makeText(MainActivity.this, "Minimum Require Points is " + String.valueOf(MINIMUM_AMOUNT), Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_purchase:
                ///bp.subscribe(MainActivity.this, SUBSCRIPTION_ID);
                return true;
            case R.id.action_telegram:
                if (ApiConfig.TELEGRAM_BANNER_URL != null && !ApiConfig.TELEGRAM_BANNER_URL.isEmpty()) {
                    Uri uri = Uri.parse(ApiConfig.TELEGRAM_BANNER_URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No Url Found", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public BroadcastReceiver myReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BrodcastStringForAction)) {
                if (intent.getStringExtra("online_status").equals("true")) {
//                    setVisibility_ON();
                } else {
//                    setVisibility_OFF();
                }
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        load_float_button();
        show_float_button();

        registerReceiver(myReciver, mIntentFilter);

        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_terms_and_conditions).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_read_me).setVisible(true);
            navigationView.getMenu().findItem(R.id.refer_earn).setVisible(true);
            navigationView.getMenu().findItem(R.id.redeem_points).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            // perform action when user is already logged in
        } else {
            navigationView.getMenu().findItem(R.id.nav_terms_and_conditions).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_read_me).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.refer_earn).setVisible(false);
//            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.redeem_points).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            // perform action when user is not logged in
        }
    }


    @Override
    public void onDestroy() {

        if (adView != null) {
            adView.destroy();
        }
        unregisteredBrodcastReciver();
        super.onDestroy();
    }

    protected void unregisteredBrodcastReciver() {
        try {
            unregisterReceiver(myReciver);
            // unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(myReciver, mIntentFilter);
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle(getString(R.string.app_name));
        alertdialog.setMessage("Are you sure you want to exit?");

        alertdialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                System.exit(0);
//                finish();

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);

            }
        });

        alertdialog.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.crictone.teamexpert");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alertdialog.show();

    }


    LivescoreFragment livescoreFragment = new LivescoreFragment();
    NewsFragment newsFragment = new NewsFragment();
    FantacyPredictionFragment fantacyPredictionFragment = new FantacyPredictionFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.liveScore:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, livescoreFragment).commit();
                return true;

            case R.id.fantacyPrediction:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fantacyPredictionFragment).commit();
                return true;

            case R.id.newsBottomMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, newsFragment).commit();
                return true;
        }

        return false;
    }
    //Sort Matches Time//

    // Load Floating Action Button Start//
    private void load_float_button() {
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.POST, GAMES_ + "banners.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        ApiConfig.TELEGRAM_BANNER = object.getString("t_banner_image");
                        ApiConfig.TELEGRAM_BANNER_URL = object.getString("t_banner_link");
                        ApiConfig.FLOATING_BUTTON_URL = object.getString("web_url");
                        ApiConfig.FLOATING_BUTTON_ICON = object.getString("float_icon");
                        //Toast.makeText(MainActivity.this, FLOATING_BUTTON_URL, Toast.LENGTH_SHORT).show();
                        Glide.with(getApplicationContext()).load(IMG + ApiConfig.FLOATING_BUTTON_ICON).into(float_button_icon);

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
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        queue1.add(request1);
    }

    // Load Floating Action button End//
    public void setVisibility_ON() {
        if (noInternet != null)
            noInternet.setVisibility(View.GONE);
        fragmentHome.setVisibility(View.VISIBLE);
    }

    public void setVisibility_OFF() {
        noInternet.setVisibility(View.VISIBLE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        fragmentHome.setVisibility(View.GONE);

    }

}