package com.example.hubbie.modules.message.listChat.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hubbie.BuildConfig
import com.example.hubbie.R
import com.example.hubbie.adapter.ListChatAdapter
import com.example.hubbie.adapter.OnGetUserChatList
import com.example.hubbie.entities.User
import com.example.hubbie.modules.message.OnGetData
import com.example.hubbie.modules.message.listChat.IListChat
import com.example.hubbie.modules.message.listChat.presenter.ListChatPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_list_chat.view.*

/**
 * A simple [Fragment] subclass.
 */
class ListChatFragment : Fragment(), IListChat.View {

    private var getView: View? = null
    private var adapter: ListChatAdapter? = null
    private var listChat: ArrayList<User>? = null
    private var presenter: ListChatPresenter? = null
    private var user: User? = null
    private var listennerGetUserChat = object : OnGetUserChatList {
        override fun onItemClick(position: Int) {
            presenter?.navigationMessage(
                context,
                user?.uid ?: "",
                listChat?.get(position)?.uid ?: ""
            )
        }
    }

    private var listenerGetListChat = object : OnGetData<ArrayList<User>> {
        override fun onSuccess(data: ArrayList<User>?) {
            Log.d("huanhuan", "List : " + Gson().toJson(data))
            listChat = data
            adapter?.updateAdapter(listChat ?: ArrayList())
        }

        override fun onError() {
        }
    }

    companion object {

        const val ARG_USER = BuildConfig.APPLICATION_ID + ".arg.ARG_USER"

        fun newInstance(user: User): ListChatFragment {
            val fragment = ListChatFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_USER, user)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = ListChatPresenter()
        getView = inflater.inflate(R.layout.fragment_list_chat, container, false)
        getDataBundle()
        initAdapter()
        getListChat()
        return getView

    }

    private fun getDataBundle() {
        user = arguments?.getParcelable(ARG_USER)
        Log.d("huanhuan", "User :  " + Gson().toJson(user))
    }

    private fun getListChat() {
        Log.d("huanhuan", "Get ")
        presenter?.getListChat(user ?: User(), listenerGetListChat)
    }

    private fun initAdapter() {
        adapter = ListChatAdapter(context, listChat, listennerGetUserChat)
        getView?.rv_list_chat?.layoutManager = LinearLayoutManager(context)
        getView?.rv_list_chat?.adapter = adapter
    }
}
