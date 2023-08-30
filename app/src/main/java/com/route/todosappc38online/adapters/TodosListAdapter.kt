package com.route.todosappc38online.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todosappc38online.R
import com.route.todosappc38online.database.model.TodoModel
import com.zerobranch.layout.SwipeLayout

class TodosListAdapter(private var todosList: List<TodoModel>? = null) :
    Adapter<TodosListAdapter.TodosListViewHolder>() {
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
    var onItemDeleteClick: OnItemDeleteClick? = null
    var onItemLongClick: OnItemLongClick? = null
    var onTaskDone: OnTaskDone? = null
    override fun onBindViewHolder(holder: TodosListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = todosList?.get(position)
        holder.time.text = item?.time.toString()
        holder.title.text = item?.title
        if (item!!.isDone == true) {
            holder.isDoneButton.setImageResource(R.drawable.ic_done)
            holder.isDoneButton.setBackgroundResource(R.color.transparent)
            holder.viewLine.setBackgroundColor(
                holder.viewLine.resources.getColor(
                    R.color.lightGreen,
                    null
                )
            )
            holder.title.setTextColor(holder.title.resources.getColor(R.color.lightGreen, null))
        }
        else if (item.isDone == false) {
            holder.isDoneButton.setImageResource(R.drawable.ic_check)
            holder.isDoneButton.setBackgroundResource(R.drawable.is_done_button_shape)
            holder.viewLine.setBackgroundColor(
                holder.viewLine.resources.getColor(
                    R.color.blue,
                    null
                )
            )
            holder.title.setTextColor(holder.title.resources.getColor(R.color.blue, null))
        }
        holder.swipeLayout.setOnActionsListener(object : SwipeLayout.SwipeActionsListener {
            override fun onOpen(direction: Int, isContinuous: Boolean) {
                holder.deleteButton.setOnClickListener {
                    holder.swipeLayout.close(true)
                    onItemDeleteClick?.onClickDelete(item, position)
                }
            }

            override fun onClose() {
            }

        })
        holder.isDoneButton.setOnClickListener {
            onTaskDone?.onDone(item)
        }
        if (onItemLongClick != null) {
            holder.card.setOnLongClickListener {
                onItemLongClick?.onLongClick(item)
                true
            }
        }
    }

    class TodosListViewHolder(val view: View) : ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.task_name)
        val time: TextView = view.findViewById(R.id.task_time)
        val checkImage: ImageView = view.findViewById(R.id.task_check_button)
        var isDoneButton: ImageView = view.findViewById(R.id.task_check_button)
        var deleteButton: ImageView = view.findViewById(R.id.delete)
        var swipeLayout: SwipeLayout = view.findViewById(R.id.swipe)
        var viewLine: View = view.findViewById(R.id.view_line)
        var card: CardView = view.findViewById(R.id.card)
    }

    fun deleteItem(position: Int) {
        notifyItemRemoved(position)
    }

    fun interface OnTaskDone {
        fun onDone(todo: TodoModel)
    }

    fun interface OnItemDeleteClick {
        fun onClickDelete(todo: TodoModel, position: Int)
    }

    fun interface OnItemLongClick {
        fun onLongClick(todo: TodoModel)
    }
}