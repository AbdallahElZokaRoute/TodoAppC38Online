package com.route.todosappc38online.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todosappc38online.R
import com.route.todosappc38online.database.model.Task
import com.route.todosappc38online.databinding.ItemTodoBinding
import com.zerobranch.layout.SwipeLayout
import com.zerobranch.layout.SwipeLayout.SwipeActionsListener

class TodosListAdapter(private var todosList: MutableList<Task>? = null , val listener : SwipeActionsListener,val taskListener : OnTaskClick) : Adapter<TodosListAdapter.TodosListViewHolder>() {

    var position : Int? = null


    var onDoneClick : OnDoneClick? = null
    var onDeleteCLick : onDeleteClick? = null
    var itemToDoBinding : ItemTodoBinding? = null
    var selectedDate : Long? = null

    inner class TodosListViewHolder(val binding : ItemTodoBinding) : ViewHolder(binding.root){
       fun bind(task : Task){
           binding.todoTitleText.text = task.title
           binding.descriptionItem.text = task.description
       }
   }

    fun setDate(selectedDate: Long){
        this.selectedDate= selectedDate
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosListViewHolder {
         itemToDoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodosListViewHolder(itemToDoBinding!!)
    }

    override fun getItemCount(): Int {
        return todosList?.size ?: 0
    }

    fun updateData(todosList: MutableList<Task>?) {
        this.todosList = todosList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TodosListViewHolder, position: Int) {
       holder.bind(todosList!![position])
        holder.binding.swipeLayoutX.setOnActionsListener(listener)
        this.position = position

        holder.binding.cardItemTask.setOnLongClickListener{
            taskListener.onClick(task = todosList!![position],position)
            true
        }

        if(todosList!![position].isDone!!){
            holder.binding.verticalLine.setBackgroundColor(Color.GREEN)
            holder.binding.todoTitleText.setTextColor(Color.GREEN)
            holder.binding.doneBtn.setBackgroundResource(R.drawable.done)
            holder.binding.doneBtn.setImageResource(0)
        }else{
            holder.binding.verticalLine.setBackgroundColor(Color.parseColor("#5D9CEC"))
            holder.binding.todoTitleText.setTextColor(Color.parseColor("#5D9CEC"))
            holder.binding.doneBtn.setBackgroundResource(R.drawable.ic_check_bg)
            holder.binding.doneBtn.setImageResource(R.drawable.ic_check)
        }

       holder.binding.deleteIcon.setOnClickListener {
           holder.binding.swipeLayoutX.close(true)
           onDeleteCLick?.onDelete(todosList!![position],position)
       }

        holder.binding.doneBtn.setOnClickListener {
            onDoneClick?.updateDatabase(todosList!![position],position)
        }


    }

    fun taskDeleted(task: Task?) {
        val position = todosList?.indexOf(task)
        todosList?.remove(task)
        notifyItemRemoved(position!!)
    }

    fun interface onDeleteClick {
        fun  onDelete(task : Task, position: Int)
    }

    fun interface OnTaskClick{
        fun onClick(task: Task,position: Int)
    }

    fun interface OnDoneClick{
        fun updateDatabase(task: Task,position: Int)
    }





}