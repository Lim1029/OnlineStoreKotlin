package com.mingkang.onlinestorekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProductURL = "http://192.168.137.251/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        var cartProductList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        val jsonAR = JsonArrayRequest(Request.Method.GET, cartProductURL, null,
            Response.Listener { response ->
                for(joIndex in 0.until(response.length())){//id,name,price,email,amount
                    cartProductList.add("${response.getJSONObject(joIndex).getInt("id")} \n" +
                            "${response.getJSONObject(joIndex).getString("name")} \n" +
                            "${response.getJSONObject(joIndex).getInt("price")} \n"+
                            "${response.getJSONObject(joIndex).getString("amount")} \n")
                }

                var cardProductsAdapter = ArrayAdapter(this@CartProductsActivity, android.R.layout.simple_list_item_1, cartProductList)
                cartProductListView.adapter = cardProductsAdapter
            },
            Response.ErrorListener { error ->
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Alert")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })
        requestQ.add(jsonAR)

    }
}
