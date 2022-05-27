package putriiiiiuta.androidlima.datastrorelatihan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import putriiiiiuta.androidlima.datastrorelatihan.datastore.UserManager
import putriiiiiuta.androidlima.datastrorelatihan.model.GetUserItemItem
import putriiiiiuta.androidlima.datastrorelatihan.network.ApiClient
import retrofit2.Call
import retrofit2.Response


class LoginFragment : Fragment() {

    private var userManager: UserManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireContext())

        userManager!!.username.asLiveData().observe(viewLifecycleOwner){
            if (it != ""){
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }

        btn_login.setOnClickListener {
            if (et_username.text.isNotEmpty() && et_password.text.isNotEmpty()) {
                val username = et_username.text.toString()
                val password = et_password.text.toString()

                login(username, password)
            } else {
                Toast.makeText(requireContext(), "Mohon isi form login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(username: String, password: String) {
        ApiClient.instance.getUser(username)
            .enqueue(object : retrofit2.Callback<List<GetUserItemItem>> {
                override fun onResponse(
                    call: retrofit2.Call<List<GetUserItemItem>>,
                    response: Response<List<GetUserItemItem>>
                ) {
                    if (response.isSuccessful){
                        if (response.body() != null){
                            if (response.body()!![0].password == password){
                                GlobalScope.launch {
                                    userManager!!.saveData(username)
                                }
                                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                            }else{
                                Toast.makeText(requireContext(), "Password salah", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(requireContext(), "Akun belum terdaftar", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: retrofit2.Call<List<GetUserItemItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }


            })
    }


}