package nykorefa.books.author

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import nykorefa.books.R


class AuthorSelectDialog: DialogFragment() {

    var items = arrayListOf<String>()
    lateinit var onItemSelectedListener: OnItemSelected
    var checkedItem = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (AlertDialog.Builder(activity))
            .setTitle(getString(R.string.author))
            .setSingleChoiceItems(items.toTypedArray(), checkedItem, this::onItemSelected)
            .create()
    }

    private fun onItemSelected(dialog: DialogInterface, which: Int) {
        onItemSelectedListener.onAuthorSelectDialogItemSelected(which)
        dialog.dismiss()
    }


    interface OnItemSelected {
        fun onAuthorSelectDialogItemSelected(author: Int)
    }
}
