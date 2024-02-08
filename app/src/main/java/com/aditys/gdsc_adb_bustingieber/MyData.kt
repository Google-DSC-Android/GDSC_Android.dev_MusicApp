package com.aditys.gdsc_adb_bustingieber

import android.provider.ContactsContract

data class MyData(
    val `data`: List<ContactsContract.Contacts.Data>,
    val next: String,
    val total: Int
)