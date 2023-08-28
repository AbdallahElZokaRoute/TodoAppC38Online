package com.route.todosappc38online.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.WeekCalendarView
import com.kizitonwose.calendar.view.WeekDayBinder
import com.route.todosappc38online.EditActivity
import com.route.todosappc38online.R
import com.route.todosappc38online.adapters.DayViewHolder
import com.route.todosappc38online.adapters.OnItemClicked
import com.route.todosappc38online.adapters.TodosListAdapter
import com.route.todosappc38online.base.BaseFragment
import com.route.todosappc38online.clearTime
import com.route.todosappc38online.constant
import com.route.todosappc38online.database.TodoDatabase
import com.route.todosappc38online.database.model.TodoModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TodosListFragment : BaseFragment() {

    lateinit var todosRecyclerView: RecyclerView
    lateinit var adapter: TodosListAdapter
    lateinit var calendarView: WeekCalendarView
    var selectedDate: LocalDate? = null
    lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todosRecyclerView = view.findViewById(R.id.todos_recycler_view)
        calendar = Calendar.getInstance()
        adapter = TodosListAdapter(null)
        todosRecyclerView.adapter = adapter
        val items = TodoDatabase.getInstance(requireContext()).getTodosDao().getAllTodos()
        adapter.updateData(items)
        calendarView = view.findViewById(R.id.calendar_view)

        calendarView.dayBinder = object : WeekDayBinder<DayViewHolder> {
            override fun create(view: View): DayViewHolder {
                return DayViewHolder(view)
            }


            override fun bind(container: DayViewHolder, data: WeekDay) {
                // Saturday
                // Sat
                container.dayOfWeekText.text = data.date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.getDefault()
                )
                container.dayOfMonthText.text = data.date.dayOfMonth.toString()
                val currentSelection = selectedDate
                container.calendarDayView.setOnClickListener {
                    //   20                20
                    if (currentSelection == data.date) {
                        selectedDate = null
                        getTodosByDate(null)
                        calendarView.notifyDateChanged(currentSelection)
                    } else {
                        selectedDate = data.date
                        calendarView.notifyDateChanged(data.date)
                        if (currentSelection != null) {
                            calendarView.notifyDateChanged(currentSelection)
                        }
                    }
                }
                if (selectedDate == data.date) {
                    container.dayOfWeekText.setTextColor(resources.getColor(R.color.blue, null))
                    container.dayOfMonthText.setTextColor(resources.getColor(R.color.blue, null))
                    // Set Date
                    Log.e("TAG", "bind: MONTH (Github Library) = ${selectedDate?.monthValue}")
                    Log.e("TAG", "bind: MONTH (Calendar Class) = ${calendar.get(Calendar.MONTH)}")
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDate?.dayOfMonth!!)
                    calendar.set(Calendar.MONTH, selectedDate?.monthValue?.minus(1)!!)
                    calendar.set(Calendar.YEAR, selectedDate?.year!!)

                    // Clear Time
                    calendar.clearTime()
                    Log.e("TAG", "bind: ${calendar.time.time}")
                    getTodosByDate(calendar.time)
                } else {
                    container.dayOfWeekText.setTextColor(resources.getColor(R.color.black, null))
                    container.dayOfMonthText.setTextColor(resources.getColor(R.color.black, null))

                }
            }
        }

        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(100).atEndOfMonth()  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        calendarView.setup(startDate, endDate, firstDayOfWeek)
        calendarView.scrollToWeek(currentDate)


        //
        adapter.onItemClicked=object :OnItemClicked{


            override fun onItemClick(todoModel: TodoModel) {
                // show messege > Base fragment > alert Diadlog

                showMessage(
                    "what do you want ?",
                    "Update",
                    { _, dialog -> updateTodoTask(todoModel) },
                    "Make done !",
                    { _, dialog -> makeDone(todoModel) },
                )
            }

        }



    }

    //refresh recycler
    private fun refreshRecyclerView() {
        adapter.changeDate(TodoDatabase.getInstance(requireActivity()).getTodosDao().getAllTodos())
        adapter.notifyDataSetChanged()
    }

    private fun updateTodoTask(todoModel: TodoModel) {
        var intent = Intent(requireContext(), EditActivity::class.java)
        //send task by data base > to show data and edit it
        intent.putExtra(constant.Task, todoModel)
        startActivity(intent)
    }

    private fun makeDone(todoModel: TodoModel) {
        todoModel.isDone = true
        TodoDatabase.getInstance(requireActivity()).getTodosDao()
            .updateTodo(todoModel)
        refreshRecyclerView()
    }

    fun getTodosByDate(selectedDate: Date?) {
        val todosList = if (selectedDate != null)
            TodoDatabase
                .getInstance(requireContext())
                .getTodosDao()
                .getTodosByDate(selectedDate)
        else
            TodoDatabase
                .getInstance(requireContext())
                .getTodosDao()
                .getAllTodos()
        adapter.updateData(todosList)

    }
}
