package com.route.todosappc38online.ui.edit_task

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.route.todosappc38online.Constant
import com.route.todosappc38online.clearTime
import com.route.todosappc38online.database.TodoDatabase
import com.route.todosappc38online.database.model.Task
import com.route.todosappc38online.databinding.ActivityEditTaskBinding
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {
    lateinit var calendar : Calendar
    lateinit var  binding : ActivityEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews(){
      calendar = Calendar.getInstance()
        val title = intent.getStringExtra(Constant.TASK_TITLE)
        val description = intent.getStringExtra(Constant.TASK_DETAILS)
        binding.titleEditText.text = SpannableStringBuilder(title)
        binding.detailsEditText.text = SpannableStringBuilder(description)

        bindActions()
    }


    var task : Task? = null
    private fun bindActions() {

        binding.dateLayout.setOnClickListener {
            showDatePicker()
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(Constant.TASK,Task::class.java)!!
        }else{
            intent.getParcelableExtra(Constant.TASK)!!
        }



        binding.saveChangesBtn.setOnClickListener {
            if(valid()){
                val newTask = task?.copy(title = binding.titleEditText.text.toString(),
                    description = binding.detailsEditText.text.toString(),
                    date = calendar.timeInMillis,
                    isDone = false
                )

                TodoDatabase.getInstance(this).getTodosDao().updateTodo(newTask!!)
                Log.e("title1", newTask.title.toString())
                finish()
            }

        }
    }

    private fun valid(): Boolean {
     var isValid = true
        if(binding.titleEditText.text!!.isEmpty() ||binding.titleEditText.text!!.isBlank() ){
            binding.titleEditText.error = "Please Enter Task Title"
            isValid = false
        }

        if(binding.detailsEditText.text!!.isEmpty() ||binding.detailsEditText.text!!.isBlank() ){
            binding.detailsEditText.error = "Please Enter Task Details"
            isValid = false
        }

        if(binding.textViewDate.text!!.isEmpty()  ||binding.textViewDate.text!!.isBlank()){
            binding.dateLayout.error = "Enter Date Please"
            isValid = false
            }

        return isValid
    }


    private fun   showDatePicker() {

            val datePicker = DatePickerDialog(this)
            datePicker.show()
            datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                binding.textViewDate.text = "$dayOfMonth / ${month + 1} / $year"
                binding.dateLayout.error = null

                calendar.set(year,month,dayOfMonth)


                calendar.set(Calendar.HOUR_OF_DAY,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                calendar.clearTime()

            }


    }

}