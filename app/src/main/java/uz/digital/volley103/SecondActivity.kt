package uz.digital.volley103

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.digital.volley103.adapter.UserAdapter
import uz.digital.volley103.databinding.ActivitySecondBinding
import uz.digital.volley103.model.User
import uz.digital.volley103.util.Constants
import uz.digital.volley103.util.NetworkUtils
import java.lang.reflect.Type

class SecondActivity : AppCompatActivity() {
    private val userAdapter by lazy { UserAdapter() }
    private lateinit var requestQueue: RequestQueue
    private val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@SecondActivity)
        }

        val networkUtils = NetworkUtils(this)
        if (networkUtils.isNetworkConnected()) {
            requestQueue = Volley.newRequestQueue(this)
            loadUsers()
        } else {
            Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
        }
        userAdapter.onClick = {
            val id = bundleOf("id" to it)
            val intent = Intent(this, GithubDetailActivity::class.java)
            intent.putExtras(id)
            startActivity(intent)
        }
    }

    private fun loadUsers() {
        val userRequest = JsonArrayRequest(
            Request.Method.GET,
            Constants.BASE_URL2,
            null,
            {
                binding.progressBar.isVisible = false
                val type: Type = object : TypeToken<List<User>>() {}.type
                val userList = Gson().fromJson<List<User>>(it.toString(), type)
                userAdapter.submitList(userList)
            },
            {
                binding.progressBar.isVisible = false
                Toast.makeText(this@SecondActivity, "${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        requestQueue.add(userRequest)
    }
}