package com.kotlinallsample.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlinallsample.R

import kotlinx.android.synthetic.main.layout_rv_item.view.*

/**
 * Created by Nilesh Birhade on 23-05-2018.
 */
class AnimalAdapter(val listAnimals: ArrayList<String>, val context: Context) :
        RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvAnimalType?.text = listAnimals.get(position)

        holder.tvAnimalType.setOnClickListener { view ->
            (context as RecyclerSampleActivity).clickfromAdapter(position)
        }

        /*holder.tvAnimalType.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //  Toast.makeText(context, listAnimals.get(position), Toast.LENGTH_SHORT).show()
            itemClicked.getClickedPostion(position)
            }
        })*/
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return listAnimals.size
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvAnimalType = view.txtAnimalName

}