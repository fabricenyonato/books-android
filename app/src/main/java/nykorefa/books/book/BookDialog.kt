package nykorefa.books.book

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.View
import android.widget.EditText
import nykorefa.books.R
import nykorefa.books.author.AuthorSelectDialog
import nykorefa.books.http.Http
import org.json.JSONObject
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class BookDialog: DialogFragment(), View.OnClickListener, AuthorSelectDialog.OnItemSelected {

    lateinit private var mView: View
    lateinit private var mAuthorEditText: EditText
    lateinit var onPositiveButtonClick: OnPositiveButtonClick
    var author = 0
    var authorsNames = arrayListOf<String>()
    var authorsIds = arrayListOf<String>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mGetAuthors()
        mView = activity.layoutInflater.inflate(R.layout.book_dialog, null)

        mAuthorEditText = mView.findViewById(R.id.bookDialogBookAuthorEditText)
        mAuthorEditText.setOnClickListener(this)

        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.bookDialogTitle))
            .setView(mView)
            .setPositiveButton(getString(R.string.save), this::onPositiveButtonClick)
            .setNegativeButton(getString(R.string.cancel), this::onNegativeButtonClick)

        return builder.create()
    }

    private fun onPositiveButtonClick(dialog: DialogInterface, id: Int) {
        val title = mView.findViewById<EditText>(R.id.bookDialogBookTitleEditText).text.toString().trim()
        onPositiveButtonClick.onBookDialogPositiveButtonClick(title, mAuthorEditText.tag.toString().toInt())
    }

    private fun onNegativeButtonClick(dialog: DialogInterface, id: Int) {
        dialog.dismiss()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bookDialogBookAuthorEditText -> {
                mShowAuthorDialog()
            }
        }
    }

    override fun onAuthorSelectDialogItemSelected(author: Int) {
        this.author = author
        mAuthorEditText.setText(authorsNames[author])
        mAuthorEditText.tag = authorsIds[author]
    }

    private fun mShowAuthorDialog() {
        val authorDialog = AuthorSelectDialog()
        authorDialog.onItemSelectedListener = this
        authorDialog.checkedItem = author
        authorDialog.items = authorsNames
        authorDialog.show(fragmentManager, "authorSelectDialog")
    }

    private fun mGetAuthors() {
        val jsonBody = JSONObject()
        jsonBody.put("query", "MATCH (author:Author) RETURN id(author), author.name")

        Http.getInstance().post(jsonBody, object: Callback {
            override fun onFailure(call: Call?, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call?, response: Response) {
                val data: JSONArray = JSONObject(response.body()?.string()).getJSONArray("data")
                Log.d("BookDialog", data.toString())

                for (i in 0..(data.length() - 1)) {
                    val author = data[i] as JSONArray

                    authorsIds.add(i, author[0].toString())
                    authorsNames.add(i, author[1].toString())
                }
            }
        })
    }


    interface OnPositiveButtonClick {
        fun onBookDialogPositiveButtonClick(book: String, author: Int)
    }
}
