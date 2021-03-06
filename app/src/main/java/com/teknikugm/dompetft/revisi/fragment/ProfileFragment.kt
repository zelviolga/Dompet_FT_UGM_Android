package com.teknikugm.dompetft.revisi.fragment

import android.app.AlertDialog
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.retrofit.Constant
import com.teknikugm.dompetft.revisi.Login
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.SessionManager
import com.teknikugm.dompetft.utama.MainActivity
import kotlinx.android.synthetic.main.fragment_profile_fragment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragmnet.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private var PREFS_NAME = MainActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiClient = ApiClient()
        sessionManager = SessionManager(this.context!!)

        if (sessionManager.fetchAuthToken() == null) {
            usernameprofil.text = "Guest"
            nameprofil.text = "Guest"
            iduserprofil.text = "Guest"
            emailprofil.text = "Guest"
            buttonlogoutprofil.visibility = View.GONE
        }
        else {
            val activity: MainActivity = activity as MainActivity
            val profile = activity.getProfile()

            nameprofil.text= profile?.saldo
            usernameprofil.text = profile?.username
            iduserprofil.text = profile?.id.toString()
            emailprofil.text = profile?.email.toString()

            buttonlogoutprofil.visibility = View.VISIBLE
        }

        buttonlogoutprofil.setOnClickListener(){
            AlertDialog.Builder(context )
                    .setMessage("Are You Sure to Logout?")
                    .setPositiveButton("Yes") { dialog, whichButton ->
                        signout()
                    }
                    .setNegativeButton("Cancel") { dialog, whichButton ->
                    }
                    .show()

        }

//        usern.text = context?.getSharedPreferences(PREFS_NAME.toString(),ContextWrapper.MODE_PRIVATE)?.getString("id","none")
    }

    private fun signout(){
        sessionManager.saveAuthToken(null)
        sessionManager.saveDeviceId(0)
        startActivity(Intent(this.context,Login::class.java))
        this.activity?.finish()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragmnet.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}