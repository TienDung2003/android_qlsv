package com.example.quan_ly_sv

import android.content.Context
import android.view.*
import android.widget.*
import androidx.appcompat.widget.PopupMenu

class StudentAdapter(
    private val context: Context,
    private val list: MutableList<Student>,
    private val listener: (String, Student, Int) -> Unit
) : BaseAdapter() {

    override fun getCount() = list.size
    override fun getItem(position: Int) = list[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            // Inflate layout mới khi convertView null
            view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
            holder = ViewHolder()
            holder.tvName = view.findViewById(R.id.tvName)
            holder.tvMssv = view.findViewById(R.id.tvMssv)
            holder.imgMenu = view.findViewById(R.id.imgMenu)
            view.tag = holder // lưu holder vào tag của view
        } else {
            // Tái sử dụng convertView
            view = convertView
            holder = view.tag as ViewHolder
        }

        val student = list[position]
        holder.tvName.text = student.name
        holder.tvMssv.text = student.mssv

        holder.imgMenu.setOnClickListener {
            val popup = PopupMenu(context, holder.imgMenu)
            popup.menuInflater.inflate(R.menu.student_popup_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_update -> listener("update", student, position)
                    R.id.menu_delete -> listener("delete", student, position)
                    R.id.menu_call -> listener("call", student, position)
                    R.id.menu_email -> listener("email", student, position)
                }
                true
            }
            popup.show()
        }

        return view
    }

    private class ViewHolder {
        lateinit var tvName: TextView
        lateinit var tvMssv: TextView
        lateinit var imgMenu: ImageView
    }
}
