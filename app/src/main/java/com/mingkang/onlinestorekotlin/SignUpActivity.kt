package com.mingkang.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUp.setOnClickListener {
            if(edtPasswordSignUp.text.toString().equals(edtPasswordConfirm.text.toString())){
                val URL = "http://192.168.137.251/OnlineStoreApp/join_new_user.php?" +
                        "email=" + edtEmailSignUp.text.toString()+
                        "&username=" + edtUsername.text.toString()+
                        "&password=" + edtPasswordSignUp.text.toString()
                val requestQ = Volley.newRequestQueue(this@SignUpActivity)
                val stringRequest = StringRequest(Request.Method.GET, URL,
                    Response.Listener { response ->
                        if(response == "A user with this Email Address already exists") {
                            val dialogBuilder = AlertDialog.Builder(this)
                            dialogBuilder.setTitle("Alert")
                            dialogBuilder.setMessage(response)
                            dialogBuilder.create().show()
                        } else {
                            Person.email = edtEmailSignUp.text.toString()
                            val dialogBuilder = AlertDialog.Builder(this)
                            dialogBuilder.setTitle("Alert")
                            dialogBuilder.setMessage(response)
                            dialogBuilder.create().show()
                            val homeIntent = Intent(this@SignUpActivity,HomeScreen::class.java)
                            startActivity(homeIntent)
                            finish()
                        }
                    },
                    Response.ErrorListener { error ->
                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Alert")
                        dialogBuilder.setMessage(error.message)
                        dialogBuilder.create().show()
                    })
                requestQ.add(stringRequest)
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Alert")
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()
            }
        }
    }
}
