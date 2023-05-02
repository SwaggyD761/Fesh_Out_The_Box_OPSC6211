package com.example.demo001_opsc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ManageCollections : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_collections)

        // Get the RecyclerView from the layout
        val recyclerView: RecyclerView = findViewById(R.id.collections_recycler_view)

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = CollectionAdapter(getCollectionsFromDatabase())
    }

    private fun getCollectionsFromDatabase(): List<Collection> {
        // Retrieve collections from the database and return a list of Collection objects
        // Replace this with your own implementation to get collections from your database
        return listOf(
            Collection(R.drawable.collection1, "Collection 1", 199),
            Collection(R.drawable.collection2, "Collection 2", 287),
            Collection(R.drawable.collection3, "Collection 3", 392),
            Collection(R.drawable.collection4, "Collection 4", 401),
            Collection(R.drawable.collection5, "Collection 5", 53),
            Collection(R.drawable.collection6, "Collection 6", 89),
            Collection(R.drawable.collection7, "Collection 7", 79),
            Collection(R.drawable.collection8, "Collection 8", 808),
            Collection(R.drawable.collection9, "Collection 9", 99),
        )
    }
}

class CollectionAdapter(private val collections: List<Collection>) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val collectionImage: ImageView = itemView.findViewById(R.id.collection_image)
        val collectionName: TextView = itemView.findViewById(R.id.collection_name)
        val collectionPrice: TextView = itemView.findViewById(R.id.collection_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collection_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collection = collections[position]

        // Set the collection image, name, and price in the corresponding views
        holder.collectionImage.setImageResource(collection.image)
        holder.collectionName.text = collection.name
        holder.collectionAmount.text = "$${collection.price}"
    }

    override fun getItemCount() = collections.size
}

data class Collection(val image: Int, val name: String, val price: Double)
