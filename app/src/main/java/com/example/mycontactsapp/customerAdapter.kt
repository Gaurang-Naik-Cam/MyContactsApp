package com.example.mycontactsapp

import android.app.Activity
import android.widget.ArrayAdapter
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView

class customerAdapter(private val context : Activity, private var customerList : ArrayList<Customer>) :
    ArrayAdapter<Customer>(context,R.layout.list_item,customerList), Filterable {
    private var cList:ArrayList<Customer> = customerList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val inflater: LayoutInflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.list_item, parent, false)


            if(customerList!=null) {
                if (customerList.isNotEmpty()) {
                    try {
                        val imgProfileView: ImageView = view.findViewById(R.id.imgDefault)
                        val txtCustomerName: TextView = view.findViewById(R.id.txtviewCustomerName)
                        imgProfileView.setImageResource(customerList[position].ImageId)
                        txtCustomerName.text = customerList[position].Name.toString()
                    }
                    catch (e:java.lang.Exception) {
                        print(e)

                    }
                }
            }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val filterResults = FilterResults()
                val queryString = charSequence.toString().lowercase().trim()
                 if (queryString==null || queryString.isEmpty()) {
                     customerList = cList
                     filterResults.values = cList//customerList
                     filterResults.count = cList.count()//customerList.count()
                 }
                else {
                    val lst:List<Customer> = customerList.filter {
                         it.Name.lowercase().contains(queryString.toString()) ||
                                 it.CompanyName.lowercase().contains(queryString.toString()) ||
                                 it.PhoneNumber.contains(queryString.toString())}

                         filterResults.values = lst
                         filterResults.count = lst.count()

                 }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

                if(p1!=null) {
                    customerList = p1?.values as ArrayList<Customer>
                    notifyDataSetChanged()
               }
            }

        }
    }
}

