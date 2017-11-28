package nykorefa.books.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText
import nykorefa.books.R


class BookDialog: DialogFragment(), View.OnClickListener, AuthorSelectDialog.OnItemClick {

    lateinit private var _view: View
    lateinit private var _authorEditText: EditText
    lateinit var onPositiveButtonClick: BookDialog.OnPositiveButtonClick

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _view = activity.layoutInflater.inflate(R.layout.book_dialog, null)

        _authorEditText = _view.findViewById(R.id.bookDialogBookAuthorEditText)
        _authorEditText.setOnClickListener(this)

        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.bookDialogTitle))
            .setView(_view)
            .setPositiveButton(getString(R.string.save), this::onPositiveButtonClick)
            .setNegativeButton(getString(R.string.cancel), this::onNegativeButtonClick)

        return builder.create()
    }

    private fun onPositiveButtonClick(dialog: DialogInterface, id: Int) {
        val title = _view.findViewById<EditText>(R.id.bookDialogBookTitleEditText).text.toString().trim()
        onPositiveButtonClick.onBookDialogPositiveButtonClick(title)
    }

    private fun onNegativeButtonClick(dialog: DialogInterface, id: Int) {
        dialog.dismiss()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bookDialogBookAuthorEditText -> {
                val authorDialog = AuthorSelectDialog()
                authorDialog.onItemClick = this
                authorDialog.show(fragmentManager, "authorSelectDialog")
            }
        }
    }

    override fun onAuthorSelectDialogItemClick(author: String) {
        _authorEditText.setText(author)
    }


    interface OnPositiveButtonClick {
        fun onBookDialogPositiveButtonClick(title: String)
    }
}
