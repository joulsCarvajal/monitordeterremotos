package com.alphazetakapp.earthquakemonitor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.alphazetakapp.earthquakemonitor.databinding.EqListItemBinding

/*QUe es inflar? Es crear a partir de un layout*/

private val TAG = EqAdapter::class.java.simpleName

class EqAdapter(private val context: Context) : ListAdapter<Earthquake, EqAdapter.EqViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqAdapter.EqViewHolder{
        val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int){
        val earthquake = getItem(position)
        holder.bind(earthquake)
    }

    inner class  EqViewHolder(private val binding: EqListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(earthquake: Earthquake){
            /*binding.eqMagnitudeText.text = earthquake.magnitude.toString()*/
            binding.eqMagnitudeText.text = context.getString(R.string.magnitude_format, earthquake.magnitude)
            binding.eqPlaceText.text = earthquake.place

            /*Lo que hace esta linea de 🔽 es forzar al binding a que no espere los 16ms del findviewbyid sino
            que pinte inmediatamente*/
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(earthquake)
                }else{
                    Log.e(TAG, "onItemClickListener is not initialized")
                }

            }
        }

    }

}