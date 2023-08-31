package com.route.todosappc38online

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.route.todosappc38online.database.Converters
import com.route.todosappc38online.database.TodoDatabase
import com.route.todosappc38online.database.model.TodoModel
import com.route.todosappc38online.databinding.ActivityEditTaskBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class EditTaskActivity : AppCompatActivity() {

    lateinit var binding:ActivityEditTaskBinding
    val calendar = Calendar.getInstance()
    private lateinit var task: TodoModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        task =((intent.getSerializableExtra("Task")as?TodoModel)!!)
        showData(task)
        binding.dateContainerEdit.setOnClickListener {
            showDatePickerDialog()
        }
        binding.saveEditTaskBtn.setOnClickListener {
            updateTodoTask()
        }
        binding.btnBack.setOnClickListener { finish() }
    }
    private fun validate():Boolean{
        var isValid =true
        if(binding.editTaskTitle.text.toString().isNullOrBlank()){
            binding.titleContainerEdit.error = "please enter title "
            isValid=false
        }else{
            binding.titleContainerEdit.error = null
        }
        if(binding.descEditText.text.toString().isNullOrBlank()){
            binding.descriptionContainerEdit.error = "please enter description "
            isValid=false
        }else{
            binding.descriptionContainerEdit.error = null
        }
        /* if(binding.selectDateValueEdit.text.toString().isNullOrBlank()){
             binding.dateContainerEdit.error = "please choose date "
             isValid=false
         }else{
             binding.dateContainerEdit.error = null
         }*/
        return isValid
    }

    private fun updateTodoTask() {
        if(!validate()){
            return
        }
        task.title=binding.editTaskTitle.text.toString()
        task.description=binding.descEditText.text.toString()
        task.time=calendar.time
        TodoDatabase.getInstance(this).getTodosDao().updateTodo(task)
        startActivity(Intent(this,MainActivity::class.java))
        finish()


    }

    private fun showDatePickerDialog() {
        var dialog = DatePickerDialog(this)
        dialog.setOnDateSetListener{ view, year, month, dayOfMonth ->
            binding.selectDateValueEdit.text ="$year / ${month +1} / $dayOfMonth"
            //  calendar.set(year,month,dayOfMonth)
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            // to ignore time
            calendar.clearTime()

        }
        dialog.show()
    }

    private fun showData(task: TodoModel) {
        binding.editTaskTitle.setText(task.title)
        binding.descEditText.setText(task.description)
        val date=converLongToTime(task.time)
        binding.selectDateValueEdit.text=date

    }
    private fun converLongToTime(dateTime: Date?): CharSequence? {
        val dateLong = Converters().dateToTimestamp(dateTime)
        val date=Date(dateLong!!)
        val format = SimpleDateFormat("yyyy/MM/dd")
        return format.format(date)

    }

}