package com.example.hubbie.modules.message.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hubbie.BuildConfig
import com.example.hubbie.R
import com.example.hubbie.adapter.InAppMessageAdapter
import com.example.hubbie.entities.Message
import com.example.hubbie.modules.message.OnGetData
import com.example.hubbie.modules.message.presenter.MessagePresenter
import com.example.hubbie.utilis.GeneralUtils
import com.example.hubbie.utilis.firestore.FirestoreMessageUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_message.view.*

/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val ARG_UID_USER = BuildConfig.APPLICATION_ID + ".arg.ARG_UID_USER"
        private const val ARG_UID_RECEIVER = BuildConfig.APPLICATION_ID + ".arg.ARG_UID_RECEIVER"
        fun newInstance(uid: String, receiverID: String): MessageFragment {
            val fragment = MessageFragment()
            val bundle = Bundle()
            bundle.putString(ARG_UID_USER, uid)
            bundle.putString(ARG_UID_RECEIVER, receiverID)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var getView: View? = null
    private var listSMS = ArrayList<Message>()
    private var adapter: InAppMessageAdapter? = null
    private var uid: String? = null
    private var receiverId: String? = null
    private val presenter = MessagePresenter()
    private val listenerNewMessage = object : OnGetData<Message> {
        override fun onSuccess(data: Message?) {
            onNewMessageComing(data ?: Message())
        }

        override fun onError() {
        }
    }

    private val listenerAllMessage = object : OnGetData<ArrayList<Message>> {
        override fun onSuccess(data: ArrayList<Message>?) {
            Log.d("huanhuan", " data : " + Gson().toJson(data))
            adapter?.updateAdapter(data ?: ArrayList())
        }

        override fun onError() {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getView = inflater.inflate(R.layout.fragment_message, main_container_frame, false)
        initView()
        getDataBundle()
        initAdapter(getView)
        getMessageHistory()
        listenToNewMessage(uid ?: "", receiverId ?: "")
        return getView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun getMessageHistory() {
        //Get message history
        presenter.getHistoryMessage(uid ?: "", receiverId ?: "", listenerAllMessage)
    }

    private fun initView() {
        getView?.img_send?.setOnClickListener(this)
    }

    private fun listenToNewMessage(senderID: String, receiverID: String) {
        presenter.onListentToNewMessage(senderID, receiverID, listenerNewMessage)
    }

    private fun onNewMessageComing(message: Message) {
        //Add new message into listSMS
        listSMS.add(message)
        adapter?.updateAdapter(listSMS)
        getView?.rv_message?.smoothScrollToPosition(listSMS.lastIndex)
    }

    private fun initAdapter(getView: View?) {

        adapter = InAppMessageAdapter(context, listSMS, uid)
        getView?.rv_message?.layoutManager = LinearLayoutManager(context)
        getView?.rv_message?.adapter = adapter

    }

    private fun getDataBundle() {
        uid = arguments?.getString(ARG_UID_USER)
        receiverId = arguments?.getString(ARG_UID_RECEIVER)
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.img_send -> {
                val textMessage = getView?.edit_text_input_message?.text.toString()
                val message = Message()
                message.message = textMessage
                message.uid = uid
                message.time = GeneralUtils.getTimeId()

                if (textMessage.isNotEmpty()) {

//                    listSMS.add(message)
//                    adapter?.updateAdapter(listSMS, true)
                    getView?.edit_text_input_message?.setText("")

                }

                //Store message into firestore
                FirestoreMessageUtil.setMessage(uid ?: "", receiverId ?: "", message)
                FirestoreMessageUtil.setMessage(receiverId ?: "", uid ?: "", message)

            }
        }

    }
}
