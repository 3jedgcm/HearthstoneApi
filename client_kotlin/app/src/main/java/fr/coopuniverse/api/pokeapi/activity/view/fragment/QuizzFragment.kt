package fr.coopuniverse.api.pokeapi.activity.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.enums.Info
import fr.coopuniverse.api.pokeapi.activity.view.viewModel.QuizzViewModel
import kotlinx.android.synthetic.main.quizz_fragment.*


class QuizzFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.quizz_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isOnResponse(false)


        currentMoney.text = this.context!!.getString(R.string.you_have) + " " + Account.money + " " + this.context!!.getString(R.string.golds)

        QuizzViewModel.money.observe(this, Observer{
            currentMoney.text = this.context!!.getString(R.string.you_have) + " " + it + " " + this.context!!.getString(R.string.golds)
        })

        QuizzViewModel.response.observe(this, Observer {
            reponse_one.text = it[0]
            reponse_two.text = it[1]
            reponse_three.text = it[2]
            reponse_four.text = it[3]
        })

        QuizzViewModel.enableButton.observe(this, Observer {
            if(it)
            {
                this.isOnResponse(false)
            }
            else
            {
                this.isOnResponse(true)
            }
        })

        QuizzViewModel.answer.observe(this, Observer {
            answer.text = it
        })

        QuizzViewModel.info.observe(this, Observer {
            when(it)
            {
                Info.NO_MUCH_MONEY -> {
                    info.text = this.context!!.getString(R.string.insufficient_credit)
                }
                Info.VOID -> {
                    info.text = this.context!!.getString(R.string.voidString)
                }
                Info.WRONG_ANSWER -> {
                    info.text = this.context!!.getString(R.string.wrong_answer)
                }
                Info.RIGHT_ANSWER -> {
                    info.text = this.context!!.getString(R.string.right_answer)
                }
                Info.ERROR -> {
                    info.text = this.context!!.getString(R.string.networkError)
                }
            }
        })


        went.setOnClickListener {
            went.isEnabled = false
            QuizzViewModel.getAnswer()

        }
        reponse_one.setOnClickListener {
            this.disableButton()
            QuizzViewModel.setResponse(1)
        }
        reponse_two.setOnClickListener {
            this.disableButton()
            QuizzViewModel.setResponse(2)
        }
        reponse_three.setOnClickListener {
            this.disableButton()
            QuizzViewModel.setResponse(3)
        }
        reponse_four.setOnClickListener {
            this.disableButton()
            QuizzViewModel.setResponse(4)
        }


        QuizzViewModel.viewInProgress.observe(this, Observer {
            if(it == "win")
            {
                gifImageViewQuizz.setImageResource(R.drawable.quizz_win)
                gifImageViewQuizz.visibility = View.VISIBLE
            }
            else if(it == "loose")
            {
                gifImageViewQuizz.setImageResource(R.drawable.quizz_lose)
                gifImageViewQuizz.visibility = View.VISIBLE
            }
            else
            {
                gifImageViewQuizz.visibility = View.INVISIBLE
            }
        })


    }




    private fun isOnResponse(b: Boolean)
    {
        reponse_one.isEnabled = b
        reponse_two.isEnabled = b
        reponse_three.isEnabled = b
        reponse_four.isEnabled = b
        went.isEnabled = !b
    }

    private fun disableButton()
    {
        reponse_one.isEnabled = false
        reponse_two.isEnabled = false
        reponse_three.isEnabled = false
        reponse_four.isEnabled = false
        went.isEnabled = false
    }


}
