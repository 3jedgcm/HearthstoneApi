package fr.coopuniverse.api.pokeapi.activity.view.fragment


import android.accounts.Account
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.Observer

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackFragment
import fr.coopuniverse.api.pokeapi.activity.data.Account.id
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.User
import fr.coopuniverse.api.pokeapi.activity.view.viewModel.ExchangeViewModel


class ExchangeFragment : androidx.fragment.app.Fragment(),AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }




    var callback: CallBackFragment? = null

    private  var spinnerUser1Cards: Spinner? = null;
    private  var spinnerUsers : Spinner? = null;
    private  var spinnerUser2Cards: Spinner? = null;
    private  var btnExchange: Button? = null;

    private var curenUserID: String =""

    private var arrayofCardsUserCurrent : ArrayList<Card>? = null
    private var arrayofCardsUserSecond : ArrayList<Card>? = null
    private var arrayofUsers : ArrayList<User>? = null

//    private var aAdapterUserCards : ArrayAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val viewMain = inflater.inflate(R.layout.exchange_fragment, container, false)

        spinnerUser1Cards = viewMain.findViewById<Spinner>(R.id.spinnerUser1Cards)
        spinnerUsers = viewMain.findViewById<Spinner>(R.id.spinnerUsers)
        spinnerUser2Cards = viewMain.findViewById<Spinner>(R.id.spinnerUser2Cards)
        btnExchange = viewMain.findViewById<Button>(R.id. btnExchange)

        curenUserID = fr.coopuniverse.api.pokeapi.activity.data.Account.id

        spinnerUser1Cards!!.setOnItemSelectedListener(this)
        spinnerUser2Cards!!.setOnItemSelectedListener(this)
        spinnerUsers!!.setOnItemSelectedListener(this)



        ExchangeViewModel.getCardofUser(curenUserID)
        ExchangeViewModel.getAllUsers(curenUserID)




        ExchangeViewModel.dataCardsUser.observe(this, Observer {

            arrayofCardsUserCurrent= it
          //alimenter premier spinner  of card current users
            // Create an ArrayAdapter using a simple spinner layout and languages array
           val aAdapterUserCards = ArrayAdapter(context, android.R.layout.simple_spinner_item,arrayofCardsUserCurrent )
            // Set layout to use when the list of choices appear
            aAdapterUserCards.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            spinnerUser1Cards!!.setAdapter(aAdapterUserCards)


        })

        ExchangeViewModel.listofUsers_Mutable.observe(this, Observer {

            arrayofUsers = it
            // alimenter second Spinner of Users

            // Create an ArrayAdapter using a simple spinner layout and languages array
            val aAdapterUsersList = ArrayAdapter(context, android.R.layout.simple_spinner_item,arrayofUsers )
            // Set layout to use when the list of choices appear
            aAdapterUsersList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            spinnerUsers!!.setAdapter(aAdapterUsersList)


        })




        return inflater.inflate(R.layout.exchange_fragment, container, false)



    }



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is CallBackFragment) {
            callback = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

}
