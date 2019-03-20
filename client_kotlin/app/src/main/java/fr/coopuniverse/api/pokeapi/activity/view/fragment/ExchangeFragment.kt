package fr.coopuniverse.api.pokeapi.activity.view.fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsAdapter
import fr.coopuniverse.api.pokeapi.activity.adapter.UsersAdapter
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackFragment
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.User
import fr.coopuniverse.api.pokeapi.activity.view.viewModel.ExchangeViewModel
import kotlinx.android.synthetic.main.exchange_fragment.*


class ExchangeFragment : androidx.fragment.app.Fragment(), AdapterView.OnItemSelectedListener {

    private var callback: CallBackFragment? = null
    private var curenUserID: String = ""
    private var idUserTwo = ""
    private var idCard = ""
    private var idCardTwo = ""
    private var arrayofCardsUserCurrent: ArrayList<Card>? = null
    private var arrayofCardsUserSecond: ArrayList<Card>? = null
    private var arrayofUsers: ArrayList<User>? = null

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //Void fun
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent == spinnerUsers) {
            var selectedItm: User = spinnerUsers.selectedItem as User
            if (selectedItm != null) {
                ExchangeViewModel.getCardofUser(selectedItm.IdUser)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.exchange_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        curenUserID = fr.coopuniverse.api.pokeapi.activity.data.Account.id
        spinnerUsers.onItemSelectedListener = this
        ExchangeViewModel.getCardofUser(curenUserID)
        ExchangeViewModel.getAllUsers(curenUserID)
        ExchangeViewModel.dataCardsUser.observe(this, Observer {
            arrayofCardsUserSecond = it
            var arrayofCards: ArrayList<Card>?
            arrayofCards = arrayofCardsUserSecond!!.clone() as ArrayList<Card>
            val aAdapterUserCards = CardsAdapter(context, arrayofCards)
            spinnerUser2Cards!!.adapter = aAdapterUserCards
        })

        ExchangeViewModel.dataCardsCurrentUser.observe(this, Observer {
            arrayofCardsUserCurrent = it
            var arrayofCards2: ArrayList<Card>?
            arrayofCards2 = arrayofCardsUserCurrent!!.clone() as ArrayList<Card>
            val aAdapterCurrentUserCards = CardsAdapter(context, arrayofCards2)
            spinnerUser1Cards!!.adapter = aAdapterCurrentUserCards
        })

        ExchangeViewModel.listofUsers_Mutable.observe(this, Observer {
            arrayofUsers = it
            var array: ArrayList<User> = ArrayList()
            arrayofUsers!!.forEach {
                if (it.IdUser != curenUserID) {
                    array.add(it)
                }
            }


            val aAdapterUsersList = UsersAdapter(context, array)
            spinnerUsers!!.setAdapter(aAdapterUsersList)
        })

        ExchangeViewModel.nbCardsUser.observe(this, Observer {
            if (it.equals(0)) {
                Toast.makeText(context, "Selected user" + " has no cards", Toast.LENGTH_LONG).show()
            }
        })

        btnExchange.setOnClickListener {
            if(spinnerUsers.selectedItem != null && spinnerUser1Cards.selectedItem != null && spinnerUser2Cards.selectedItem != null ){
                idUserTwo = (spinnerUsers.selectedItem!! as User).IdUser
                idCard = (spinnerUser1Cards.selectedItem!! as Card).id.toString()
                idCardTwo = ((spinnerUser2Cards.selectedItem!! as Card).id.toString())
                btnExchange.isEnabled = false
                ExchangeViewModel.exchangeCards(idUser = Account.id, idUserTwo = idUserTwo, idCard = idCard , idCardTwo = idCardTwo)
            }
        }

        ExchangeViewModel.viewInProgress.observe(this, Observer {
            if(it) {
                gifImageViewExchange.visibility = View.VISIBLE
            }
            else {
                gifImageViewExchange.visibility = View.INVISIBLE
            }
        })
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

    override fun onResume() {
        super.onResume()
        ExchangeViewModel.getCardofUser(curenUserID)
        ExchangeViewModel.getAllUsers(curenUserID)
    }
}
