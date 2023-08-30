package com.route.todosappc38online.solid.single_responsiblites

data class Invoice(
    val book : Book ,
    val quantity : Int ,
    val discountRate : Double ,
    val taxRate : Double ,
    val  total : Double ,
    )