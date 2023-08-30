package com.route.todosappc38online.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todosappc38online.R
import com.route.todosappc38online.database.model.TodoModel

class TodosListAdapter(private var todosList: List<TodoModel>? = null) :
    Adapter<TodosListAdapter.TodosListViewHolder>() {
    var onTaskClickListener:OnTaskClickListener?=null

    /*
    1- Swipe to delete
    2- Edit To task
    3- Mark As Done
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodosListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todosList?.size ?: 0
    }

    fun updateData(todosList: List<TodoModel>?) {
        this.todosList = todosList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TodosListViewHolder, position: Int) {
        val item = todosList?.get(position)
        holder.time.text = item?.time.toString()
        holder.title.text = item?.title
        holder.view.setOnLongClickListener {
            onTaskClickListener?.onTaskClick(item!!,position)
            return@setOnLongClickListener true
        }

    }

    class TodosListViewHolder(val view: View) : ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.todo_title_text)
        val time: TextView = view.findViewById(R.id.todo_time)
        val checkImage: ImageView = view.findViewById(R.id.todo_check)
    }

    interface OnTaskClickListener {
        fun onTaskClick(task:TodoModel,position:Int)
    }

}