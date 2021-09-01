package com.vfgodoy.android_system_bar.service.model

import android.net.Uri
import com.google.firebase.firestore.DocumentId

class ProductModel(
    @DocumentId
    val id: String = "",
    val name : String? = null,
    val price : Float? = null,
    val imageUri : Uri? = null,
    val imageUrl : String? = null
)