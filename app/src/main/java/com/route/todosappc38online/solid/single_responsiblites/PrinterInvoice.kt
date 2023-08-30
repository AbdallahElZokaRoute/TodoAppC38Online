package com.route.todosappc38online.solid.single_responsiblites

class PrinterInvoice {

    fun printInvoice(book: Book, invoice: Invoice){
        println("${invoice.quantity} X  ${book.name}    ${book.price} \$ ")
        println("Discount rate:  ${invoice.discountRate}")
        println("Tax Rate: ${invoice.taxRate}")
        println("Total: ${invoice.total}")
    }
}