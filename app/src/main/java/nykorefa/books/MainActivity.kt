package nykorefa.books

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.main_activity.*
import nykorefa.books.fragments.BookDialog

class MainActivity : AppCompatActivity(), BookDialog.OnPositiveButtonClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbarMain)

        if (intent.action == Intent.ACTION_SEARCH ) {
            Log.d("MainActivity", SearchManager.QUERY)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
//
//        val searchItem = menu.findItem(R.id.menuItemSearch)
//        val searchView = searchItem.actionView as SearchView

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

    override fun onBookDialogPositiveButtonClick(title: String) {
        Log.d("MainActivity", title)
    }
}
