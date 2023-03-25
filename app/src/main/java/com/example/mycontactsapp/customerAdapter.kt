package com.example.mycontactsapp

import android.app.Activity
import android.widget.ArrayAdapter
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView

class customerAdapter(private val context : Activity, private val customerList : ArrayList<Customer>) :
    ArrayAdapter<Customer>(context,R.layout.list_item,customerList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view :View = inflater.inflate(R.layout.list_item,null)
        val imgProfileView : ImageView = view.findViewById(R.id.imgDefault)
        val txtCustomerName : TextView = view.findViewById(R.id.txtviewCustomerName)

        imgProfileView.setImageResource(customerList[position].ImageId)
        txtCustomerName.text = customerList[position].Name.toString()
        return view
    }
}

