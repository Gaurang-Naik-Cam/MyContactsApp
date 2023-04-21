package com.example.mycontactsapp

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import java.lang.reflect.Type
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemLongClickListener
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mycontactsapp.databinding.ActivityMainBinding
import android.content.SharedPreferences
import android.content.Context
import android.provider.ContactsContract.Contacts
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.jar.Attributes.Name


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var itemlist = LoadContacts()//arrayListOf<Customer>()
        var adapter = customerAdapter(this,itemlist)
        val btnSave: Button = findViewById(R.id.btnSave)
        val editCustomerName: EditText = findViewById(R.id.txtCustomerName)
        val editCustomerPhoneNumber :EditText = findViewById(R.id.txtCustomerPhoneNumber)
        val listView: ListView = findViewById(R.id.ListViewCustomers)
        binding.ListViewCustomers.isClickable = true
        binding.ListViewCustomers.adapter = adapter
        adapter.notifyDataSetChanged()
        val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if(result.resultCode == RESULT_OK){
                val index = result.data?.getIntExtra("index",0)
                val updatedCustomer = result.data?.getSerializableExtra("updatedCustomer") as Customer
                itemlist.removeAt(index!!)
                itemlist.add(index!!,updatedCustomer)
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
                if(validatePhoneNumber(newCustomer.PhoneNumber , itemlist)) {
                    itemlist.add(newCustomer)
                    SaveContacts(itemlist)
                    binding.ListViewCustomers.adapter = adapter
                    adapter.notifyDataSetChanged()
                    editCustomerName.text.clear()
                    editCustomerPhoneNumber.text.clear()
                    adapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(applicationContext,"Same number is already present with with another contact",
                        Toast.LENGTH_LONG).show()
                }
            }
            else {
                Toast.makeText(applicationContext,"Contact name and phone number cannot be empty",
                    Toast.LENGTH_LONG).show()
            }
            listView.onRestoreInstanceState(state)
        }

        listView.onItemLongClickListener = OnItemLongClickListener { arg0, arg1, pos, id -> // TODO Auto-generated method stub
            Log.v("long clicked", "pos: $pos")

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
                    adapter.filter.filter(query)

                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                    adapter.filter.filter(query)

                return false
            }
        })
    }

    private fun validatePhoneNumber(phoneNumber: String, itemlist: ArrayList<Customer>): Boolean {

        for(item:Customer in itemlist){
            if(item.PhoneNumber == phoneNumber.trim())
                return false
        }
        return true
    }


    fun SaveContacts(contacts:ArrayList<Customer>) {
        val sharedPreferences = getSharedPreferences("contacts", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson:Gson = Gson()
        val contactData:String = gson.toJson(contacts)
        editor.apply{
            putString("contacts",contactData)
        }.commit()
    }

    fun LoadContacts():ArrayList<Customer>{

        val sharedPreferences = getSharedPreferences("contacts", Context.MODE_PRIVATE)
        val contacts = sharedPreferences.getString("contacts",null);
        val gson:Gson = Gson()
        val type:Type = object : TypeToken<ArrayList<Customer>>() {
        }.type
        var myList:ArrayList<Customer>
        if(contacts == null){
            myList = GenerateDummyRecords()
        }
        else {
            myList = gson.fromJson(contacts,type)
        }

        if(myList.isNullOrEmpty()){
            myList = ArrayList<Customer>()
            val newContact:Customer = Customer()
            newContact.Name = "Gaurang Naik"
            newContact.PhoneNumber = "1-202-508-9837"

            val newContact1:Customer = Customer()
            newContact1.Name = "John Doe"
            newContact1.PhoneNumber = "1-203-502-9737"

            myList.add(newContact)
            myList.add(newContact1)
        }
        return myList
    }

    fun GenerateDummyRecords() : ArrayList<Customer>{
        var myList:ArrayList<Customer> =  ArrayList<Customer>()
        val newContact:Customer = Customer()
        newContact.Name = "Gaurang Naik"
        newContact.PhoneNumber = "1-202-508-9837"

        val newContact1:Customer = Customer()
        newContact1.Name = "John Doe"
        newContact1.PhoneNumber = "1-203-502-9737"

        myList.add(newContact)
        myList.add(newContact1)

        return myList
    }
}