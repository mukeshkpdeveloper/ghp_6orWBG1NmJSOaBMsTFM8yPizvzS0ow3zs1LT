package com.example.payutest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

//    private TextView mTxvProductPrice, mTxvBuy;
private static final String TAG = "MainActivity";
    long number;
    String txnid ="txt12346", amount ="20", phone ="7803994667",
            productinfo ="BlueApp Course", firstname ="Mukesh", email ="kumarmukeshpatel57@gmail.com",
            merchantId ="8250101", merchantkey="3TnMpV";
    private String salt = "g0nGFe03";
    /*String txnid ="txt12346", amount ="20", phone ="7770878087",
            productinfo ="BlueApp Course", firstname ="yogi", email ="yogiii363@gmail.com",
            merchantId ="5884494", merchantkey="qZtEgloC";
    private String salt = "BuvBXdsVpm";*/

    String hashSequence;
    PayUmoneySdkInitializer.PaymentParam paymentParam;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String hashSequence = key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||salt;
//        String serverCalculatedHash= hashCal("SHA-512", hashSequence);

        TextView txt_buy_product = findViewById(R.id.txt_buy_product);

        txt_buy_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPaymentFlow();

            }
        });

    }


     void launchPaymentFlow() {
        hashSequence=merchantkey+"|"+txnid+"|"+amount+"|"+productinfo+"|"+firstname+"|"+email+"|"
                +"udf1"+"|"+"udf2"+"|"+"udf3"+"|"+"udf4"+"|"+"udf5"+"|"+"udf6"+"|"+"udf7"+"|"+"udf8"+"|"
                +"udf9"+"|"+"udf10"+"|"+salt;

        Log.d(TAG, "launchPaymentFlow1: "+hashSequence);

        String serverCalculatedHash= getHashkey("SHA-512", hashSequence);

        Log.d(TAG, "launchPaymentFlow2: "+serverCalculatedHash);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        builder.setAmount(amount)
                .setTxnId(txnid)
                .setPhone(phone)
                .setProductName(productinfo)
                .setFirstName(firstname)
                .setEmail(email)
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")
                .setUdf1("udf1")
                .setUdf2("udf2")
                .setUdf3("udf3")
                .setUdf4("udf4")
                .setUdf5("udf5")
                .setUdf6("udf6")
                .setUdf7("udf7")
                .setUdf8("udf8")
                .setUdf9("udf9")
                .setUdf10("udf10")
                .setIsDebug(false) // Integration environment - true (Debug)/ false(Production)
                .setKey(merchantkey)
                .setMerchantId(merchantId);

        try {
            paymentParam = builder.build();
            paymentParam.setMerchantHash(serverCalculatedHash);
            Log.d(TAG, "sent1Try: ");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "sent1: "+e.getMessage());
        }

        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, MainActivity.this,
                R.style.AppTheme_default, false);

    }

    public static String getHashkey(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
            Log.d(TAG, "getHashkey: "+hash.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d(TAG, "getHashkey2: "+e.getMessage());

        }
        return hash.toString();
    }


/*
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

//        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        stringBuilder.append("eCwWELxi");

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }
*/

   /* public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }
*/
/*
    private void calculateHashInServer(final PayUmoneySdkInitializer.PaymentParam mPaymentParams) {
//        ProgressUtils.showLoadingDialog(this);
//        String url = Constants.MONEY_HASH;


    }
*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    //Failure Transaction
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
            }  *//*else if (resultModel != null && resultModel.getError() != null) {
                Log.d("TAG", "Error response : " + resultModel.getError().getTransactionResponse());
            }*//* else {
                Log.d("TAG", "Both objects are null!");
            }
        }
    }*/

    /**
     * This AsyncTask generates hash from server.
     */
//    private class GetHashesFromServerTask extends AsyncTask<String, String, String> {
//        private ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Please wait...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... postParams) {
//
//            String merchantHash = "";
//            try {
//                //TODO Below url is just for testing purpose, merchant needs to replace this with their server side hash generation url
//                URL url = new URL("https://payu.herokuapp.com/get_hash");
//
//                String postParam = postParams[0];
//
//                byte[] postParamsByte = postParam.getBytes("UTF-8");
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
//                conn.setDoOutput(true);
//                conn.getOutputStream().write(postParamsByte);
//
//                InputStream responseInputStream = conn.getInputStream();
//                StringBuffer responseStringBuffer = new StringBuffer();
//                byte[] byteContainer = new byte[1024];
//                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
//                    responseStringBuffer.append(new String(byteContainer, 0, i));
//                }
//
//                JSONObject response = new JSONObject(responseStringBuffer.toString());
//
//                Iterator<String> payuHashIterator = response.keys();
//                while (payuHashIterator.hasNext()) {
//                    String key = payuHashIterator.next();
//                    switch (key) {
//                        /**
//                         * This hash is mandatory and needs to be generated from merchant's server side
//                         *
//                         */
//                        case "payment_hash":
//                            merchantHash = response.getString(key);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return merchantHash;
//        }
//
//        @Override
//        protected void onPostExecute(String merchantHash) {
//            super.onPostExecute(merchantHash);
//
//            progressDialog.dismiss();
//   //         payNowButton.setEnabled(true);
//
//            if (merchantHash.isEmpty() || merchantHash.equals("")) {
//                Toast.makeText(MainActivity.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
//            } else {
//                mPaymentParams.setMerchantHash(merchantHash);
//
//
//                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, MainActivity.this, R.style.PayumoneyAppTheme, false);
//            }
//        }
//    }
}
