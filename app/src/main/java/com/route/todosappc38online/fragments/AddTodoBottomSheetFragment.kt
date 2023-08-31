package com.route.todosappc38online.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todosappc38online.R
import com.route.todosappc38online.clearTime
import com.route.todosappc38online.database.TodoDatabase
import com.route.todosappc38online.database.model.Task
import com.route.todosappc38online.databinding.FragmentAddTodoBinding
import java.util.Calendar

class AddTodoBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var calendar: Calendar

    lateinit var binding : FragmentAddTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        calendar = Calendar.getInstance()


        binding.dateLayout.setOnClickListener {
            showDatePicker()
        }
        binding.addTodoBtn.setOnClickListener {
            if(validateFields()){
                addTodoToDataBase()
            }
        }
    }

    private fun validateFields(): Boolean {

         var isValid = true

        if (binding.titleEditText.text.toString().isEmpty() || binding.titleEditText.text.toString().isBlank()) {
            binding.titleEditText.error = getString(R.string.title_required)
            isValid = false

        }

        if (binding.descriptionEditText.text.toString().isEmpty() || binding.descriptionEditText.text.toString().isBlank()
        ) {
            binding.descriptionEditText.error = "Description Required"
            isValid = false
        }

        if (binding.dateEditText.text.toString().isEmpty() || binding.dateEditText.text.toString().isBlank()
        ) {
            binding.dateLayout.error = "Date Required"
            isValid = false
        }


        return isValid
    }

    private fun addTodoToDataBase() {

            val task = Task(
                title = binding.titleEditText.text.toString(),
                description = binding.descriptionEditText.text.toString(),
                date = calendar.timeInMillis
            )
            calendar.clearTime()

            TodoDatabase
                .getInstance(requireContext())
                .getTodosDao()
                .insertTodo(task)
            onTaskAddedListener?.onTaskAdded()
            dismiss()
    }
    var onTaskAddedListener : OnTaskAddedListener? = null
    fun interface OnTaskAddedListener{
        fun onTaskAdded()
    }

    private fun   showDatePicker() {
        context?.let { // 3shan enta mesh mot2kd en require context htb2a null wla la2
            val datePicker = DatePickerDialog(it)
            datePicker.show()
            datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                binding.dateEditText.text = "$dayOfMonth / ${month + 1} / $year"
                calendar.set(year,month,dayOfMonth)

                //to ignore time
                calendar.set(Calendar.HOUR_OF_DAY,0)
                calendar.set(Calendar.MINUTE,0)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)
                calendar.clearTime()

            }
        }

    }
}