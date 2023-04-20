package com.example.mycontactsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.example.mycontactsapp.databinding.ActivityDetailsBinding

class details : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding
    private var selectedIndex:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnUpdate: Button = findViewById(R.id.buttonUpdate)
        val editCustomerName: EditText = findViewById(R.id.editTexName)
        val editCustomerPhoneNumber: EditText = findViewById(R.id.editTextPhone)
        val editCustomerEmail: EditText = findViewById(R.id.editEmail)
        val editCustomerCompany: EditText = findViewById(R.id.editTextCompany)
        val customer = intent.getSerializableExtra("customer") as Customer
        val index = intent.getIntExtra("index",0)
        selectedIndex = index
        Log.d("Index of CustomerList: ",index.toString());
        binding.editTexName.setText(customer.Name)
        binding.editTextPhone.setText(customer.PhoneNumber)
        binding.editEmail.setText(customer.Email)
        binding.editTextCompany.setText(customer.CompanyName)

        btnUpdate.setOnClickListener {

            if (editCustomerName.text.isNotEmpty() && editCustomerPhoneNumber.text.isNotEmpty() ) {
                var newCustomer: Customer = Customer()
                newCustomer.Name = editCustomerName.text.toString()
                newCustomer.PhoneNumber = editCustomerPhoneNumber.text.toString()
                newCustomer.Email = editCustomerEmail.text.toString()
                newCustomer.CompanyName = editCustomerCompany.text.toString()

                var resultIntent = Intent()
                resultIntent.putExtra("updatedCustomer",newCustomer)
                resultIntent.putExtra("index",selectedIndex)
                setResult(RESULT_OK)
                finish()
            }
            else
            {
                //send toast message
            }
        }

    }


}