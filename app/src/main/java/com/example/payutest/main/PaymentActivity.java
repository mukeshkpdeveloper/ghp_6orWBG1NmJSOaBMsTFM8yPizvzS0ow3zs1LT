package com.example.payutest.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.payumoney.core.PayUmoneySdkInitializer;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity : ";
    private boolean isDisableExitConfirmation = false;
    private String userMobile, userEmail;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private SharedPreferences userDetailsPreference;
    private EditText email_et, mobile_et, amount_et;
    private TextInputLayout email_til, mobile_til;
    private SwitchCompat switch_disable_wallet, switch_disable_netBanks, switch_disable_cards, switch_disable_ThirdPartyWallets, switch_disable_ExitConfirmation;
    private TextView logoutBtn;
    private AppCompatRadioButton radio_btn_default;
    private AppPreference mAppPreference;

    private Button payNowButton;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mAppPreference = new AppPreference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.app_name));
        settings = getSharedPreferences("settings", MODE_PRIVATE);
        logoutBtn = (TextView) findViewById(R.id.logout_button);
        email_et = (EditText) findViewById(R.id.email_et);
        mobile_et = (EditText) findViewById(R.id.mobile_et);
        amount_et = (EditText) findViewById(R.id.amount_et);
        amount_et.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
        email_til = (TextInputLayout) findViewById(R.id.email_til);
        mobile_til = (TextInputLayout) findViewById(R.id.mobile_til);
        radioGroup_color_theme = (RadioGroup) findViewById(R.id.radio_grp_color_theme);
        radio_btn_default = (AppCompatRadioButton) findViewById(R.id.radio_btn_theme_default);
        radio_btn_theme_pink = (AppCompatRadioButton) findViewById(R.id.radio_btn_theme_pink);
        radio_btn_theme_purple = (AppCompatRadioButton) findViewById(R.id.radio_btn_theme_purple);
        radio_btn_theme_green = (AppCompatRadioButton) findViewById(R.id.radio_btn_theme_green);
        radio_btn_theme_grey = (AppCompatRadioButton) findViewById(R.id.radio_btn_theme_grey);
    }
}