package com.route.todosappc38online.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todosappc38online.R
import com.route.todosappc38online.database.model.TodoModel
import com.zerobranch.layout.SwipeLayout


class TodosListAdapter(private var todosList: List<TodoModel>? = null) :
    Adapter<TodosListAdapter.TodosListViewHolder>() {
    /*
    1- Swipe to delete
    2- Edit To task (DN)
    3- Mark As Done (DN)
     */
    var onItemClicked:OnItemClicked?=null //
    var onItemDeleteClick: OnItemDeleteClick? = null///

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

        if (todosList!![position].isDone!!) {
            holder.btnIsDone.setBackgroundColor(Color.GREEN)
            holder.title.setTextColor(Color.GREEN)
            holder.btnIsDone.setBackgroundResource(R.drawable.makedone)
        }

        holder.swipe.setOnActionsListener(object :SwipeLayout.SwipeActionsListener{
            override fun onOpen(direction: Int, isContinuous: Boolean) {

            }

            override fun onClose() {

            }

        })

        if(onItemClicked!=null) {
            holder.card.setOnLongClickListener {
                onItemClicked?.onItemClick(todosList!![position])
                true
            }

        }

        holder.delete.setOnClickListener(View.OnClickListener {
            onItemDeleteClick?.onItemDeleteClick(position, todosList!![position])
        })

    }

    class TodosListViewHolder(val view: View) : ViewHolder(view) {
        val btnIsDone: ImageView = view.findViewById(R.id.todo_check)
        val title: TextView = view.findViewById(R.id.todo_title_text)
        val time: TextView = view.findViewById(R.id.todo_time)
        val card: CardView = view.findViewById(R.id.card)
        val swipe: SwipeLayout = view.findViewById(R.id.swipe)
        val delete: LinearLayout = view.findViewById(R.id.delete)

    }

    fun changeDate(newListOfTasks: List<TodoModel>?) {
        todosList = newListOfTasks;
        notifyDataSetChanged()
    }



}

interface OnItemClicked{
    fun onItemClick(todoModel: TodoModel)
}

interface OnItemDeleteClick {
    fun onItemDeleteClick(position: Int, task: TodoModel)
}