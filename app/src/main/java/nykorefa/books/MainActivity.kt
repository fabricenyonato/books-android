package nykorefa.books

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    override fun onBookDialogPositiveButtonClick(title: String) {
        Log.d("MainActivity", title)
    }
}
