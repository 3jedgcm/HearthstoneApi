package fr.coopuniverse.api.pokeapi.activity.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.view.viewModel.CraftViewModel
import kotlinx.android.synthetic.main.craft_fragment.*
import kotlinx.android.synthetic.main.melt_fragment.*


class CraftFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.craft_fragment, container, false)

    }

    override fun onDetach() {
        super.onDetach()
        CraftViewModel.result.postValue("")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CraftViewModel.initData()
        CraftViewModel.listNameCard.observe(this, Observer {
            spinner_card_one.adapter = ArrayAdapter<String>(this.context,R.layout.support_simple_spinner_dropdown_item,it)
            spinner_card_two.adapter = ArrayAdapter<String>(this.context,R.layout.support_simple_spinner_dropdown_item,it)
            spinner_card_three.adapter = ArrayAdapter<String>(this.context,R.layout.support_simple_spinner_dropdown_item,it)
            if(it.size != 0)
            {
                CraftViewModel.stateButton.postValue(true)
            }
            else
            {
                CraftViewModel.stateButton.postValue(false)
            }
            CraftViewModel.viewInProgress.postValue(false)

        })

        craft_button.setOnClickListener {
            if(spinner_card_one.selectedItemPosition != spinner_card_two.selectedItemPosition && spinner_card_two.selectedItemPosition != spinner_card_three.selectedItemPosition && spinner_card_one.selectedItemPosition != spinner_card_three.selectedItemPosition)
            {
                CraftViewModel.craftCard(spinner_card_one.selectedItemPosition,spinner_card_two.selectedItemPosition,spinner_card_three.selectedItemPosition)
            }
            else
            {
                error.text = "Info : Select three different card"
            }
        }



        CraftViewModel.result.observe(this, Observer {
            if(it != "")
            {
                val builder = AlertDialog.Builder(this.context)
                builder.setTitle("Melt result")
                builder.setMessage(it)
                val dialog: AlertDialog = builder.create()
                dialog.show()

            }
        })

        CraftViewModel.stateButton.observe(this, Observer {
            craft_button.isEnabled = it

        })


        CraftViewModel.viewInProgress.observe(this, Observer {
            if(it)
            {
                //meltProgressLabel.visibility = View.VISIBLE
                //gifImageView.visibility = View.VISIBLE
            }
            else
            {
                //meltProgressLabel.visibility = View.INVISIBLE
                //gifImageView.visibility = View.INVISIBLE
            }
        })

    }
}
