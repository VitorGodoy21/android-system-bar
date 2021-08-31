package com.vfgodoy.android_system_bar.service.model

import com.google.firebase.firestore.DocumentId

class ProductModel(
    @DocumentId
    val id: String = "",
    val name : String? = null,
    val price : Float? = null,
    val imageUrl : String? = null
)