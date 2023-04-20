package com.example.mycontactsapp

import android.os.Parcel
import android.os.Parcelable

class Customer() : java.io.Serializable  {
        lateinit var Name : String
        lateinit var PhoneNumber : String
        val ImageId : Int = R.drawable.anonymous
        var CompanyName : String = ""
        var Email : String = ""


    constructor(_name:String , _phoneNumber : String) :this() {
        this.Name = _name
        this.PhoneNumber = _phoneNumber
    }

     constructor(_name:String , _phoneNumber : String, _email : String, _companyName : String) :this() {
         this.Name = _name
         this.PhoneNumber = _phoneNumber
         this.Email = _email
         this.CompanyName = _companyName
     }
}
