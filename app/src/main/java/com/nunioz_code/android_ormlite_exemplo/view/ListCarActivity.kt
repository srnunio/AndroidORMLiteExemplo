package com.nunioz_code.android_ormlite_exemplo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.nunioz_code.android_ormlite_exemplo.R
import com.nunioz_code.android_ormlite_exemplo.model.Car
import com.nunioz_code.android_ormlite_exemplo.model.DAOCar
import com.nunioz_code.android_ormlite_exemplo.model.DataBaseHelper
import kotlinx.android.synthetic.main.activity_create_car.*
import kotlinx.android.synthetic.main.activity_list_car.*
import java.util.*

class ListCarActivity : AppCompatActivity() {

    var dbHelper: DataBaseHelper = DataBaseHelper(this)
    var dao: DAOCar = DAOCar(dbHelper.connectionSource)
    var carAdpter: CarAdpter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_car)

        carAdpter = CarAdpter()
        recycler.setHasFixedSize(false)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = carAdpter
        novoCar.setOnClickListener({
            startActivityForResult(
                    Intent(this, CreateCarActivity::class.java), 0
            )
        })
        updateList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       updateList()
    }

    fun updateList(){
        val list = dao.queryForAll()
        Collections.reverse(list)
        carAdpter!!.addItens(list)
    }
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
