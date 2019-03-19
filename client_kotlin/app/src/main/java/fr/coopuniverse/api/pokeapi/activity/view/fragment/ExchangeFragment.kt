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


    var callback: CallBackFragment? = null


    private var curenUserID: String = ""
    private var idUserTwo = ""
    private var idCard = ""
    private var idCardTwo = ""
    private var arrayofCardsUserCurrent: ArrayList<Card>? = null
    private var arrayofCardsUserSecond: ArrayList<Card>? = null
    private var arrayofUsers: ArrayList<User>? = null

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("ItemNothingSelected", "adapterview= " + parent.toString())
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("Item", view.toString() + ", pos =" + position + " id=" + id)
        //       Toast.makeText(context,"Selected : " + "pos =" + position + " id=" + id , Toast.LENGTH_LONG).show()

        if (parent!!.equals(spinnerUsers)) {


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

        spinnerUsers.setOnItemSelectedListener(this)



        ExchangeViewModel.getCardofUser(curenUserID)
        ExchangeViewModel.getAllUsers(curenUserID)


        ExchangeViewModel.dataCardsUser.observe(this, Observer {

            arrayofCardsUserSecond = it
            var arrayofCards: ArrayList<Card>? = ArrayList()
            arrayofCards = arrayofCardsUserSecond!!.clone() as ArrayList<Card>

            val aAdapterUserCards = CardsAdapter(context, arrayofCards)
            spinnerUser2Cards!!.setAdapter(aAdapterUserCards)


        })

        ExchangeViewModel.dataCardsCurrentUser.observe(this, Observer {

            arrayofCardsUserCurrent = it

            var arrayofCards2: ArrayList<Card>? = ArrayList()
            arrayofCards2 = arrayofCardsUserCurrent!!.clone() as ArrayList<Card>


            val aAdapterCurrentUserCards = CardsAdapter(context, arrayofCards2)
            spinnerUser1Cards!!.setAdapter(aAdapterCurrentUserCards)

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

        ExchangeViewModel.reloadData_Mutable.observe(this, Observer {
            Toast.makeText(context, "Exchange of cards :" + idCard + " and " + idCardTwo + " was successful", Toast.LENGTH_LONG).show()

            ExchangeViewModel.getCardofUser(curenUserID)
            ExchangeViewModel.getCardofUser((spinnerUsers.selectedItem as User).IdUser)

            //idUserTwo = (spinnerUsers.selectedItem as User).IdUser


        })
        btnExchange.setOnClickListener {


            idUserTwo = ""
            idCard    = ""
            idCardTwo = ""



            if(spinnerUsers.selectedItem!= null){
                idUserTwo = (spinnerUsers.selectedItem!! as User).IdUser
            }


            if(spinnerUser1Cards.selectedItem!= null){
                idCard = (spinnerUser1Cards.selectedItem!! as Card).id.toString()
            }

            if(spinnerUser2Cards.selectedItem!= null){
                idCardTwo = ((spinnerUser2Cards.selectedItem!! as Card).id.toString())
            }

            //  Toast.makeText(context, "Selected user"  + " has no cards", Toast.LENGTH_LONG).show()

            if (Account.id != "" && idUserTwo != "" && idCard != "" && idCardTwo != "" && !idCard.equals(idCardTwo)) {

                ExchangeViewModel.exchangeCards(_idUser = Account.id, _idUserTwo = idUserTwo, _idCard = idCard , _idCardTwo = idCardTwo)
            }


        }

        ExchangeViewModel.viewInProgress.observe(this, Observer {
            if(it)
            {

                gifImageViewExchange.visibility = View.VISIBLE
            }
            else
            {

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
        //ExchangeViewModel.result.postValue("")
    }

    override fun onResume() {
        super.onResume()
        ExchangeViewModel.getCardofUser(curenUserID)
        ExchangeViewModel.getAllUsers(curenUserID)
    }



}
