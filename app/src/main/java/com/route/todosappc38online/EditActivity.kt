package com.route.todosappc38online


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import androidx.appcompat.app.AlertDialog
import com.route.todosappc38online.database.TodoDatabase
import com.route.todosappc38online.database.model.TodoModel
import com.route.todosappc38online.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.Date

class EditActivity : AppCompatActivity() {
    private lateinit var task:TodoModel
    lateinit var ViewBinding:ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewBinding=ActivityEditBinding.inflate(layoutInflater)
        setContentView(ViewBinding.root)
        //recive data
        task = ((intent.getSerializableExtra(constant.Task)as? TodoModel)!!)
        showData(task)
        ViewBinding.editBtn.setOnClickListener(View.OnClickListener {
            updateTodo()
        })
    }
    fun valdit():Boolean{
        var valid=true
        val title =ViewBinding.containerTitle.editText?.text.toString()
        val desc=ViewBinding.containerDesc.editText?.text.toString()

        if (title.isNullOrBlank())
        {
            ViewBinding.containerTitle.error=" please enter the title"
            valid=false
        }

        else {
            ViewBinding.containerTitle.error = null
        }
        if (desc.isNullOrBlank())
        {

            ViewBinding.containerDesc.error="please enter the description"
            valid=false
        }
        else {
            ViewBinding.containerDesc.error = null
        }
        return valid
    }

    private fun updateTodo() {
        if (valdit()==false) {
            return;
        }
        task.title=ViewBinding.containerTitle.editText?.text.toString()
        task.description=ViewBinding.containerDesc.editText?.text.toString()
        TodoDatabase.getInstance(this).getTodosDao().updateTodo(task)
        showInsertDialog()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun showInsertDialog() {
        val alertDialogBuilder= AlertDialog.Builder(this)
            .setMessage("Update successfully")
            .setPositiveButton("ok"
            ) { dialog, which ->
                dialog.dismiss()
            }
        alertDialogBuilder.show()
    }

    private fun showData(todoModel: TodoModel) {
        ViewBinding.containerTitle.editText?.setText(todoModel.title)
        ViewBinding.containerDesc.editText?.setText(todoModel.description)

    }

}