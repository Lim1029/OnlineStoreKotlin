package com.mingkang.onlinestorekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_e_products.*

class FetchEProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_e_products)

        val selectedBrand = intent.getStringExtra("BRAND")
        txtBrandName.text = selectedBrand

        var productsList = ArrayList<EProduct>()

        val productURL = "http://192.168.137.251/OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"
        val requestQ = Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonAR = JsonArrayRequest(Request.Method.GET, productURL, null,
            Response.Listener { response ->
                for(productJOIndex in 0.until(response.length())){
                    productsList.add(EProduct(
                        response.getJSONObject(productJOIndex).getInt("id"),
                        response.getJSONObject(productJOIndex).getString("name"),
                        response.getJSONObject(productJOIndex).getInt("price"),
                        response.getJSONObject(productJOIndex).getString("brand"),
                        response.getJSONObject(productJOIndex).getString("picture")))
                }
                var pAdapter = EProductAdapter(this@FetchEProductsActivity, productsList)
                productsRV.layoutManager = LinearLayoutManager(this@FetchEProductsActivity)
                productsRV.adapter = pAdapter
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
