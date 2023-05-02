package com.example.demo001_opsc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ManageSneakers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_
    }


        dropdownMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        // View Sneaks selected
                        val intent = Intent(this@YourActivity, ManageSneakersActivity::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        // View Collections selected
                        val intent = Intent(this@YourActivity, ManageCollectionsActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

    // Get the RecyclerView from the layout
    val recyclerView: RecyclerView = findViewById(R.id.sneakers_recycler_view)

    // Set the layout manager and adapter for the RecyclerView
    recyclerView.layoutManager = GridLayoutManager(this, 3)
    recyclerView.adapter = SneakerAdapter(getSneakersFromDatabase())



}

class SneakerAdapter(private val sneakers: List<Sneaker>) : RecyclerView.Adapter<SneakerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sneakerImage: ImageView = itemView.findViewById(R.id.sneaker_image)
        val sneakerName: TextView = itemView.findViewById(R.id.sneaker_name)
        val sneakerPrice: TextView = itemView.findViewById(R.id.sneaker_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sneaker_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sneaker = sneakers[position]

        // Set the sneaker image, name, and price in the corresponding views
        holder.sneakerImage.setImageResource(sneaker.image)
        holder.sneakerName.text = sneaker.name
        holder.sneakerPrice.text = "$${sneaker.price}"
    }

    override fun getItemCount() = sneakers.size
}

