package nykorefa.books.book

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.book_list_activity.*
import kotlinx.android.synthetic.main.content_main.*
import nykorefa.books.R
import nykorefa.books.http.Http
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class BookListActivity : AppCompatActivity(), BookDialog.OnPositiveButtonClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_list_activity)
        setSupportActionBar(toolbarMain)

        mGetBooks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemSearch -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onFabClick(view: View) {
        val bookDialog = BookDialog()
        bookDialog.onPositiveButtonClick = this
        bookDialog.show(supportFragmentManager, "bookDialog")
    }

    override fun onBookDialogPositiveButtonClick(book: String, author: Int) {
        Log.d("BookListActivity", "$book, $author")
    }

    private fun mGetBooks() {
        val jsonBody = JSONObject()
        jsonBody.put("query", "MATCH (book:Book)-[:WRITTEN_BY]->(author:Author) RETURN id(book), book.title, id(author), author.name")

        Http.getInstance().post(jsonBody, object: Callback {
            override fun onResponse(call: Call?, response: Response) {
                val data = JSONObject(response.body()?.string()).getJSONArray("data")

                Log.d("BookListActivity", data.toString())
                this@BookListActivity.runOnUiThread {
                    textView.text = data.toString()
                }
            }

            override fun onFailure(call: Call?, e: IOException) {
                e.printStackTrace()
            }
        })
    }
}