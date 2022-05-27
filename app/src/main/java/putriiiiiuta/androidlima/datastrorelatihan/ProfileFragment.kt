package putriiiiiuta.androidlima.datastrorelatihan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import putriiiiiuta.androidlima.datastrorelatihan.datastore.UserManager
import putriiiiiuta.androidlima.datastrorelatihan.model.GetUserItemItem
import putriiiiiuta.androidlima.datastrorelatihan.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {

    private lateinit var userManager : UserManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireContext())

        userManager.username.asLiveData().observe(viewLifecycleOwner){
            ApiClient.instance.getUser(it)
                .enqueue(object : Callback<List<GetUserItemItem>>{
                    override fun onResponse(
                        call: retrofit2.Call<List<GetUserItemItem>>,
                        response: Response<List<GetUserItemItem>>
                    ) {
                        if (response.isSuccessful){
                            val data = response.body()!![0]
                            tv_username.text = data.username
                            tv_address.text = data.address
                            tv_umur.text = data.umur.toString()
                            tv_nama.text = data.name
                            Glide.with(requireView()).load(data.image).into(img_user)
                        }else{
                            Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<List<GetUserItemItem>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                })
        }

        btn_logout.setOnClickListener {
            GlobalScope.launch {
                userManager.saveData("")
                it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }

}