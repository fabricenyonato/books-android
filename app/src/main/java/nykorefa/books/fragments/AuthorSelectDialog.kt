package nykorefa.books.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import nykorefa.books.R


class AuthorSelectDialog: DialogFragment() {

    private val _items = arrayOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Height", "Nine", "Zero")
    lateinit var onItemClick: OnItemClick

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (AlertDialog.Builder(activity))
            .setTitle(getString(R.string.author))
            .setItems(_items, this::onItemClick)
            .setNegativeButton(getString(R.string.cancel), this::onNegativeButtonClick)
            .create()
    }

    private fun onNegativeButtonClick(dialog: DialogInterface, which: Int) {
        dialog.dismiss()
    }

    private fun onItemClick(dialog: DialogInterface, which: Int) {
        onItemClick.onAuthorSelectDialogItemClick(_items[which])
        dialog.dismiss()
    }


    interface OnItemClick {
        fun onAuthorSelectDialogItemClick(author: String)
    }
}
