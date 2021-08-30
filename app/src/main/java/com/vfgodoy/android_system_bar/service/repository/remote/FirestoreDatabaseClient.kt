package com.vfgodoy.android_system_bar.service.repository.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDatabaseClient private constructor(){

    companion object{

        var db: FirebaseFirestore? = null

        fun createFirebaseReference(tableName : String) : CollectionReference?{
            db = FirebaseFirestore.getInstance()
            return db?.collection(tableName)
        }

    }



}