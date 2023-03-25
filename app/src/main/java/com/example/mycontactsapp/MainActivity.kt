package com.example.mycontactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.core.util.set
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // var itemlist = arrayListOf<String>()
        var itemlist = arrayListOf<Customer>()
        //var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)
        var adapter = customerAdapter(this,itemlist)
        val btnSave: Button = findViewById(R.id.btnSave)
        //val btnDelete: Button = findViewById(R.id.btnDelete)
       // val btnClear: Button = findViewById(R.id.btnClear)
        val editCustomerName: EditText = findViewById(R.id.txtCustomerName)
        val editCustomerPhoneNumber :EditText = findViewById(R.id.txtCustomerPhoneNumber)
        val listView: ListView = findViewById(R.id.ListViewCustomers)

        btnSave.setOnClickListener {
            val state = listView.onSaveInstanceState()
            if (editCustomerName.text.isNotEmpty() && editCustomerPhoneNumber.text.isNotEmpty() ) {
                var newCustomer: Customer = Customer()
                newCustomer.Name = editCustomerName.text.toString()
                newCustomer.PhoneNumber = editCustomerPhoneNumber.text.toString()
                itemlist.add(newCustomer)
                listView.adapter = adapter
                adapter.notifyDataSetChanged()
                editCustomerName.text.clear()
                editCustomerPhoneNumber.text.clear()
                adapter.notifyDataSetChanged()
            }
            listView.onRestoreInstanceState(state)
        }

/* btnClear.setOnClickListener {
     itemlist.clear()
     adapter.notifyDataSetChanged()
 }*/

/* btnDelete.setOnClickListener {
     val positions = listView.checkedItemPositions;
     var items = listView.count - 1;
     while (items >= 0) {
         if (positions.get(items)) {
             adapter.remove(itemlist.get(items))
         }
         items--
     }
     positions.clear()
     adapter.notifyDataSetChanged()

 }*/
}
}