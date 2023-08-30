package com.route.todosappc38online.solid.single_responsiblites

class CalculateInvoice  {

    fun calculateTotal(book: Book, invoice: Invoice): Double {
        val price = ((book.price - book.price * invoice.discountRate) * invoice.quantity)

        return price * (1 + invoice.taxRate)
    }
}