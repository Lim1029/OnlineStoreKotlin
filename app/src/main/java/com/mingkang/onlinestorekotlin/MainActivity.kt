package com.mingkang.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogIn.setOnClickListener{
            val URL = "http://192.168.137.251/OnlineStoreApp/login_app_user.php?email=" +
                    edtEmail.text.toString() + "&password=" + edtPassword.text.toString()
            val requestQ = Volley.newRequestQueue(this@MainActivity)
            val stringRequest = StringRequest(Request.Method.DELETE, URL,
                Response.Listener { response ->
                    if(response == "The user does exist"){
                        Person.email = edtEmail.text.toString()
                        Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
                        val homeIntent = Intent(this@MainActivity,HomeScreen::class.java)
                        startActivity(homeIntent)
                        finish()
                    } else {
                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Alert")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()
                    }
                },
                Response.ErrorListener { error ->
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Alert")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()
                })
            requestQ.add(stringRequest)
        }

        txtSignUp.setOnClickListener {
            var intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
