package com.route.todosappc38online.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todosappc38online.R
import com.route.todosappc38online.database.model.TodoModel
import com.zerobranch.layout.SwipeLayout

class TodosListAdapter(private var todosList: MutableList<TodoModel>? = null) :
    Adapter<TodosListAdapter.TodosListViewHolder>() {
    var onTaskClickListener:OnTaskClickListener?=null
    var onItemDeleteClickListener:OnItemDeleteClickListener?=null

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

    fun updateData(todosList: MutableList<TodoModel>?) {
        this.todosList = todosList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TodosListViewHolder, position: Int) {
        val item = todosList?.get(position)
        holder.time.text = item?.time.toString()
        holder.title.text = item?.title
        holder.title.setOnLongClickListener {
            onTaskClickListener?.onTaskClick(item!!,position)
            return@setOnLongClickListener true
        }
        holder.deletBtn.setOnClickListener {
            holder.swipe.close(true)
            onItemDeleteClickListener?.onItemDeleteClick(item!!,position)
        }
        holder.checkImage.setOnClickListener {
            onTaskClickListener?.onDoneClick(item!!,position)
        }
        if(todosList!![position].isDone!!){
            holder.checkImage.setImageResource(R.drawable.ic_done)
            holder.checkImage.setBackgroundColor(Color.TRANSPARENT)
            holder.checkImage.setPadding(8)
            holder.title.setTextColor(holder.title.resources.getColor(R.color.green_isDone_btn,null))
            holder.viewLine.setBackgroundColor(holder.viewLine.resources.getColor(R.color.green_isDone_btn,null))

        }

    }

    fun taskDelete(task: TodoModel) {
        todosList?.remove(task)
        notifyItemRemoved(todosList?.indexOf(task)!!)
    }

    class TodosListViewHolder(val view: View) : ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.todo_title_text)
        val time: TextView = view.findViewById(R.id.todo_time)
        val checkImage: ImageView = view.findViewById(R.id.todo_check)
        val swipe:SwipeLayout=view.findViewById(R.id.swipe_layout)
        val deletBtn:CardView=view.findViewById(R.id.delete_view)
        val viewLine:View=view.findViewById(R.id.vertical_line)
    }

    interface OnTaskClickListener {
        fun onTaskClick(task:TodoModel,position:Int)
        fun onDoneClick(task:TodoModel,position: Int)
    }

    interface OnItemDeleteClickListener{
        fun onItemDeleteClick(task:TodoModel,position: Int)
    }

}