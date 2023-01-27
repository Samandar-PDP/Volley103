package uz.digital.volley103

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import uz.digital.volley103.databinding.ActivityGithubDetailBinding
import uz.digital.volley103.databinding.ActivityMainBinding
import uz.digital.volley103.model.Todo
import uz.digital.volley103.model.User
import uz.digital.volley103.util.Constants
import java.lang.reflect.Type

class GithubDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGithubDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 1)
        loadUser(id)
    }

    private fun loadUser(id: Int) {
        val requestQueue = Volley.newRequestQueue(this)
        val userRequest = JsonObjectRequest(
            Request.Method.GET,
            "${Constants.BASE_URL2}/$id",
            null,
            object : Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject?) {
                    binding.pr.isVisible = false
                    val type: Type = object : TypeToken<User>() {}.type
                    val user = Gson().fromJson<User>(response.toString(), type)
                    binding.apply {
                        Glide.with(imageView)
                            .load(user.image)
                            .circleCrop()
                            .into(imageView)
                        text.text = user.login
                    }
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    binding.pr.isVisible = false
                }
            }
        )
        requestQueue.add(userRequest)
    }
}