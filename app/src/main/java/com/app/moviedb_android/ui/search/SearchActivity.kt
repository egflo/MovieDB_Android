package com.app.moviedb_android.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.*

import com.app.moviedb_android.R

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val search=findViewById<SearchView>(R.id.searchView)
        val results=findViewById<ListView>(R.id.listView)

        val names = arrayOf("python", "php")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)
        results.adapter = adapter

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search.clearFocus()
                if(names.contains(query)){
                    //val intent= Intent(this@SearchActivity, SearchResultActivity::class.java)
                    //intent.putExtra("query", query)
                    //startActivity(intent)
                    adapter.filter.filter(query)
                }

                Toast.makeText(this@SearchActivity, query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })



    }


}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}