package fr.coopuniverse.api.pokeapi.activity.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.lifecycle.Observer

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.view.viewModel.MeltViewModel
import kotlinx.android.synthetic.main.melt_fragment.*


class MeltFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.melt_fragment, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        MeltViewModel.result.postValue("")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MeltViewModel.initData()
        MeltViewModel.listName.observe(this, Observer {
            spinner_card.adapter = ArrayAdapter<String>(this.context,R.layout.support_simple_spinner_dropdown_item,it)
            if(it.size != 0) {
                MeltViewModel.stateButton.postValue(true)
            }
            else {
                MeltViewModel.stateButton.postValue(false)
            }
            MeltViewModel.viewInProgress.postValue(false)

        })

        melt_button.setOnClickListener {
            MeltViewModel.meltCard(spinner_card.selectedItemPosition)
        }

        MeltViewModel.result.observe(this, Observer {
            if(it != "") {
                val builder = AlertDialog.Builder(this.context)
                builder.setTitle(this.context!!.resources.getText(R.string.result_melt_title).toString())
                builder.setMessage(it)
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        })

        MeltViewModel.stateButton.observe(this, Observer {
            melt_button.isEnabled = it
        })


        MeltViewModel.viewInProgress.observe(this, Observer {
            if(it) {
                meltProgressLabel.visibility = View.VISIBLE
                gifImageView.visibility = View.VISIBLE
            }
            else {
                meltProgressLabel.visibility = View.INVISIBLE
                gifImageView.visibility = View.INVISIBLE
            }
        })

    }
}
