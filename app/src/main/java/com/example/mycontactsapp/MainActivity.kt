package com.example.mycontactsapp

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mycontactsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //adding menu to the Actionbar
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu,menu)
//
//        val menuItem:MenuItem = menu.findItem(R.id.searchMenu)
//        val searchView:SearchView =  menuItem.actionView as SearchView
//        searchView.queryHint = "Search contacts by name"
//        searchView.setOnQueryTextListener(searchView.setOnQueryTextListener {
//            @Override
//           fun onQueryTextSubmit(query:String): Boolean {
//               return false
//           }
//        })
//
//        return true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.ListViewCustomers.isClickable = true

        val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: androidx.activity.result.ActivityResult ->

            if(result.resultCode == Activity.RESULT_OK){
                val index = result.data?.getIntExtra("index",0)
                val updatedCustomer = result.data?.getSerializableExtra("updatedCustomer") as Customer
                itemlist.removeAt(index!!)
                itemlist.add(index!!,updatedCustomer)
              //  binding.ListViewCustomers.adapter = adapter
                adapter.notifyDataSetChanged()

            }
        }

        binding.ListViewCustomers.setOnItemClickListener { parent, view, position, id ->

            val name = itemlist[position].Name
            val phone = itemlist[position].PhoneNumber

            val intent = Intent(this,details::class.java)
            intent.putExtra("customer",itemlist[position])
            intent.putExtra("index", position)
            resultContract.launch(intent)
        }

        btnSave.setOnClickListener {
            val state = listView.onSaveInstanceState()
            if (editCustomerName.text.isNotEmpty() && editCustomerPhoneNumber.text.isNotEmpty() ) {
                var newCustomer: Customer = Customer()
                newCustomer.Name = editCustomerName.text.toString()
                newCustomer.PhoneNumber = editCustomerPhoneNumber.text.toString()
                itemlist.add(newCustomer)
                //listView.adapter = adapter
                binding.ListViewCustomers.adapter = adapter
                adapter.notifyDataSetChanged()
                editCustomerName.text.clear()
                editCustomerPhoneNumber.text.clear()
                adapter.notifyDataSetChanged()
            }
            listView.onRestoreInstanceState(state)
        }

//        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener {
//                parent, view, position, id  ->
//            itemlist.removeAt (position)
//            adapter.notifyDataSetChanged()
//            return true
//
//        }

        listView.onItemLongClickListener = OnItemLongClickListener { arg0, arg1, pos, id -> // TODO Auto-generated method stub
            Log.v("long clicked", "pos: $pos")
//            val dialogBuilder = AlertDialog.Builder(this)
//            dialogBuilder.setTitle("Do you want to remove this contact?")
            AlertDialog.Builder(this)
                .setTitle("Do you want to remove this contact?")
                .setPositiveButton("Yes",DialogInterface.OnClickListener{ dialog , id ->
                    itemlist.removeAt (pos)
                    adapter.notifyDataSetChanged()
                })
                .setNegativeButton("No",DialogInterface.OnClickListener{ dialog , id ->
                    dialog.cancel()
                }).create().show()
            true
        }

        binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                val searchResult = itemlist.map{ c-> c.Name.contains(query.toString()) }
                if(searchResult != null) {
                    adapter.filter.filter(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //binding.searchView.clearFocus()
                val searchResult = itemlist.filter { c-> c.Name.contains(query.toString()) }
                if(searchResult != null) {
                    adapter.filter.filter(query)
                }
                return false
            }
        })




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