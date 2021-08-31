package com.vfgodoy.android_system_bar.service.repository.remote

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FirebaseStorage {

    companion object{
        fun storageReference() : StorageReference?{
            return Firebase.storage.reference
        }
    }

}