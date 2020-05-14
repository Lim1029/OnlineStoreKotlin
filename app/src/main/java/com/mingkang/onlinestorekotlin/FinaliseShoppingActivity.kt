package com.mingkang.onlinestorekotlin

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_finalise_shopping.*
import java.math.BigDecimal

class FinaliseShoppingActivity : AppCompatActivity() {

    var ttPrice:Long = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalise_shopping)

        val calculateTotalPriceURL = "http://192.168.137.251/OnlineStoreApp/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUMBER")}"
        val requestQ = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, calculateTotalPriceURL,
            Response.Listener { response ->
                btnPaymentProcessing.text = "Pay ${response} via PayPal"
                ttPrice = response.toLong()
            },
            Response.ErrorListener { error ->
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Alert")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })
        requestQ.add(stringRequest)
        var paypalConfig = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(MyPayPal.clientID)
        var ppService = Intent(this, PayPalService::class.java)
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, ppService)
        startService(ppService)

        btnPaymentProcessing.setOnClickListener {
            var ppProcessing = PayPalPayment(BigDecimal.valueOf(ttPrice),"USD","Online Store Kotlin!", PayPalPayment.PAYMENT_INTENT_SALE)
            var paypalPaymentIntent = Intent(this, PaymentActivity::class.java)
            paypalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalConfig)
            paypalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT,ppProcessing)
            startActivityForResult(paypalPaymentIntent, 1000)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1000){
            if(resultCode==Activity.RESULT_OK){
                var intent = Intent(this, ThankYouActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this,"Sorry, please try again",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, PayPalService::class.java))
    }
}
